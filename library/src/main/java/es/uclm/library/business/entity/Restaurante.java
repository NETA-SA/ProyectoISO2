package es.uclm.library.business.entity;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
public class Restaurante {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "idUsuario", nullable = false)
	private Usuario usuario;

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

	@ManyToOne
	@JoinColumn(name = "codigo_postal_id", nullable = false)
	private CodigoPostal codigoPostal;

	@OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL)
	private List<ItemMenu> items;

	// Default constructor
	public Restaurante() {}

	// Constructor with parameters
	public Restaurante(Usuario usuario, String nombre, String cif, Direccion direccion, CodigoPostal codigoPostal, Collection<Pedido> pedidos, Collection<CartaMenu> cartasMenu, List<ItemMenu> items) {
		this.usuario = usuario;
		this.nombre = nombre;
		this.cif = cif;
		this.direccion = direccion;
		this.codigoPostal = codigoPostal;
		this.pedidos = pedidos;
		this.cartasMenu = cartasMenu;
		this.items = items;
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

	public CodigoPostal getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(CodigoPostal codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public List<ItemMenu> getItems() {
		return items;
	}

	public void setItems(List<ItemMenu> items) {
		this.items = items;
	}

	public List<ItemMenu> listarMenu(String idRestaurante) {
		return items;
	}
}