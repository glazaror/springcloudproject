package com.glazaror.springboot.app.oauth.security;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

// Similar a SpringSecurityConfig pero con configuracion especifica de oauth2
@Configuration
// Habilitamos la clase como un servidor de autorizacion -> @EnableAuthorizationServer y extendemos AuthorizationServerConfigurerAdapter
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	// 1. inyectamos 2 atributos: BCryptPasswordEncoder y AuthenticationManager
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private InfoAdicionalToken infoAdicionalToken;

	// 2. Debemos implementar los 3 metodos de AuthorizationServerConfigurerAdapter
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		// Sirve para dar seguridad a los endpoints
		// Configuramos el permiso que van a tener nuestros endpoints del servidor de autorizacion de oauth2:
		// --> para generar el token
		// --> y para validar el token
		
		// tokenKeyAccess 	--> es el endpoint para generar el token (POST: /oauth/token), en el cual enviamos las credenciales del usuario y de la aplicacion cliente... 
		// 					--> la idea es que este endpoint sea publico (que cualquier cliente pueda acceder a esta ruta) para generar el token... por eso indicamos "permitAll()"
		
		// checkTokenAccess	--> se encarga de validar el token, esta ruta para validar token requiere autenticacion, para esto indicamos "isAuthenticated" el cual es un metodo de "Spring Security" que permite verificar si un usuario esta autenticado
		
		// Estos 2 endpoints (tokenKeyAccess y checkTokenAccess) estan protegidos por autenticacion via http BASIC utilizando las credenciales de la aplicacion cliente (CLIENT_ID, SECRET)
		// las credenciales de la aplicacion cliente se envian en las cabeceras dentro del authorization a diferencia del token que se envia como "bearer" 
		
		security.tokenKeyAccess("permitAll()")
		.checkTokenAccess("isAuthenticated()")
		;
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// Configurar nuestros clientes... las aplicaciones frontend que van a acceder a nuestros microservicios
		// puede ser una aplicacion con android, una aplicacion con angular, otra con react, etc.
		// De ser el caso si tenemos varios clientes entonces debemos registrar uno por uno con su CLIENT_ID y su SECRET
		
		// La idea del estandar oauth2 es proporcionar mayor seguridad, no solamente nos autenticamos con los usuarios de nuestro backend
		// sino tambien con las credenciales de nuestra aplicacion cliente
		// --> Podriamos decir que tenemos una doble autenticacion -> 1. El usuario 2. La aplicacion cliente
		
		// Para el caso de prueba vamos utilizar el tipo de almacenamiento inMemory... podriamos usar JDBC
		// --> para registrar un cliente usamos "withClient"... para registrar la contrasena encriptada para esto utilizamos: passwordEncoder
		// --> configuramos el alcance/permisos de la aplicacion cliente usando: "scopes"
		// --> configuramos el tipo de concesion que tendra nuestra autenticacion: "authorizedGrantTypes"... como vamos a obtener el token: en nuestro caso con PASSWORD es decir con credenciales (username y contrasena)
		//	   por ejemplo existen otros tipos de concesion como "authorizacion code" el cual no autentica un usuario con sus credenciales sino que es a traves de un codigo de autorizacion que nos entrega el backend y a traves de ese codigo de autorizacion obtenemos el token de acceso
		// --> configuramos la concesion "refresh_token" el cual es un token que nos permite obtener un nuevo token (token de acceso completamente renovado)... permite obtener un nuevo token justo antes de que caduque el token actual
		// --> Configuracion el tipo de validez del token... entero en segundos... 3600 = 1 hora
		// --> Configuracion del refresh token
		
		// Configuramos un cliente adicional (por ejemplo angularapp)
		
		clients.inMemory().withClient("frontendapp")
		.secret(passwordEncoder.encode("12345"))
		.scopes("read", "write")
		.authorizedGrantTypes("password", "refresh_token")
		.accessTokenValiditySeconds(3600)
		.refreshTokenValiditySeconds(3600)
		.and()
		.withClient("angularapp")
		.secret(passwordEncoder.encode("12345"))
		.scopes("read", "write")
		.authorizedGrantTypes("password", "refresh_token")
		.accessTokenValiditySeconds(3600)
		.refreshTokenValiditySeconds(3600)
		
		;
		
		super.configure(clients);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		// Esta configuracion es para el endpoint de oauth2 del servidor de autorizacion que se encarga de generar el token
		// --> /oauth/token
		// --> tipo POST
		// --> recibe el username, password, GRANT_TYPE (tipo otorgamiento del token... que sera del tipo PASSWORD)
		// --> asi tambien se recibe el CLIENT_ID y SECRET los cuales corresponden a las credenciales de la aplicacion cliente.
		// --> si es que todo sale correcto se genera el token con esos los datos y retorna un JSON con el token al usuario.
		// y despues el usuario puede utilizar ese token para acceder a los recursos protegidos (esto se realiza en otro microservicio -> servidor de recurso)
		
		// Debemos configurar 3 cosas:
		// 1. El authentication manager en el endpoint
		// 2. El token storage que debe ser de tipo JWT... componente que se encarga de generar el token con los datos del "access token converter"
		// 3. El access token converter (que debe ser de tipo JWT) que se encarga de guardar los datos del usuario en el token (datos como el username, roles, cualquier tipo de informacion adicional conocidos como claims)
		// 	  Por detras este componente se encarga de tomar estos valores del usuario y convertirlos en el token JWT codificado en base64
		
		// Creamos un chain de enhancers (incluyendo el access token converter y el infoAdicionalToken)
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		List<TokenEnhancer> tokenEnhancers = new ArrayList<TokenEnhancer>();
		tokenEnhancers.add(infoAdicionalToken);
		tokenEnhancers.add(accessTokenConverter());
		tokenEnhancerChain.setTokenEnhancers(tokenEnhancers);
		
		// adicionamos el tokenenhancerchain en el endpoint
		endpoints.authenticationManager(authenticationManager)
		.tokenStore(tokenStore())
		.accessTokenConverter(accessTokenConverter())
		.tokenEnhancer(tokenEnhancerChain);
	}
	
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
