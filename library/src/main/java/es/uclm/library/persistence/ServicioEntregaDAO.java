package es.uclm.library.persistence;

import es.uclm.library.business.entity.Repartidor;
import es.uclm.library.business.entity.ServicioEntrega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicioEntregaDAO extends JpaRepository<ServicioEntrega, Long> {

    List<ServicioEntrega> findByRepartidor(Repartidor repartidor);
}
