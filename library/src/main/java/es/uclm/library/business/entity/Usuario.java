package es.uclm.library.business.entity;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {

	@Id
	@Column(name = "idUsuario", nullable = false, unique = true)
	private String idUsuario;

	@Column(nullable = false)
	private String pass;

	private String  rol; // Ajustar según el propósito real

	// Constructor por defecto
	public Usuario() {}

	// Constructor con parámetros
	public Usuario(String idUsuario, String pass, String rol) {
		this.idUsuario = idUsuario;
		this.pass = pass;
		this.rol = rol;
	}

	// Getters y Setters
	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
}
