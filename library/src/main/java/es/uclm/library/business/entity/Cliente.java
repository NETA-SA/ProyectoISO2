package es.uclm.library.business.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Cliente extends Usuario {

	@ManyToMany
	@JoinTable(
			name = "cliente_favoritos",
			joinColumns = @JoinColumn(name = "cliente_id"),
			inverseJoinColumns = @JoinColumn(name = "restaurante_id")
	)
	private Collection<Restaurante> favoritos;

	@OneToMany(mappedBy = "cliente")
	private Collection<Pedido> pedidos;

	@OneToMany(mappedBy = "cliente")
	private Collection<Direccion> direcciones;

	private String nombre;
	private String apellidos;
	private String dni;

	// Constructores
	public Cliente() {}

	public Cliente(String nombre, String apellidos, String dni, Collection<Restaurante> favoritos,
				   Collection<Pedido> pedidos, Collection<Direccion> direcciones) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.dni = dni;
		this.favoritos = favoritos;
		this.pedidos = pedidos;
		this.direcciones = direcciones;
	}

	// Getters y Setters
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
