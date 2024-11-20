package es.uclm.library.business.service;

import es.uclm.library.business.entity.Usuario;
import es.uclm.library.business.entity.Restaurante;
import es.uclm.library.business.entity.Repartidor;
import es.uclm.library.persistence.UsuarioDAO;
import es.uclm.library.persistence.RestauranteDAO;
import es.uclm.library.persistence.RepartidorDAO;
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
}