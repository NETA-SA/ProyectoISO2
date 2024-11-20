package es.uclm.library.business.service;
import es.uclm.library.persistence.*;
import es.uclm.library.business.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    private UsuarioDAO usuarioDAO;
    @Autowired
    private RestauranteDAO restauranteDAO;

    @Autowired
    private RepartidorDAO repartidorDAO;

    public void registerUsuario(Usuario usuario) {
        usuarioDAO.save(usuario);
    }

    public void registerRestaurante(Restaurante restaurante) {
        restauranteDAO.save(restaurante);
    }

    public void registerRepartidor(Repartidor repartidor) {
        repartidorDAO.save(repartidor);
    }

    public boolean authenticate(String idUsuario, String pass) {
        Usuario usuario = usuarioDAO.findByIdUsuario(idUsuario);

        // Verifica si el usuario existe y si la contraseña es correcta
        if (usuario != null && usuario.getPass().equals(pass)) {
            logger.info("Usuario autenticado correctamente: " + idUsuario);
            return true;
        } else {
            logger.warn("Falló la autenticación para el usuario: " + idUsuario);
            return false;
        }
    }
}
