package es.uclm.library.business.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "pago")
public class Pago {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "pedido_id")
	private Pedido pedido;

	@Enumerated(EnumType.STRING)
	@Column(name = "metodo_pago", nullable = false)
	private MetodoPago tipo;

	@Column(name = "id_transaccion", unique = true, nullable = false)
	private UUID idTransaccion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_transaccion", nullable = false)
	private Date fechaTransaccion;

	// Constructor por defecto
	public Pago() {}

	// Constructor con parámetros
	public Pago(Pedido pedido, MetodoPago tipo, UUID idTransaccion, Date fechaTransaccion) {
		this.pedido = pedido;
		this.tipo = tipo;
		this.idTransaccion = idTransaccion;
		this.fechaTransaccion = fechaTransaccion;
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

	public MetodoPago getTipo() {
		return tipo;
	}

	public void setTipo(MetodoPago tipo) {
		this.tipo = tipo;
	}

	public UUID getIdTransaccion() {
		return idTransaccion;
	}

	public void setIdTransaccion(UUID idTransaccion) {
		this.idTransaccion = idTransaccion;
	}

	public Date getFechaTransaccion() {
		return fechaTransaccion;
	}

	public void setFechaTransaccion(Date fechaTransaccion) {
		this.fechaTransaccion = fechaTransaccion;
	}
}
