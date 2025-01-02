package es.uclm.library.persistence;

import es.uclm.library.business.entity.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

@Repository
public interface RestauranteDAO extends JpaRepository<Restaurante, Long> {

    // Consulta SQL nativa para obtener todos los restaurantes
    @Query(value = "SELECT * FROM Restaurante", nativeQuery = true)
    List<Restaurante> obtenerTodosConSQL();

    // Método para buscar restaurante por relación con usuario usando JPQL
    @Query("SELECT r.id FROM Restaurante r WHERE r.usuario.idUsuario = :usuarioId")
    Long obtenerIdRestaurantePorUsuario(@Param("usuarioId") String usuarioId);
}
