package com.glazaror.springboot.app.usuarios.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
//import org.springframework.data.rest.core.annotation.RestResource;

import com.glazaror.springboot.app.usuario.commons.model.entity.Usuario;

// Hacemos uso de data-rest... una facilidad para exponer nuestro repository (dao) a un endpoint rest
// Todos los metodos del crud y los metodos personalizados son exportados a nuestra api rest
@RepositoryRestResource(path = "usuarios")
public interface UsuarioDAO extends PagingAndSortingRepository<Usuario, Long>{

	// para exponer el metodo como endpoint RestResource
	//@RestResource(path = "buscar-username")
	public Usuario findByUsername(@Param("nombre") String username);
	
	@Query("select u from Usuario u where u.username = ?1")
	public Usuario obtenerPorUsername(String username);
}
