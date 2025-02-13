package com.ezra.programandojuntos.models.entity;

import java.io.Serializable;

import jakarta.persistence.*;



@Entity
@Table(name="tipo_movimientos")
public class TipoMovimiento implements Serializable{
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String movimiento;
	private String tipo;
	

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
