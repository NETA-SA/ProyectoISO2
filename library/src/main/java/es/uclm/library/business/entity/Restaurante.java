package es.uclm.library.business.entity;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
public class Restaurante extends Usuario {

	@OneToMany(mappedBy = "restaurante")
	private Collection<Pedido> pedidos;

	@OneToMany(mappedBy = "restaurante")
	private Collection<CartaMenu> cartasMenu;

	@OneToOne
	@JoinColumn(name = "direccion_id")
	private Direccion direccion;

	@Column(nullable = false)
	private String nombre;

	@Column(nullable = false, unique = true)
	private String cif;

	// Constructor por defecto
	public Restaurante() {}

	// Constructor con parámetros
	public Restaurante(String nombre, String cif, Direccion direccion, Collection<Pedido> pedidos, Collection<CartaMenu> cartasMenu) {
		this.nombre = nombre;
		this.cif = cif;
		this.direccion = direccion;
		this.pedidos = pedidos;
		this.cartasMenu = cartasMenu;
	}

	// Getters y Setters
	public Collection<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(Collection<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public Collection<CartaMenu> getCartasMenu() {
		return cartasMenu;
	}

	public void setCartasMenu(Collection<CartaMenu> cartasMenu) {
		this.cartasMenu = cartasMenu;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	// Método para listar menú (esqueleto)
	public List<ItemMenu> listarMenu(String idRestaurante) {
		// TODO - implementar lógica para listar el menú
		throw new UnsupportedOperationException();
	}
}
