package es.uclm.library.business.service;

import es.uclm.library.business.entity.Direccion;
import es.uclm.library.business.entity.ItemMenu;
import es.uclm.library.business.entity.Restaurante;
import es.uclm.library.business.entity.TipoItemMenu;
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

    /**
     * Registra un nuevo restaurante.
     *
     * @param nombre Nombre del restaurante
     * @param cif CIF del restaurante
     * @param direccion Dirección del restaurante
     * @return Restaurante registrado
     */
    public Restaurante registrarRestaurante(String nombre, String cif, Direccion direccion) {
        Restaurante restaurante = new Restaurante(nombre, cif, direccion);
        Restaurante savedRestaurante = restauranteDAO.save(restaurante);
        logger.info("Restaurante registrado con éxito: {}", savedRestaurante);
        return savedRestaurante;
    }

    /**
     * Edita la carta de un restaurante.
     *
     * @param nombre Nombre del restaurante
     * @param items Lista de ítems del menú
     */
    public void editarCarta(String nombre, List<ItemMenu> items) {
        Restaurante restaurante = restauranteDAO.findByName(nombre);
        if (restaurante != null) {
            restaurante.setItems(items);
            restauranteDAO.update(restaurante);
            logger.info("Carta actualizada para el restaurante: {}", restaurante);
        } else {
            logger.warn("No se encontró el restaurante con nombre: {}", nombre);
        }
    }

    /**
     * Crea un nuevo ítem de menú.
     *
     * @param nombre Nombre del ítem
     * @param precio Precio del ítem
     * @param tipo Tipo del ítem
     * @return Nuevo ítem de menú creado
     */
    public ItemMenu crearItem(String nombre, double precio, TipoItemMenu tipo) {
        ItemMenu item = new ItemMenu(nombre, precio, tipo);
        ItemMenu savedItem = itemMenuDAO.save(item);
        logger.info("Ítem de menú creado: {}", savedItem);
        return savedItem;
    }

    public Restaurante findById(Long id) {
        return restauranteDAO.findById(id).orElse(null);
    }

}
