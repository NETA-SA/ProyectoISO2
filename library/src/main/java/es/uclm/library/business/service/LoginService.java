package es.uclm.library.business.service;

import es.uclm.library.business.entity.Usuario;
import es.uclm.library.persistence.UsuarioDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    private UsuarioDAO usuarioDAO;
//
//    /**
//     * Autentica un usuario con el ID y la contraseña proporcionados.
//     *
//     * @param id   Identificador del usuario
//     * @param pass Contraseña del usuario
//     * @return Verdadero si la autenticación fue exitosa, falso de lo contrario
//     */
//    public boolean autenticarUsuario(String id, String pass) {
//        logger.info("Autenticando usuario con ID: {}", id);
//
//        // Busca al usuario por su ID
//        Usuario usuario = usuarioDAO.findById(id).orElse(null);
//
//        if (usuario != null && usuario.getPass().equals(pass)) {
//            logger.info("Autenticación exitosa para el usuario: {}", id);
//            return true;
//        } else {
//            logger.warn("Autenticación fallida para el usuario: {}", id);
//            return false;
//        }
//    }
}
