package com.thorium.sampleapps.myecom.api;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = "com.thorium.sampleapps.myecom.api")
public class MyEcomApiApplication {

	private static final Logger LOGGER = Logger.getLogger(MyEcomApiApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MyEcomApiApplication.class, args);
	}
}
