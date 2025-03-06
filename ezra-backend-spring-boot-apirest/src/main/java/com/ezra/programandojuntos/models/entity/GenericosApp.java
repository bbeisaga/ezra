package com.ezra.programandojuntos.models.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "genericos_app")
@Getter
@Setter
public class GenericosApp {
	@Id
	private String codigo;
	private BigDecimal valor1;
	private Long valor2;


	private static final long serialVersionUID = 1L;

}
