package com.glazaror.springboot.app.oauth.services;

import com.glazaror.springboot.app.usuario.commons.model.entity.Usuario;

public interface IUsuarioService {

	public Usuario findByUsername(String username);
}
