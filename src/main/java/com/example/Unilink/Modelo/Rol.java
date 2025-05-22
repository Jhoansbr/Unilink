package com.example.Unilink.Modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "roles") // Opcional, si tu tabla no se llama "rol"
public class Rol {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idRol") // Para asegurar coincidencia con User.java
	private Long idRol;

	@Column(name = "nombre_rol", nullable = false, unique = true)
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
