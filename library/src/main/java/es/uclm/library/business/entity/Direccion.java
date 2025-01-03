// Direccion.java
package es.uclm.library.business.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "direccion")
public class Direccion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "codigo_postal", nullable = false)
	private CodigoPostal codigoPostal;

	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	@Column(nullable = false)
	private String calle;

	@Column(nullable = false)
	private String numero;

	private String complemento;
	private String municipio;

	// Constructor por defecto
	public Direccion() {}

	// Constructor con parámetros
	public Direccion(CodigoPostal codigoPostal, String calle, String numero, String complemento, String municipio) {
		this.codigoPostal = codigoPostal;
		this.calle = calle;
		this.numero = numero;
		this.complemento = complemento;
		this.municipio = municipio;
	}

	// Getters y Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CodigoPostal getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(CodigoPostal codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}