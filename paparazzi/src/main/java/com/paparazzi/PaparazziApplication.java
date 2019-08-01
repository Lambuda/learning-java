package com.paparazzi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.paparazzi")
public class PaparazziApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaparazziApplication.class, args);
	}

}
