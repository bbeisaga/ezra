package com.ezra.programandojuntos.models.entity;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "tipo_pedido")
@Getter
@Setter
public class TipoPedido implements Serializable{
	
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	private static final long serialVersionUID = 1L;
}
