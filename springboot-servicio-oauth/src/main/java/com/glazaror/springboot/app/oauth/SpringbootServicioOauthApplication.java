package com.glazaror.springboot.app.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
// Solo con fines de prueba implementamos CommandLineRunner para obtener los hash de la clave de los usuarios
public class SpringbootServicioOauthApplication implements CommandLineRunner {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioOauthApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String password = "12345";
		// Bcrypt nos permite obtener varios passwords encriptados a partir de un mismo valor password
		// En este ejemplo vamos a generar 4 passwords encriptados a partir del password 12345
		for (int i = 1; i < 4; i++) {
			System.out.println(passwordEncoder.encode(password));
		}
	}

}
