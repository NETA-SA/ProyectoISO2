package es.uclm.library.business.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "carta_menu")
public class CartaMenu {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "restaurante_id")
	private Restaurante restaurante;

	@OneToMany
	@JoinColumn(name = "carta_menu_id")
	private Collection<ItemMenu> items = new ArrayList<>(); // Inicialización aquí

	private String nombre;

	// Constructores
	public CartaMenu() {
		this.items = new ArrayList<>(); // Inicialización en el constructor predeterminado
	}

	public CartaMenu(Restaurante restaurante, Collection<ItemMenu> items, String nombre) {
		this.restaurante = restaurante;
		this.items = (items != null) ? items : new ArrayList<>(); // Validación si se pasa null
		this.nombre = nombre;
	}

	// Getters y Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Restaurante getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
	}

	public Collection<ItemMenu> getItems() {
		return items;
	}

	public void setItems(Collection<ItemMenu> items) {
		this.items = items;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
