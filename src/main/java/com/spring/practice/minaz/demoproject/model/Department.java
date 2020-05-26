package com.spring.practice.minaz.demoproject.model;

import com.fasterxml.jackson.annotation.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="department")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "deptId", scope = Long.class)
public class Department{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long deptId;
    @Column(unique = true)
    String departmentName;
    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnore
    List<Employee> employees = new ArrayList<>();

    public Department(){}

    public Department(String departmentName, List<Employee> employees)
    {
        this.departmentName=departmentName;
        this.employees=employees;
    }

    public Department(Long id, String departmentName, List<Employee> employees){
        this.deptId=id;
        this.departmentName=departmentName;
        this.employees=employees;
    }

    public Department(String departmentName){
        this.departmentName = departmentName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + deptId +
                ", departmentName='" + departmentName + '\'' +
                '}';
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }
}
