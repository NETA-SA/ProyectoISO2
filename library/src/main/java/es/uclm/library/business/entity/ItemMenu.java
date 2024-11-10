package es.uclm.library.business.entity;

import javax.persistence.*;

@Entity
@Table(name = "item_menu")
public class ItemMenu {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "tipo_item_menu_id")
	private TipoItemMenu tipo;

	@Column(nullable = false)
	private String nombre;

	@Column(nullable = false)
	private double precio;

	// Constructor por defecto
	public ItemMenu() {}

	// Constructor con parámetros
	public ItemMenu(TipoItemMenu tipo, String nombre, double precio) {
		this.tipo = tipo;
		this.nombre = nombre;
		this.precio = precio;
	}

	// Getters y Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoItemMenu getTipo() {
		return tipo;
	}

	public void setTipo(TipoItemMenu tipo) {
		this.tipo = tipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}
}
