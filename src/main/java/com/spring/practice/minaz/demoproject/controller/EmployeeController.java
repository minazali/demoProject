package com.spring.practice.minaz.demoproject.controller;

import com.spring.practice.minaz.demoproject.exceptions.ResourceNotFoundException;
import com.spring.practice.minaz.demoproject.model.Employee;
import com.spring.practice.minaz.demoproject.services.DepartmentService;
import com.spring.practice.minaz.demoproject.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DepartmentService departmentService;

    @GetMapping("")
    public ResponseEntity<Object> getAllEmployees() {
        List<Employee> employees = employeeService.findAll();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<Object> getByEmployeeId(@PathVariable long employeeId) {
        Optional<Employee> optionalEmployee = employeeService.findById(employeeId);
        if(optionalEmployee.isPresent())
            return ResponseEntity.ok(optionalEmployee.get());
        else
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee with id "+ employeeId+ " not found");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable long id){
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(value = ResourceNotFoundException .class)
    public ResponseEntity<Object> resourceNotFoundException(){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Rosource not found");
    }
}
