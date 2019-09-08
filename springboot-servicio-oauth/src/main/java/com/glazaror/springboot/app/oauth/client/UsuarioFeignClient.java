package com.glazaror.springboot.app.oauth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.glazaror.springboot.app.usuario.commons.model.entity.Usuario;

@FeignClient(name="servicio-usuarios")
public interface UsuarioFeignClient {

	@GetMapping("/usuarios/search/findByUsername")
	public Usuario findByUsername(@RequestParam(name = "nombre") String username);
}
