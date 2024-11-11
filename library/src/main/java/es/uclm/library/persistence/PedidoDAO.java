package es.uclm.library.persistence;

import es.uclm.library.business.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoDAO extends JpaRepository<Pedido, Long> {
    // Aquí puedes agregar métodos personalizados si necesitas consultas específicas
}
