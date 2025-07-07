package com.ezra.programandojuntos.models.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Data;



@Entity
@Table(name = "cajas")
@Data
public class Caja  implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Byte id;
	private String nombre;
	private String ubicacion;
	
	@JsonIgnoreProperties(value={"caja", "hibernateLazyInitializer", "handler"}, allowSetters=true)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "caja", cascade = CascadeType.ALL)
	private List<CajaUsuario> cajaUsuarios;
	

	private static final long serialVersionUID = 1L;
	
}
