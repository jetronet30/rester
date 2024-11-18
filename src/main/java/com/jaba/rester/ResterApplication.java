package com.jaba.rester;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jaba.rester.initservices.InitServices;

@SpringBootApplication
public class ResterApplication {
	private static final InitServices initservices = new InitServices();

	public static void main(String[] args) {
		initservices.init_main();
		SpringApplication.run(ResterApplication.class, args);
	}

}
