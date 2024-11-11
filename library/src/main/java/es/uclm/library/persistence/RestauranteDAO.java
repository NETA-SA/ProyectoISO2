package es.uclm.library.business.persistence;

import es.uclm.library.business.entity.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestauranteDAO extends JpaRepository<Restaurante, Long> {
	// Aquí puedes agregar métodos personalizados, si es necesario


	// List<Restaurante> findByNombre(String nombre);
}
