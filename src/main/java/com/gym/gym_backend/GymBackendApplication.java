package com.gym.gym_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class GymBackendApplication {

	public static void main(String[] args) {

		SpringApplication app = new SpringApplication(GymBackendApplication.class);

		Map<String, Object> props = new HashMap<>();
		props.put("server.address", "0.0.0.0");
		props.put("server.port", "8080");

		app.setDefaultProperties(props);
		app.run(args);
	}
}
