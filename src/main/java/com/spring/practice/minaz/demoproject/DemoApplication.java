package com.spring.practice.minaz.demoproject;

import com.spring.practice.minaz.demoproject.utility.DataUpload;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class,args);
		context.getBean(DataUpload.class).process();
		}

}
