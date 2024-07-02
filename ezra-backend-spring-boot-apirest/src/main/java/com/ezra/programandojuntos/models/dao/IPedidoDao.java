package com.ezra.programandojuntos.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.ezra.programandojuntos.models.entity.Pedido;

public interface IPedidoDao extends CrudRepository<Pedido, Long>{

}
