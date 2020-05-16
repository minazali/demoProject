package com.spring.practice.minaz.demoproject.utility;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
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

    @Value("${department.filepath}")
    private String departmentFilepath;

    @Value("${employee.filepath}")
    private String employeeFilepath;

    @Value("${mappingFilepath}")
    private String mappingFilepath;

    @Autowired
    private DepartmentFileUpload departmentFileUpload;

    @Autowired
    private EmployeeFileUpload employeeFileUpload;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EmployeeService employeeService;

    public void process() {
        Function<Boolean, Boolean> uploadEmployees = b -> {
            if (b)
                return employeeFileUpload.uploadEmployeeData(employeeFilepath);
            else
                return false;
        };
        if (mode.equalsIgnoreCase("CREATE") && departmentFilepath != null && employeeFilepath != null) {
            CompletableFuture<Boolean> completableFuture = CompletableFuture.supplyAsync(() -> {
                return departmentFileUpload.uploadDepartmentData(departmentFilepath);
            }).thenApplyAsync(uploadEmployees);
            try {
                boolean result = completableFuture.get();
                if (result) {
                    createMapping(mappingFilepath);
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
