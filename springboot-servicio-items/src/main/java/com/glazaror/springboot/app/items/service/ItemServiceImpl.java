package com.glazaror.springboot.app.items.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.glazaror.springboot.app.items.models.Item;
import com.glazaror.springboot.app.items.models.Producto;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private RestTemplate clienteRest;
	
	@Override
	public List<Item> findAll() {
		List<Producto> productos = Arrays.asList(clienteRest.getForObject("http://localhost:8001/productos", Producto[].class));
		return productos.stream().map(producto -> new Item(producto, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findByItem(Long id, Integer cantidad) {
		Producto producto = clienteRest.getForObject("http://localhost:8001/productos/{id}", Producto.class, id);
		return new Item(producto, cantidad);
	}

}
