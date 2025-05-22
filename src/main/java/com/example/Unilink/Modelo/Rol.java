package com.example.Unilink.Modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity

public class Rol {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idRol;

	@Column(name = "nombre_rol")
	private String nombreRol;

	public String getNombreRol() {
		return nombreRol;
	}

	public Long getIdRol() {
		return idRol;
	}

	public void setIdRol(Long idRol) {
		this.idRol = idRol;
	}

	public void setNombreRol(String nombreRol) {
		this.nombreRol = nombreRol;
	}

}
