// Cliente.java
package es.uclm.library.business.entity;

import jakarta.persistence.*;
import java.util.Collection;

@Entity

public class Cliente {

	@ManyToMany
	@JoinTable(
			name = "cliente_favoritos",
			joinColumns = @JoinColumn(name = "cliente_id"),
			inverseJoinColumns = @JoinColumn(name = "restaurante_id")
	)
	private Collection<Restaurante> favoritos;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "idUsuario",referencedColumnName = "idUsuario", nullable = false)
	private Usuario usuario;

	@OneToMany(mappedBy = "cliente")
	private Collection<Pedido> pedidos;

	@OneToMany(mappedBy = "cliente")
	private Collection<Direccion> direcciones;

	private String nombre;
	private String apellidos;
	private String dni;

	// Constructors
	public Cliente() {}

	public Cliente(Usuario usuario, String nombre, String apellidos, String dni) {
		this.usuario = usuario;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.dni = dni;
	}

	// Getters and Setters
	public Collection<Restaurante> getFavoritos() {
		return favoritos;
	}

	public void setFavoritos(Collection<Restaurante> favoritos) {
		this.favoritos = favoritos;
	}

	public Collection<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(Collection<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public Collection<Direccion> getDirecciones() {
		return direcciones;
	}

	public void setDirecciones(Collection<Direccion> direcciones) {
		this.direcciones = direcciones;
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

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}
}