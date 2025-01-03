package es.uclm.library.business.service;

import es.uclm.library.business.entity.Direccion;
import es.uclm.library.business.entity.ItemMenu;
import es.uclm.library.business.entity.CartaMenu;
import es.uclm.library.business.entity.Restaurante;
import es.uclm.library.business.entity.TipoItemMenu;
import es.uclm.library.persistence.ItemMenuDAO;
import es.uclm.library.persistence.CartaMenuDAO;
import es.uclm.library.persistence.RestauranteDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestauranteService {

    @Autowired
    private RestauranteDAO restauranteDAO;

    public List<Restaurante> obtenerTodosRestaurantes() {
        return restauranteDAO.obtenerTodosConSQL();
    }

    public Long obtenerIdRestaurantePorUsuario (String idUsuario) {
        Long idRestaurante = restauranteDAO.obtenerIdRestaurantePorUsuario(idUsuario);
        if (idRestaurante == null) {
            throw new RuntimeException("No se pudo encontrar un restaurante para el usuario especificado.");
        }
        return idRestaurante;
    }

    public void guardarRestaurante(Restaurante restaurante) {
        restauranteDAO.save(restaurante); // save del JpaRepository
    }

    @Autowired
    private CartaMenuDAO cartaMenuDAO;
    @Autowired
    private ItemMenuDAO itemMenuDAO;

    // Guardar una nueva carta
    public CartaMenu guardarNuevaCarta(CartaMenu cartaMenu) {
        return cartaMenuDAO.save(cartaMenu);
    }

    // Obtener una carta por su ID
    public CartaMenu obtenerCartaPorId(Long cartaMenuId) {
        return cartaMenuDAO.findById(cartaMenuId).orElse(null);
    }

    // Obtener una carta por su ID
    public ItemMenu obtenerItemPorId(Long itemMenuId) {
        return itemMenuDAO.findById(itemMenuId).orElse(null);
    }

    // Obtener todas las cartas asociadas a un restaurante
    public List<CartaMenu> obtenerCartasPorRestaurante(Long idRestaurante) {
        return cartaMenuDAO.findByRestauranteId(idRestaurante);
    }

    // Validar si una carta existe por nombre y restaurante
    public boolean cartaExiste(String nombre, Long idRestaurante) {
        return cartaMenuDAO.findByNombreAndRestauranteId(nombre, idRestaurante) != null;
    }
    
    // Metodo para actualizar una carta
    public void actualizarCarta(CartaMenu cartaMenu) {
        cartaMenuDAO.save(cartaMenu); // Guardar cambios en la base de datos
    }

    public Restaurante obtenerRestaurantePorId(Long restauranteId) {
        return restauranteDAO.findById(restauranteId).orElse(null);
    }
}
