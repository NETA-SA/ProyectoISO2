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

	public List<ServicioEntrega> getServiciosEntrega() {
		return serviciosEntrega;
	}

	public void setServiciosEntrega(List<ServicioEntrega> serviciosEntrega) {
		this.serviciosEntrega = serviciosEntrega;
	}
}