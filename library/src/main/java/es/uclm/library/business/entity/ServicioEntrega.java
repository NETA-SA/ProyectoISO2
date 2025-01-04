// ServicioEntrega.java
package es.uclm.library.business.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "servicio_entrega")
public class ServicioEntrega {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "pedido_id", nullable = false)
	private Pedido pedido;

	@ManyToOne
	@JoinColumn(name = "direccion_cliente_id", nullable = false)
	private Direccion direccionCliente;

	@ManyToOne
	@JoinColumn(name = "direccion_restaurante_id", nullable = false)
	private Direccion direccionRestaurante;

	@ManyToOne
	@JoinColumn(name = "repartidor_id", nullable = false)
	private Repartidor repartidor;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_recepcion", nullable = false)
	private Date fechaRecepcion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_entrega")
	private Date fechaEntrega;

	// Default constructor
	public ServicioEntrega() {}

	// Constructor with parameters
	public ServicioEntrega(Pedido pedido, Direccion direccionCliente, Direccion direccionRestaurante, Repartidor repartidor, Date fechaRecepcion, Date fechaEntrega) {
		this.pedido = pedido;
		this.direccionCliente = direccionCliente;
		this.direccionRestaurante = direccionRestaurante;
		this.repartidor = repartidor;
		this.fechaRecepcion = fechaRecepcion;
		this.fechaEntrega = fechaEntrega;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Direccion getDireccionCliente() {
		return direccionCliente;
	}

	public void setDireccionCliente(Direccion direccionCliente) {
		this.direccionCliente = direccionCliente;
	}

	public Direccion getDireccionRestaurante() {
		return direccionRestaurante;
	}

	public void setDireccionRestaurante(Direccion direccionRestaurante) {
		this.direccionRestaurante = direccionRestaurante;
	}

	public Repartidor getRepartidor() {
		return repartidor;
	}

	public void setRepartidor(Repartidor repartidor) {
		this.repartidor = repartidor;
	}

	public Date getFechaRecepcion() {
		return fechaRecepcion;
	}

	public void setFechaRecepcion(Date fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}

	public Date getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}
}