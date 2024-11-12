package es.uclm.library.persistence;

import es.uclm.library.business.entity.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestauranteDAO extends JpaRepository<Restaurante, Long> {

//    List<Restaurante> findByCodigoPostal(CodigoPostal codigoPostal);
//
//    Restaurante findByName(String name);
//
//    void update(Restaurante restaurante);
//



}
