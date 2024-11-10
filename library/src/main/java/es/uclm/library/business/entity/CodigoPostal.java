package es.uclm.library.business.entity;

import javax.persistence.*;

@Entity
@Table(name = "codigo_postal")
public class CodigoPostal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo", nullable = false, unique = true)
    private String codigo;

    // Constructor por defecto
    public CodigoPostal() {}

    // Constructor con parámetros
    public CodigoPostal(String codigo) {
        this.codigo = codigo;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
