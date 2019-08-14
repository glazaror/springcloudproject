package com.glazaror.springboot.app.items.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.glazaror.springboot.app.items.models.Item;
import com.glazaror.springboot.app.items.models.Producto;
import com.glazaror.springboot.app.items.service.ItemService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping(path = "items")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@GetMapping
	public List<Item> listar() {
		return itemService.findAll();
	}

	// anotacion hystrix para utilizar caminos alternativos en caso de presentarse algun error
	// para evitar errores en cascada que se pueden presentar en nuestro sistema
	@HystrixCommand(fallbackMethod = "metodoAlternativo")
	@GetMapping(path="{id}/cantidad/{cantidad}")
	public Item detalle(@PathVariable Long id, @PathVariable Integer cantidad) {
		return itemService.findByItem(id, cantidad);
	}
	
	// metodo alternativo invocado por hystrix en caso de error... de esta forma evitamos errores en cascada que se pueden presentar en nuestro sistema
	public Item metodoAlternativo(Long id, Integer cantidad) {
		Item item = new Item();
		item.setCantidad(cantidad);
		Producto producto = new Producto();
		producto.setId(id);
		producto.setNombre("Camara Sony");
		producto.setPrecio(500.00);
		item.setProducto(producto);
		return item;
	}
}
