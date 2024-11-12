package es.uclm.library.business.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "item_menu")
public class ItemMenu {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "Tipo_Item_Menu", nullable = false)
	private TipoItemMenu tipo;

	@ManyToOne
	@JoinColumn(name = "restaurante_id")
	private Restaurante restaurante;

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
