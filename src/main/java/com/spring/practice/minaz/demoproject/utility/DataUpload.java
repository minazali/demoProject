package com.spring.practice.minaz.demoproject.utility;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.spring.practice.minaz.demoproject.configuration.FilepathConfiguration;
import com.spring.practice.minaz.demoproject.exceptions.GenericException;
import com.spring.practice.minaz.demoproject.services.DepartmentService;
import com.spring.practice.minaz.demoproject.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

@Component
public class DataUpload {

    @Value("${mode}")
    private String mode;

    @Value("${mode1}")
    private String mode1;

    private DepartmentFileUpload departmentFileUpload;

    private EmployeeFileUpload employeeFileUpload;

    private DepartmentService departmentService;

    private EmployeeService employeeService;

    private FilepathConfiguration filepathConfiguration;

    @Autowired
    public DataUpload(DepartmentFileUpload departmentFileUpload,EmployeeFileUpload employeeFileUpload,DepartmentService departmentService,EmployeeService employeeService){
        this.departmentFileUpload = departmentFileUpload;
        this.employeeFileUpload = employeeFileUpload;
        this.departmentService = departmentService;
        this.employeeService = employeeService;
    }

    @Autowired
    public void setFilepathConfiguration(FilepathConfiguration filepathConfiguration) {
        this.filepathConfiguration = filepathConfiguration;
    }

    public void process() {
        Function<Boolean, Boolean> uploadEmployees = b -> {
            if (b)
                return employeeFileUpload.uploadEmployeeData(filepathConfiguration.getEmployeePath());
            else
                return false;
        };
        if (mode.equalsIgnoreCase("CREATE") && filepathConfiguration.getDepartmentPath() != null && filepathConfiguration.getEmployeePath() != null) {
            CompletableFuture<Boolean> completableFuture = CompletableFuture.supplyAsync(() -> {
                return departmentFileUpload.uploadDepartmentData(filepathConfiguration.getDepartmentPath());
            }).thenApplyAsync(uploadEmployees);
            try {
                boolean result = completableFuture.get();
                if (result) {
                    createMapping(filepathConfiguration.getMappingPath());
                }
                System.out.println("Initial load status: " + result);
            } catch (InterruptedException | ExecutionException e) {
                throw new GenericException("Data Upload Failed: "+e.getMessage());
            }
        }
    }

    public void createMapping(String mappingFilepath){
        try {
            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
            CsvMapper mapper = new CsvMapper();
            File file = Paths.get(mappingFilepath).toFile();
            MappingIterator<Mapper> readValues =
                    mapper.readerWithSchemaFor(Mapper.class).with(bootstrapSchema).readValues(file);
            List<Mapper> mappings = readValues.readAll();
            getEmployees(mappings);
        } catch (Exception e) {
            throw new GenericException("Mapping Failed: "+e.getMessage());
        }
    }

    public void getEmployees(List<Mapper> mappings) {
        for (Mapper mapper : mappings) {
            String names[] = mapper.getPipeSeparatedFirstname().split("\\|");
            for (String name : Arrays.asList(names)) {
                employeeService.createEmployee(name, mapper.getDepartmentName());
            }
        }
    }

}
