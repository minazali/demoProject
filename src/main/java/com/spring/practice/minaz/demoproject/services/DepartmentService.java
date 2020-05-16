package com.spring.practice.minaz.demoproject.services;

import com.spring.practice.minaz.demoproject.exceptions.ResourceNotFoundException;
import com.spring.practice.minaz.demoproject.model.Department;
import com.spring.practice.minaz.demoproject.repositories.DepartmentRepository;
import com.spring.practice.minaz.demoproject.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    public void createDepartment(Department department){
        departmentRepository.save(department);
    }

    public List<Department> findAll(){
        return departmentRepository.findAll();
    }

    public Department findById(Long id)
    {
        return departmentRepository.findById(id).get();
    }

    public Department findByDepartmentName(String deptName){
        return departmentRepository.findByDepartmentName(deptName);
    }

    public void deleteDepartment(Long id){
        try {
            departmentRepository.deleteById(id);
        }catch(Exception e){
            throw new ResourceNotFoundException("Employee with id "+ id +" does not exist");
        }
    }

    public void saveAll(List<Department> departments){
        departmentRepository.saveAll(departments);
    }
}
