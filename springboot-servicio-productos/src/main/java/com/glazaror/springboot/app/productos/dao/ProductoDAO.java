package com.glazaror.springboot.app.productos.dao;

import org.springframework.data.repository.CrudRepository;

import com.glazaror.springboot.app.commons.model.entity.Producto;

public interface ProductoDAO extends CrudRepository<Producto, Long>{

}
