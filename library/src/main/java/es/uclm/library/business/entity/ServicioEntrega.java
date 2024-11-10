package es.uclm.library.business.entity;

import javax.persistence.*;
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
	@JoinColumn(name = "direccion_id", nullable = false)
	private Direccion direccion;

	@ManyToOne
	@JoinColumn(name = "repartidor_id", nullable = false)
	private Repartidor repartidor;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_recepcion", nullable = false)
	private Date fechaRecepcion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_entrega")
	private Date fechaEntrega;

	// Constructor por defecto
	public ServicioEntrega() {}

	// Constructor con parámetros
	public ServicioEntrega(Pedido pedido, Direccion direccion, Repartidor repartidor, Date fechaRecepcion, Date fechaEntrega) {
		this.pedido = pedido;
		this.direccion = direccion;
		this.repartidor = repartidor;
		this.fechaRecepcion = fechaRecepcion;
		this.fechaEntrega = fechaEntrega;
	}

	// Getters y Setters
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

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
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
