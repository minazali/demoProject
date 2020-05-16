package com.spring.practice.minaz.demoproject.repositories;

import com.spring.practice.minaz.demoproject.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByFirstname(String firstname);
}
