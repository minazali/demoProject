package com.spring.practice.minaz.demoproject;

import com.spring.practice.minaz.demoproject.model.Employee;
import com.spring.practice.minaz.demoproject.services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

	/*@Test
	void contextLoads() {
	}*/
	// bind the above RANDOM_PORT
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private EmployeeService employeeService;

	@Test
	void getEmployees()
	{
		List<Employee> employeeList= employeeService.getEmployees();
		System.out.println(employeeList);
	}

	@Test
	void getControlletEmployees() throws Exception{
		List<Employee> employeeList= employeeService.getEmployees();
		ResponseEntity<String> response =
				restTemplate.getForEntity(
				new URL("http://localhost:" + port + "/api/employees").toString(), String.class);
		assertEquals(employeeList, response.getBody());
	}

}
