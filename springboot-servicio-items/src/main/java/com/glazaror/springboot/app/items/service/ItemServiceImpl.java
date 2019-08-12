package com.glazaror.springboot.app.items.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.glazaror.springboot.app.items.models.Item;
import com.glazaror.springboot.app.items.models.Producto;

@Service
//@Primary
public class ItemServiceImpl implements ItemService {

	@Autowired
	private RestTemplate clienteRest;
	
	@Override
	public List<Item> findAll() {
		// en vez de indicar servidor:puerto (Ejemplo localhost:8001) ahora indicamos el nombre del servicio... en este caso "servicio-productos" tal como esta indicado en el application.properties
		List<Producto> productos = Arrays.asList(clienteRest.getForObject("http://servicio-productos/productos", Producto[].class));
		return productos.stream().map(producto -> new Item(producto, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findByItem(Long id, Integer cantidad) {
		// en vez de indicar servidor:puerto (Ejemplo localhost:8001) ahora indicamos el nombre del servicio... en este caso "servicio-productos" tal como esta indicado en el application.properties 
		Producto producto = clienteRest.getForObject("http://servicio-productos/productos/{id}", Producto.class, id);
		return new Item(producto, cantidad);
	}

}
