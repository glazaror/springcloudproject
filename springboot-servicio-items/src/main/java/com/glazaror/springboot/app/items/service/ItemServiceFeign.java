package com.glazaror.springboot.app.items.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.glazaror.springboot.app.items.clientes.ProductoClienteRest;
import com.glazaror.springboot.app.items.models.Item;
import com.glazaror.springboot.app.items.models.Producto;

@Service
//@Primary
public class ItemServiceFeign implements ItemService {
	
	@Autowired
	private ProductoClienteRest clienteFeign;

	@Override
	public List<Item> findAll() {
		return clienteFeign.findAll().stream().map(producto -> new Item(producto, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findByItem(Long id, Integer cantidad) {
		Producto producto = clienteFeign.detalle(id);
		return new Item(producto, cantidad);
	}

	@Override
	public Producto save(Producto producto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Producto update(Producto producto, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

}
