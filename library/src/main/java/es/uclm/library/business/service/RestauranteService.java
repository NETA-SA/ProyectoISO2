package es.uclm.library.business.service;

import es.uclm.library.business.entity.ItemMenu;
import es.uclm.library.business.entity.Restaurante;
import es.uclm.library.persistence.ItemMenuDAO;
import es.uclm.library.persistence.RestauranteDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestauranteService {

    private static final Logger logger = LoggerFactory.getLogger(RestauranteService.class);

    @Autowired
    private RestauranteDAO restauranteDAO;

    @Autowired
    private ItemMenuDAO itemMenuDAO;

    public List<Restaurante> obtenerListaDeRestaurantes() {
        logger.info("Obteniendo lista de restaurantes desde el repositorio");
        return restauranteDAO.findAll();
    }

    public List<ItemMenu> obtenerItemsDeMenu(Long idRestaurante) {
        logger.info("Obteniendo ítems del menú para el restaurante con ID: {}", idRestaurante);
        return itemMenuDAO.findByRestauranteId(idRestaurante);
    }


}
