package es.uclm.library.persistence;

import es.uclm.library.business.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoDAO extends JpaRepository<ItemPedido, Long> {
}