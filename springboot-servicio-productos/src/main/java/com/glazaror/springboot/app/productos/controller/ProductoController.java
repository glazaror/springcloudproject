package com.glazaror.springboot.app.productos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.glazaror.springboot.app.productos.models.entity.Producto;
import com.glazaror.springboot.app.productos.service.IProductoService;

@RestController
@RequestMapping(path = "productos")
public class ProductoController {
	
	@Autowired
	private IProductoService productoService;
	
	@GetMapping
	public List<Producto> listar() {
		return productoService.findAll();
	}

	@GetMapping(path = "{id}")
	public Producto detalle(@PathVariable Long id) {
		return productoService.findById(id);
	}
	
}
