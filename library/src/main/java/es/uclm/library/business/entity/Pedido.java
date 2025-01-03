package es.uclm.library.business.entity;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "pedido")
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = false)
	private Cliente cliente;

	@OneToOne
	@JoinColumn(name = "pago_id")
	private Pago pago;

	@OneToMany
	@JoinColumn(name = "pedido_id")
	private Collection<ItemPedido> items;

	@ManyToOne
	@JoinColumn(name = "restaurante_id", nullable = false)
	private Restaurante restaurante;

	@OneToOne
	@JoinColumn(name = "servicio_entrega_id")
	private ServicioEntrega entrega;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private EstadoPedido estado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date fecha;

	// Constructores
	public Pedido() {}

	public Pedido(Cliente cliente, Pago pago, Collection<ItemPedido> items, Restaurante restaurante, ServicioEntrega entrega, EstadoPedido estado, Date fecha) {
		this.cliente = cliente;
		this.pago = pago;
		this.items = items;
		this.restaurante = restaurante;
		this.entrega = entrega;
		this.estado = estado;
		this.fecha = fecha;
	}

	// Getters y Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Pago getPago() {
		return pago;
	}

	public void setPago(Pago pago) {
		this.pago = pago;
	}

	public Collection<ItemPedido> getItems() {
		return items;
	}

	public void setItems(Collection<ItemPedido> items) {
		this.items = items;
	}

	public Restaurante getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
	}

	public ServicioEntrega getEntrega() {
		return entrega;
	}

	public void setEntrega(ServicioEntrega entrega) {
		this.entrega = entrega;
	}

	public EstadoPedido getEstado() {
		return estado;
	}

	public void setEstado(EstadoPedido estado) {
		this.estado = estado;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	// Métodos para añadir y eliminar ítems
	public void addItem(ItemPedido itemPedido) {
		this.items.add(itemPedido);
	}

	public void deleteItem(ItemPedido itemPedido) {
		this.items.remove(itemPedido);
	}
}