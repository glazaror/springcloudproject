package com.glazaror.springboot.app.usuarios;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

import com.glazaror.springboot.app.usuario.commons.model.entity.Rol;
import com.glazaror.springboot.app.usuario.commons.model.entity.Usuario;

// Por defecto no se exponen los id de los entity en las respuesta de los endpoint data rest
// para esto adicionamos la siguiente configuracion opcional para exponer los ids
@Configuration
public class RepositoryConfig implements RepositoryRestConfigurer {

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		config.exposeIdsFor(Usuario.class, Rol.class);
	}

}
