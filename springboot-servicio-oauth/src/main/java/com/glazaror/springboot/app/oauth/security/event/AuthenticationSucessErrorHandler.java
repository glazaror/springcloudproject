package com.glazaror.springboot.app.oauth.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.glazaror.springboot.app.oauth.services.IUsuarioService;
import com.glazaror.springboot.app.usuario.commons.model.entity.Usuario;

import brave.Tracer;
import feign.FeignException;

// Creamos un handler de manejo de exito y fracaso en autenticacion
// Tiene que ser un componente de Spring para que se pueda inyectar en la clase de configuracion de Spring Security
@Component
public class AuthenticationSucessErrorHandler implements AuthenticationEventPublisher {
	
	private Logger log = LoggerFactory.getLogger(AuthenticationSucessErrorHandler.class);
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private Tracer tracer;

	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		UserDetails user = (UserDetails) authentication.getPrincipal();
		// obtenemos el nombre de usuario para pintar en el log... o guardar en bd.. etc
		String mensaje = "Success Login: " + user.getUsername();
		System.out.println(mensaje);
		log.info(mensaje);
		
		Usuario usuario = usuarioService.findByUsername(authentication.getName());
		if (usuario.getIntentos() != null && usuario.getIntentos() > 0) {
			usuario.setIntentos(0);
			usuarioService.update(usuario, usuario.getId());
		}
	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		String mensaje = "Error en el Login: " + exception.getMessage();
		System.out.println(mensaje);
		log.info(mensaje);
		
		try {
			
			StringBuilder errors = new StringBuilder();
			errors.append(mensaje);
			
			Usuario usuario = usuarioService.findByUsername(authentication.getName());
			if (usuario.getIntentos() == null) {
				usuario.setIntentos(0);
			}
			log.info("Intentos actual es de: " + usuario.getIntentos());
			usuario.setIntentos(usuario.getIntentos() + 1);
			log.info("Intentos despues es de: " + usuario.getIntentos());
			
			errors.append(" - Intentos despues es de: " + usuario.getIntentos());
			
			if (usuario.getIntentos() >= 3) {
				String errorMaximosIntentos = String.format("El usuario %s deshabilitado por maximos intentos", usuario.getUsername());
				log.error(errorMaximosIntentos);
				errors.append(" - " + errorMaximosIntentos);
				usuario.setEnabled(false);
			}
			usuarioService.update(usuario, usuario.getId());
			
			// pintamos el tag personalizado en el log (sleuth/zipkin)
			tracer.currentSpan().tag("error.mensaje", errors.toString());
			
		} catch (FeignException e) {
			log.error(String.format("El usuario %s no existe en el sistema", authentication.getName()));
		}
	}

}
