package com.ezra.programandojuntos.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "no puede estar vacio")
	@Size(min = 4, max = 12, message = "el tamaño tiene que estar entre 4 y 12")
	@Column(nullable = false)
	private String nombres;

	@NotEmpty(message = "no puede estar vacio")
	private String apellidos;

//	@NotEmpty(message = "no puede estar vacio")
//	@Email(message = "no es una dirección de correo bien formada")
//	@Column(nullable = false, unique = true)
//	private String email;

//	@NotNull(message = "no puede estar vacio")
	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;

//	private String foto;

//	@NotNull(message = "la región no puede ser vacia")
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "region_id")
//	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
//	private Region region;
	

	@NotNull(message = "Tipo documento no puede ser vacio")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo_documentos_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private TipoDocumento tipoDocumento;
	
	@Column(name = "numero_documento")
	private String numeroDocumento;
	
	@Column(name = "celular")
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

//	public String getFoto() {
//		return foto;
//	}
//
//	public void setFoto(String foto) {
//		this.foto = foto;
//	}

//	public Region getRegion() {
//		return region;
//	}
//
//	public void setRegion(Region region) {
//		this.region = region;
//	}
	
	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
	

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}


	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}



	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}



	private static final long serialVersionUID = 1L;
}
