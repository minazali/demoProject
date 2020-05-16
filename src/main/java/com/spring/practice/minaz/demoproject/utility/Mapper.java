package com.spring.practice.minaz.demoproject.utility;


public class Mapper {
    private String departmentName;
    private String pipeSeparatedFirstname;

    public Mapper(String departmentName, String pipeSeparatedFirstname){
        this.departmentName=departmentName;
        this.pipeSeparatedFirstname = pipeSeparatedFirstname;
    }

    public Mapper(){}

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getPipeSeparatedFirstname() {
        return pipeSeparatedFirstname;
    }

    public void setPipeSeparatedFirstname(String pipeSeparatedFirstname) {
        this.pipeSeparatedFirstname = pipeSeparatedFirstname;
    }
}
