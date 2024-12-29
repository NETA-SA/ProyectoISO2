package es.uclm.library.persistence;

import es.uclm.library.business.entity.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface RestauranteDAO extends JpaRepository<Restaurante, Long> {

    @Query(value = "SELECT * FROM Restaurante", nativeQuery = true)
    List<Restaurante> obtenerTodosConSQL();

}
