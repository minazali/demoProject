package com.spring.practice.minaz.demoproject.utility;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.spring.practice.minaz.demoproject.exceptions.GenericException;
import com.spring.practice.minaz.demoproject.model.Department;
import com.spring.practice.minaz.demoproject.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

@Component
public class DepartmentFileUpload {
    @Autowired
    private  DepartmentService departmentService;

    public  boolean uploadDepartmentData(String departmentFile){
        try {
            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
            CsvMapper mapper = new CsvMapper();
            File file = Paths.get(departmentFile).toFile();
            MappingIterator<Department> readValues =
                    mapper.readerWithSchemaFor(Department.class).with(bootstrapSchema).readValues(file);
            List<Department> departmentList = readValues.readAll();
            departmentService.saveAll(departmentList);
            return true;
        } catch (Exception e) {
            throw new GenericException("DepartmentFileUpload: "+e.getMessage());
        }
    }

}
