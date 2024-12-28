// DireccionDAO.java
package es.uclm.library.persistence;

import es.uclm.library.business.entity.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DireccionDAO extends JpaRepository<Direccion, Long> {
}