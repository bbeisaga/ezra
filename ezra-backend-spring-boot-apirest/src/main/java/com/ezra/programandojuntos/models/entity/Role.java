package com.ezra.programandojuntos.models.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;



@Entity
@Table(name="roles")
public class Role implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique=true, length=30)
	private String nombre;
	
	private String descripcion;
	
//	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	@JoinColumn(name = "modulo_id" )
//	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
//	private Modulo modulo;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
	
//	public Modulo getModulo() {
//		return modulo;
//	}
//
//	public void setModulo(Modulo modulo) {
//		this.modulo = modulo;
//	}



	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
