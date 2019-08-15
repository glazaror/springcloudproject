package com.glazaror.springboot.app.items;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

// Habilitamos Hystrix en nuestro proyecto (hystrix implementa el patron de diseño circuit breaker)... maneja los posibles errores (latencia, timeouts)... evita errores en cascada que se pueden presentar en el ecosistema de nuestra aplicacion 
@EnableCircuitBreaker
// EnableEurekaClient es habilitado de forma implicita al declarar dependencia en el build.gradle
// sin embargo como buena practica es mejor declarar de forma explicita
@EnableEurekaClient
// Con Eureka ya no se necesita especificar el nombre del servicio con ribbon... viene implicito con Eureka
// @RibbonClient(name="servicio-productos")
// Feign si es necesario, por lo que lo vamos a usar como cliente para conectarnos a la API REST
@EnableFeignClients
@SpringBootApplication
public class SpringbootServicioItemsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioItemsApplication.class, args);
	}

}
