package com.glazaror.springboot.app.productos.service;

import java.util.List;

import com.glazaror.springboot.app.productos.models.entity.Producto;

public interface IProductoService {

	public List<Producto> findAll();
	
	public Producto findById(Long id);
}
