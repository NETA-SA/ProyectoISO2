package es.uclm.library.business.persistence;

import es.uclm.library.business.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioDAO extends JpaRepository<Usuario, String> {
    // Aquí puedes agregar métodos personalizados, si es necesario

    // Usuario findByEmail(String email);

}
