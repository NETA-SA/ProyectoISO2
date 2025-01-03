package es.uclm.library.persistence;

import es.uclm.library.business.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoDAO extends JpaRepository<Pago, Long> {
}