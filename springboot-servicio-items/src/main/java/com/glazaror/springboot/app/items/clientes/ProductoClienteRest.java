package com.glazaror.springboot.app.items.clientes;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.glazaror.springboot.app.items.models.Producto;

@FeignClient(name = "servicio-productos")
public interface ProductoClienteRest {

	@GetMapping(path = "/productos")
	public List<Producto> findAll();
	
	@GetMapping(path = "/productos/{id}")
	public Producto detalle(@PathVariable(name = "id") Long id);
	
	@PostMapping(path = "/productos")
	public Producto crear(@RequestBody Producto producto);
	
	@PutMapping(path = "/productos/{id}")
	public Producto update(@RequestBody Producto producto, @PathVariable(name = "id") Long id);
	
	@DeleteMapping(path = "/productos/{id}")
	public void delete(@PathVariable(name = "id") Long id);
}
