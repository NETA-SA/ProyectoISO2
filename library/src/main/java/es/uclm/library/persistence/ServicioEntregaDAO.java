package es.uclm.library.business.persistence;

import es.uclm.library.business.entity.ServicioEntrega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicioEntregaDAO extends JpaRepository<ServicioEntrega, Long> {
    // Aquí puedes agregar métodos personalizados, si es necesario

    // List<ServicioEntrega> findByRepartidorId(Long repartidorId);
}
