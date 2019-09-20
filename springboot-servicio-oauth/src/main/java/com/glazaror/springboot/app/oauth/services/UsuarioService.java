package com.glazaror.springboot.app.oauth.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.glazaror.springboot.app.oauth.client.UsuarioFeignClient;
import com.glazaror.springboot.app.usuario.commons.model.entity.Usuario;

import feign.FeignException;

@Service
// UserDetailsService es especial... es propia de Spring Security
public class UsuarioService implements UserDetailsService, IUsuarioService {
	
	private Logger log = LoggerFactory.getLogger(UsuarioService.class);

	@Autowired
	private UsuarioFeignClient client;
	
	// Implementamos metodo que se encarga de autenticar... de obtener el usuario por el username... independiente de que si estamos usando jpa, jdbc, microservicios (usando api rest)
	// Este metodo retorna un usuario autenticado (UserDetails... clase propia de Spring Security)
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		try {
			// Usando feign estamos usando balanceo de carga automaticamente...
			Usuario usuario = client.findByUsername(username);
			
			List<GrantedAuthority> authorities = usuario.getRoles()
					.stream()
					.map(role -> new SimpleGrantedAuthority(role.getNombre()))
					.peek(authority -> log.info("Role: " + authority.getAuthority()))
					.collect(Collectors.toList());
			
			log.info("Usuario autenticado: " + username);
			
			// La clase org.springframework.security.core.userdetails.User recibe como ultimo parametro la lista de roles asignados al usuario
			// La lista de roles es de un tipo especifico de Spring Security: GrantedAuthority
			return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
			
		} catch (FeignException e) {
			log.error("Error en el login, no existe el usuario '" + username + "'en el sistema");
			throw new UsernameNotFoundException("Error en el login, no existe el usuario '" + username + "'en el sistema");
		}
	}

	@Override
	public Usuario findByUsername(String username) {
		return client.findByUsername(username);
	}

	@Override
	public Usuario update(Usuario usuario, Long id) {
		return client.update(usuario, id);
	}

}
