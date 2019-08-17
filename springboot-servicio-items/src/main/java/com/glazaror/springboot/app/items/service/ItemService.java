package com.glazaror.springboot.app.items.service;

import java.util.List;

import com.glazaror.springboot.app.items.models.Item;
import com.glazaror.springboot.app.items.models.Producto;

public interface ItemService {

	public List<Item> findAll();
	
	public Item findByItem(Long id, Integer cantidad);
	
	public Producto save(Producto producto);
	
	public Producto update(Producto producto, Long id);
	
	public void delete(Long id);
}
