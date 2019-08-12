package com.glazaror.springboot.app.items.service;

import java.util.List;

import com.glazaror.springboot.app.items.models.Item;

public interface ItemService {

	public List<Item> findAll();
	
	public Item findByItem(Long id, Integer cantidad);
}
