package com.glazaror.springboot.app.oauth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService usuarioService;
	
	// para que se guarde en el contenedor de spring usamos @Bean... y lo podamos utilizar para encriptar nuestras contrasenas
	// tanto para la autenticacion (metodo configure(AuthenticationManagerBuilder)) como para configurar oauth2
	// para registrar beans de spring a nivel de metodo usamos @Bean (a diferencia de @Component, @Service las cuales son usadas para nuestras clases personalizadas)
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// registramos el usuarioService en el "authentication manager"
	// aqui se inyecta el parametro AuthenticationManagerBuilder... para q sea reconocido lo anotamos con @Autowired
	@Override
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// tenemos que encriptar la contrasena con Bcrypt (funcion hash) para dar mas seguridad al password
		// Cuando el usuario inicia sesion ingresa su contrasena, automaticamente el sistema va a encriptar ese password en bcrypt
		// Y lo va a comparar con el password que esta en base datos (que tambien tiene que estar en bcrypt)
		auth.userDetailsService(usuarioService).passwordEncoder(passwordEncoder());
	}
	
	// por ultimo tenemos que configurar el authentication manager
	// lo tenemos que registrar tambien como componente de spring, para que despues lo podamos utilizar/inyectar en la configuracion del servidor de autorizacion oauth2
	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManager();
	}
}
