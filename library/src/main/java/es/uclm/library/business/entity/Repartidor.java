package es.uclm.library.business.entity;

import jakarta.persistence.*;

@Entity
public class Repartidor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "idUsuario",referencedColumnName = "idUsuario", nullable = false)
	private Usuario usuario;

	// Other fields and methods

	// Default constructor
	public Repartidor() {}

	// Constructor with parameters
	public Repartidor(Usuario usuario) {
		this.usuario = usuario;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	// Other getters and setters
}