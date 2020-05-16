package com.spring.practice.minaz.demoproject.repositories;

import com.spring.practice.minaz.demoproject.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
     Department findByDepartmentName(String departmentName);
}
