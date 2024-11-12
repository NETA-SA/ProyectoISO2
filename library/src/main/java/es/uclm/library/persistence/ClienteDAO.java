package es.uclm.library.persistence;

import es.uclm.library.business.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteDAO extends JpaRepository<Cliente, String> {

    // Ejemplo de un método personalizado para buscar clientes por nombre
    //List<Cliente> findByNombre(String nombre);


}
