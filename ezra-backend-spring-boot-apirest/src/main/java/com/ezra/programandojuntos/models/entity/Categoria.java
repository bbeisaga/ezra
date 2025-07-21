package com.ezra.programandojuntos.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "categorias")
@Getter
@Setter
public class Categoria implements Serializable {
	
	@Id
	private Long id;

	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "imagen")
	private String imagen;
	
	@Column(name = "orden")
	private Integer orden;
	
	private boolean activa;

	@JsonIgnoreProperties(value={"categoria", "hibernateLazyInitializer", "handler"}, allowSetters=true)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "categoria", cascade = CascadeType.ALL)
	private List<Producto> productos;

	
	private static final long serialVersionUID = 1L;

}
