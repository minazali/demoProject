package com.spring.practice.minaz.demoproject.services;

import com.spring.practice.minaz.demoproject.exceptions.GenericException;
import com.spring.practice.minaz.demoproject.exceptions.ResourceNotFoundException;
import com.spring.practice.minaz.demoproject.model.Department;
import com.spring.practice.minaz.demoproject.repositories.EmployeeRepository;
import com.spring.practice.minaz.demoproject.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentService departmentService;

    public List<Employee> getEmployees(){
        List<Employee> list = employeeRepository.findAll();
        return list;
    }

    public void createEmployee(Employee e, Long deptId)
    {
        Department department = departmentService.findById(deptId);
        if(department!=null) {
            e.setDepartment(department);
            List<Employee> dl = department.getEmployees();
            dl.add(e);
        }
        employeeRepository.save(e);
    }

    public void createEmployee(String firstName, String departmentname){
        try {
            Department department = departmentService.findByDepartmentName(departmentname);
            Employee employee = employeeRepository.findByFirstname(firstName);
            if(employee==null) {
                employee = new Employee();
                employee.setFirstname(firstName);
            }
                if (department == null) {
                    department = new Department();
                    department.setDepartmentName(departmentname);
                }
                    employee.setDepartment(department);
                    List<Employee> dl = department.getEmployees();
                    dl.add(employee);

                employeeRepository.save(employee);
        }catch (Exception e){
           throw new GenericException("EmployeeService:Create failed : "+e.getMessage());
        }
    }

    public void deleteEmployee(Long id){
        try {
            employeeRepository.deleteById(id);
        }catch(Exception e){
           throw new ResourceNotFoundException("Employee with id "+ id +" does not exist");
        }
    }

    public Optional<Employee> findById(Long id)
    {
        return employeeRepository.findById(id);
    }

    public List<Employee> findAll(){

        return employeeRepository.findAll();
    }

    public boolean saveAll(List<Employee> employees){
        try {
            employeeRepository.saveAll(employees);
        }catch (Exception e){
            throw new GenericException("EmployeeService:Save All failed : "+e.getMessage());
        }
       return true;
    }

    public Employee findByFirstName(String firstname){
        return employeeRepository.findByFirstname(firstname);
    }


}
