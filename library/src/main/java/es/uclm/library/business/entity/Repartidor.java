package es.uclm.library.business.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Repartidor extends Usuario {

	@OneToMany(mappedBy = "repartidor")
	private Collection<ServicioEntrega> servicios;

	@ManyToMany
	@JoinTable(
			name = "repartidor_zonas",
			joinColumns = @JoinColumn(name = "repartidor_id"),
			inverseJoinColumns = @JoinColumn(name = "codigo_postal_id")
	)
	private Collection<CodigoPostal> zonas;

	@Column(nullable = false)
	private String nombre;

	@Column(nullable = false)
	private String apellidos;

	@Column(nullable = false, unique = true)
	private String nif;

	private int eficiencia;

	// Constructor por defecto
	public Repartidor() {}

	// Constructor con parámetros
	public Repartidor(String nombre, String apellidos, String nif, int eficiencia, Collection<ServicioEntrega> servicios, Collection<CodigoPostal> zonas) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.nif = nif;
		this.eficiencia = eficiencia;
		this.servicios = servicios;
		this.zonas = zonas;
	}

	// Getters y Setters
	public Collection<ServicioEntrega> getServicios() {
		return servicios;
	}

	public void setServicios(Collection<ServicioEntrega> servicios) {
		this.servicios = servicios;
	}

	public Collection<CodigoPostal> getZonas() {
		return zonas;
	}

	public void setZonas(Collection<CodigoPostal> zonas) {
		this.zonas = zonas;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public int getEficiencia() {
		return eficiencia;
	}

	public void setEficiencia(int eficiencia) {
		this.eficiencia = eficiencia;
	}
}
