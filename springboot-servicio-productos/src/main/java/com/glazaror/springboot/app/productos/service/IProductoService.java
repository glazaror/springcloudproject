package com.glazaror.springboot.app.productos.service;

import java.util.List;

import com.glazaror.springboot.app.commons.model.entity.Producto;

public interface IProductoService {

	public List<Producto> findAll();
	
	public Producto findById(Long id);
	
	public Producto save(Producto producto);
	
	public void deleteById(Long id);
}
