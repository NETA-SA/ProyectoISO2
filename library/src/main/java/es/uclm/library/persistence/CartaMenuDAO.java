package es.uclm.library.persistence;

import es.uclm.library.business.entity.CartaMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartaMenuDAO extends JpaRepository<CartaMenu, Long> {

    // Consulta para obtener todas las cartas de un restaurante por su ID
    @Query("SELECT c FROM CartaMenu c WHERE c.restaurante.id = :restauranteId")
    List<CartaMenu> findByRestauranteId(@Param("restauranteId") Long restauranteId);

    // Consulta para encontrar cartas por nombre
    @Query("SELECT c FROM CartaMenu c WHERE LOWER(c.nombre) = LOWER(:nombre) AND c.restaurante.id = :restauranteId")
    CartaMenu findByNombreAndRestauranteId(@Param("nombre") String nombre, @Param("restauranteId") Long restauranteId);
}