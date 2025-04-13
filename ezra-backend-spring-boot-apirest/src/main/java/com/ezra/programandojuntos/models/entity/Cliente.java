package com.ezra.programandojuntos.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "clientes")
@Getter
@Setter
public class Cliente implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

//	@NotEmpty(message = "no puede estar vacio")
//	@Size(min = 2, max = 25, message = "el tamaño tiene que estar entre 4 y 25")
//	@Column(nullable = false)
//	private String nombres;
//
//	@NotEmpty(message = "no puede estar vacio")
//	@Size(min = 2, max = 25, message = "el tamaño tiene que estar entre 4 y 25")
//	private String apellidos;
//	
//	@NotEmpty(message = "no puede estar vacio")
//	@Size(min = 2, message = "el tamaño muy pequeño")
//	private String razonSocial;
	
	@NotEmpty(message = "no puede estar vacio")
	@Size(min = 2, message = "el tamaño muy pequeño")
	@Column(name = "nom_apell_rz")
	private String nomApellRz;
	
	//@NotEmpty(message = "no puede estar vacio")
	private String direccion;


//	@NotNull(message = "no puede estar vacio")
	@Column(name = "create_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;

	@Column(name = "update_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateAt;

	@NotNull(message = "Tipo documento no puede ser vacio")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo_documentos_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private TipoDocumento tipoDocumento;
	
	@Column(name = "numero_documento", unique=true)
	private String numeroDocumento;
	
	@Column(name = "celular", unique=true)
	private String celular;

	@JsonIgnoreProperties(value={"cliente", "hibernateLazyInitializer", "handler"}, allowSetters=true)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente", cascade = CascadeType.ALL)
	private List<Pedido> pedidos;

	public Cliente() {
		this.pedidos = new ArrayList<>();
	}
	
	@PrePersist
	public void prePersist() {
		this.createAt = new Date();
	}


	private static final long serialVersionUID = 1L;
}
