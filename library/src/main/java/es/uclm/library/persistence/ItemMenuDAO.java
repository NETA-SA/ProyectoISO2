package es.uclm.library.persistence;

import es.uclm.library.business.entity.ItemMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface ItemMenuDAO extends JpaRepository<ItemMenu, Long> {

    List<ItemMenu> findByRestauranteId(Long idRestaurante);

}
