package com.spring.practice.minaz.demoproject.controller;


import com.spring.practice.minaz.demoproject.exceptions.ResourceNotFoundException;
import com.spring.practice.minaz.demoproject.model.Department;
import com.spring.practice.minaz.demoproject.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("")
    public List<Department> getAllDepartments() {
        return departmentService.findAll();
    }

    @GetMapping("/{deptId}")
    public ResponseEntity<Object> getByDepartmentId(@PathVariable long deptId) {
        Department department = departmentService.findById(deptId);
        if(department!=null){
            return  ResponseEntity.ok(department);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Department with id "+ deptId+ " not found");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteDepartment(@PathVariable long id){
        departmentService.deleteDepartment(id);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<Object> resourceNotFoundException(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rosource not found");
    }
}
