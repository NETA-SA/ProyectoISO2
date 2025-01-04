package es.uclm.library.persistence;

import es.uclm.library.business.entity.Repartidor;
import es.uclm.library.business.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepartidorDAO extends JpaRepository<Repartidor, String> {
    Repartidor findByUsuario(Usuario usuario);
}
