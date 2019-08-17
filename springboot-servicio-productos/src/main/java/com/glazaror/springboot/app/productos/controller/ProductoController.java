package com.glazaror.springboot.app.productos.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
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
	private Environment environment;
	
	@Value("${server.port}")
	private String puerto;
	
	@Autowired
	private IProductoService productoService;
	
	@GetMapping
	public List<Producto> listar() {
		return productoService.findAll().stream().map(producto -> { 
				producto.setPuerto(puerto);
				return producto;
			}
		).collect(Collectors.toList());
	}

	@GetMapping(path = "{id}")
	public Producto detalle(@PathVariable Long id) throws Exception {
		Producto producto = productoService.findById(id);
		producto.setPuerto(puerto);
		// para probar excepciones con timeout
		Thread.sleep(2000L);
		return producto;
	}
	
}
