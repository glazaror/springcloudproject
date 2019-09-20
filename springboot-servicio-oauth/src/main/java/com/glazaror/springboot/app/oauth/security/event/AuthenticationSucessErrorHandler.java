package com.glazaror.springboot.app.oauth.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

// Creamos un handler de manejo de exito y fracaso en autenticacion
// Tiene que ser un componente de Spring para que se pueda inyectar en la clase de configuracion de Spring Security
@Component
public class AuthenticationSucessErrorHandler implements AuthenticationEventPublisher {
	
	private Logger log = LoggerFactory.getLogger(AuthenticationSucessErrorHandler.class);

	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		UserDetails user = (UserDetails) authentication.getPrincipal();
		// obtenemos el nombre de usuario para pintar en el log... o guardar en bd.. etc
		String mensaje = "Success Login: " + user.getUsername();
		System.out.println(mensaje);
		log.info(mensaje);
	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		String mensaje = "Error en el Login: " + exception.getMessage();
		System.out.println(mensaje);
		log.info(mensaje);
	}

}
