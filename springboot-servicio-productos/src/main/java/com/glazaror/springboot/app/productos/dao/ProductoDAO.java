package com.glazaror.springboot.app.productos.dao;

import org.springframework.data.repository.CrudRepository;

import com.glazaror.springboot.app.productos.models.entity.Producto;

public interface ProductoDAO extends CrudRepository<Producto, Long>{

}
