package com.ezra.programandojuntos.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tipo_movimientos")
public class TipoMovimiento implements Serializable{
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Byte id;
	private String movimiento;
	private String tipo;
	
	public Byte getId() {
		return id;
	}
	public void setId(Byte id) {
		this.id = id;
	}

	
	
	public String getMovimiento() {
		return movimiento;
	}
	public void setMovimiento(String movimiento) {
		this.movimiento = movimiento;
	}



	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
