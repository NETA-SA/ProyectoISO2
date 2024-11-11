package es.uclm.library.business.service;

import es.uclm.library.business.entity.Cliente;
import es.uclm.library.business.entity.Direccion;
import es.uclm.library.business.entity.Restaurante;
import es.uclm.library.business.entity.CodigoPostal;
import es.uclm.library.persistence.ClienteDAO;
import es.uclm.library.persistence.RestauranteDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);

    @Autowired
    private ClienteDAO clienteDAO;

    @Autowired
    private RestauranteDAO restauranteDAO;

    /**
     * Registra un nuevo cliente en el sistema.
     *
     * @param nombre Nombre del cliente
     * @param apellido Apellido del cliente
     * @param direccion Dirección del cliente
     * @return Cliente registrado
     */
    public Cliente registrarCliente(String nombre, String apellido, Direccion direccion) {
        Cliente cliente = new Cliente(nombre, apellido, direccion);
        Cliente savedCliente = clienteDAO.save(cliente);
        logger.info("Cliente registrado exitosamente: {}", savedCliente);
        return savedCliente;
    }

    /**
     * Busca restaurantes en una zona específica.
     *
     * @param zona Código postal de la zona
     * @return Lista de restaurantes en la zona
     */
    public List<Restaurante> buscarRestaurantesPorZona(CodigoPostal zona) {
        List<Restaurante> restaurantes = restauranteDAO.buscarPorCodigoPostal(zona);
        logger.info("Encontrados {} restaurantes en la zona: {}", restaurantes.size(), zona);
        return restaurantes;
    }


    /**
     * Marca un restaurante como favorito para un cliente.
     *
     * @param cliente Cliente que marca el favorito
     * @param restaurante Restaurante a marcar como favorito
     */
    public void agregarRestauranteFavorito(Cliente cliente, Restaurante restaurante) {
        cliente.getFavoritos().add(restaurante);
        clienteDAO.save(cliente);
        logger.info("Restaurante {} añadido a favoritos del cliente {}", restaurante, cliente);
    }
}
