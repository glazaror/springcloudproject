package com.glazaror.springboot.app.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

// Habilitamos el config server (spring cloud config)
@EnableConfigServer
@SpringBootApplication
public class SpringbootServicioConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioConfigServerApplication.class, args);
	}

}
