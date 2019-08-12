package com.glazaror.springboot.app.items.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.glazaror.springboot.app.items.models.Item;
import com.glazaror.springboot.app.items.service.ItemService;

@RestController
@RequestMapping(path = "items")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@GetMapping
	public List<Item> listar() {
		return itemService.findAll();
	}

	@GetMapping(path="{id}/cantidad/{cantidad}")
	public Item detalle(@PathVariable Long id, @PathVariable Integer cantidad) {
		return itemService.findByItem(id, cantidad);
	}
}
