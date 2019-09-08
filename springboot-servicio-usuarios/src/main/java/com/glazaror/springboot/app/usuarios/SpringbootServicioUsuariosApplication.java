package com.glazaror.springboot.app.usuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

//Ahora las entity se encuentran en otro package distinto al de este proyecto
//por lo tanto debemos especificar el nombre del paquete desde este proyecto para que se pueda inicializar
@EntityScan(basePackages = {"com.glazaror.springboot.app.usuario.commons.model.entity"})
@SpringBootApplication
public class SpringbootServicioUsuariosApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioUsuariosApplication.class, args);
	}

}
