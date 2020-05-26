package com.spring.practice.minaz.demoproject.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "filepath")
public class FilepathConfiguration {

    private String departmentPath;
    private String employeePath;
    private String mappingPath;

    public String getDepartmentPath() {
        return departmentPath;
    }

    public void setDepartmentPath(String departmentPath) {
        this.departmentPath = departmentPath;
    }

    public String getEmployeePath() {
        return employeePath;
    }

    public void setEmployeePath(String employeePath) {
        this.employeePath = employeePath;
    }

    public String getMappingPath() {
        return mappingPath;
    }

    public void setMappingPath(String mappingPath) {
        this.mappingPath = mappingPath;
    }
}
