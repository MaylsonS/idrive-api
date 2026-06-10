package br.com.api.idrive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IdriveApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdriveApplication.class, args);
	}

}
