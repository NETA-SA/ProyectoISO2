package es.uclm.library.business.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "item_pedido")
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El tipo no puede ser nulo")
    @Enumerated(EnumType.STRING)
    @Column(name = "Tipo_Item_Pedido", nullable = false)
    private TipoItemMenu tipo;

    @ManyToOne
    @JoinColumn(name = "restaurante_id")
    private Restaurante restaurante;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Column(nullable = false)
    private String nombre;

    @NotNull(message = "El precio no puede ser nulo")
    @Column(nullable = false)
    private Double precio;

    @NotNull(message = "La cantidad no puede ser nula")
    @Column(nullable = false)
    private int cantidad;

    // Default constructor
    public ItemPedido() {}

    // Constructor with parameters
    public ItemPedido(TipoItemMenu tipo, String nombre, Double precio, int cantidad) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    // Getters and Setters
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

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}