package com.api.web.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
@Table(name="logging")
public class Persona {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="usuario",nullable = false)
	@NotBlank
	private String nomUsuario;
	
	@Column(name="ap_paterno",nullable = false)
	@NotBlank
	private String apPaterno;
	
	@Column(name="apMaterno",nullable = false)
	@NotBlank
	private String apMaterno;
}

