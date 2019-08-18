package com.glazaror.springboot.app.productos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

//EnableEurekaClient es habilitado de forma implicita al declarar dependencia en el build.gradle
//sin embargo como buena practica es mejor declarar de forma explicita
@EnableEurekaClient
@SpringBootApplication
// Ahora las entity se encuentran en otro package distinto al de este proyecto
// por lo tanto debemos especificar el nombre del paquete desde este proyecto para que se pueda inicializar
@EntityScan(basePackages = {"com.glazaror.springboot.app.commons.model.entity"})
public class SpringbootServicioProductosApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioProductosApplication.class, args);
	}

}
