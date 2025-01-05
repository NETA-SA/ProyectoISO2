// Repartidor.java
package es.uclm.library.business.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Repartidor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario", nullable = false)
	private Usuario usuario;

	@OneToMany(mappedBy = "repartidor")
	private List<ServicioEntrega> serviciosEntrega;

	@Column(nullable = false)
	private boolean disponible;

	// Default constructor
	public Repartidor() {
		this.disponible = true; // Default to available
	}

	// Constructor with parameters
	public Repartidor(Usuario usuario) {
		this.usuario = usuario;
		this.disponible = true; // Default to available
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

	public List<ServicioEntrega> getServiciosEntrega() {
		return serviciosEntrega;
	}

	public void setServiciosEntrega(List<ServicioEntrega> serviciosEntrega) {
		this.serviciosEntrega = serviciosEntrega;
	}

	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}
}