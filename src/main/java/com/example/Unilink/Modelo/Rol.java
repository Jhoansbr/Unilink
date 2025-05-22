package com.example.Unilink.Modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "rol")
public class Rol {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idRol")
	private Long idRol; // Cambiado a Long para coincidir con BIGINT

	@Column(name = "nombre_rol", nullable = false)
	private String nombreRol;

	// Getters y Setters
	public Long getIdRol() {
		return idRol;
	}

	public void setIdRol(Long idRol) {
		this.idRol = idRol;
	}

	public String getNombreRol() {
		return nombreRol;
	}

	public void setNombreRol(String nombreRol) {
		this.nombreRol = nombreRol;
	}
}