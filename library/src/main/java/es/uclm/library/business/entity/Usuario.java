package es.uclm.library.business.entity;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {

	@Id
	@Column(name = "id_usuario", nullable = false, unique = true)
	private String idUsuario;

	@Column(nullable = false)
	private String pass;

	private int attribute; // Ajustar según el propósito real

	// Constructor por defecto
	public Usuario() {}

	// Constructor con parámetros
	public Usuario(String idUsuario, String pass, int attribute) {
		this.idUsuario = idUsuario;
		this.pass = pass;
		this.attribute = attribute;
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

	public int getAttribute() {
		return attribute;
	}

	public void setAttribute(int attribute) {
		this.attribute = attribute;
	}
}
