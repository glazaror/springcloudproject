package com.glazaror.springboot.app.zuul.oauth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
// Habilitamos la configuracion del servidor de recurso @EnableResourceServer 
@EnableResourceServer
// Extendemos el adapter ResourceServerConfigurerAdapter con el cual implementamos 2 metodos
// 1. Para proteger nuestras rutas endpoints
// 2. Para configurar el token (con la misma estructura que el servidor de autorizacion)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	// Para configurar el token
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		// invocamos el token store (el mismo definido para el authorization server)
		resources.tokenStore(tokenStore());
	}

	// Para configurar la proteccion de nuestras rutas endpoints
	@Override
	public void configure(HttpSecurity http) throws Exception {
		// Las rutas mas especificas deben ir al inicio (...andMatchers(....).andMatchers(...)...)
		// Al final se dejan de forma automatica las reglas que son mas genericas
		
		// Debemos especificar que todos pueden acceder al endpoint de generar token (todos pueden autenticarse, todos pueden iniciar sesion)
		// El endpoint /api/security/oauth/token debe ser de acceso publico -> permitAll() 
		//	--> Tambien puede especificarse con un pattern ** para indicar que todos los endpoints que comienzan con una url base: /api/security/oauth/**
		//	--> Tambien se aplica para todos los tipos de peticion (verbos) POST, GET, PUT, etc...
		// http.authorizeRequests().antMatchers("/api/security/oauth/token").permitAll()
		http.authorizeRequests().antMatchers("/api/security/oauth/**").permitAll()
		// Los endpoints de consulta de items, productos y usuarios deben ser de acceso publico (solo tipo peticion GET)
		.antMatchers(HttpMethod.GET, "/api/productos/productos", "/api/items/items", "/api/usuarios/usuarios").permitAll()
		// Los endpoints de consulta especifica de un item, un producto y un usuarios deben ser de permitido solo a usuarios que tienen el rol ADMIN o USER (solo tipo peticion GET)... ojo que aqui no se debe colocar el prefijo "ROLE_" ya que este es insertado de manera automatica 
		.antMatchers(HttpMethod.GET, "/api/productos/productos/{id}", "/api/items/items/{id}/cantidad/{cantidad}", "/api/usuarios/usuarios/{id}").hasAnyRole("ADMIN", "USER")
		// POST, PUT, DELETE solo para administradores
		// OPCION 1... detallada:
		/*.antMatchers(HttpMethod.POST, "/api/productos/productos", "/api/items/items", "/api/usuarios/usuarios").hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT, "/api/productos/productos/{id}", "/api/items/items/{id}", "/api/usuarios/usuarios/{id}").hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE, "/api/productos/productos/{id}", "/api/items/items/{id}", "/api/usuarios/usuarios/{id}").hasRole("ADMIN")*/
		// OPCION 2... simplificada: sin indicar los tipos de peticion (lo dejamos de forma generica para POST, PUT y DELETE)... indicando que el CRUD esta permitido solo para administradores
		.antMatchers("/api/productos/**", "/api/items/**", "/api/usuarios/**").hasRole("ADMIN")
		// Finalmente la regla mas generica es la ruta que no se haya configurado (que no se haya especificado en las reglas) la configuramos para que requiera autenticacion
		// anyRequest: cualquier ruta que no haya sido configurada (que no se haya especificado como regla)
		// authenticated: requiere autenticacion
		.anyRequest().authenticated();
	}
	
	// Copiamos el tokenStore y accessTokenConverter que fueron definidos en el "authorization server"... ya que debemos usar la misma estructura para validar el token... incluido el mismo signing key usado para firmar el token
	// utilizamos el tipo concreto JwtTokenStore para poder generar el token y almacenarlo.
	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}
	
	// utilizamos el tipo concreto JwtAccessTokenConverter
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
		// tenemos que colocar un codigo secreto para firmar el token (debe ser unico)
		// este mismo codigo secreto se utilizara en el servidor de recurso para validar que el token sea el correcto con la misma firma
		// y asi dar acceso a los clientes a los recursos protegidos de nuestros microservicios... esto se configurara en el zuul server
		
		// luego se pasara el codigo secreto al servidor de configuracion
		// la idea es que este codigo secreto sea un texto bien ofuscado
		tokenConverter.setSigningKey("algun_codigo_secreto_aeiou");
		return tokenConverter;
	}
}
