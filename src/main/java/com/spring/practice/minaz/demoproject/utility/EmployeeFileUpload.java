package com.spring.practice.minaz.demoproject.utility;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.spring.practice.minaz.demoproject.exceptions.GenericException;
import com.spring.practice.minaz.demoproject.model.Employee;
import com.spring.practice.minaz.demoproject.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

@Component
public class EmployeeFileUpload {

    @Autowired
    private EmployeeService employeeService;

    public  boolean uploadEmployeeData(String employeeFilepath){
        try {
            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
            CsvMapper mapper = new CsvMapper();
            File file = Paths.get(employeeFilepath).toFile();
            MappingIterator<Employee> readValues =
                    mapper.readerWithSchemaFor(Employee.class).with(bootstrapSchema).readValues(file);
            List<Employee> employeeList = readValues.readAll();
            return employeeService.saveAll(employeeList);
        } catch (Exception e) {
            throw new GenericException("EmployeeFileUpload: "+e.getMessage());
        }
    }


}
