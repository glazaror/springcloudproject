package com.glazaror.springboot.app.items.clientes;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.glazaror.springboot.app.items.models.Producto;

@FeignClient(name = "servicio-productos", url = "localhost:8001")
public interface ProductoClienteRest {

	@GetMapping(path = "/productos")
	public List<Producto> findAll();
	
	@GetMapping(path = "/productos/{id}")
	public Producto detalle(@PathVariable(name = "id") Long id);
}
