package es.uclm.library.business.service;

import es.uclm.library.business.entity.Cliente;
import es.uclm.library.business.entity.Direccion;
import es.uclm.library.business.entity.ItemMenu;
import es.uclm.library.business.entity.Pedido;
import es.uclm.library.business.entity.Restaurante;
import es.uclm.library.business.entity.ServicioEntrega;
import es.uclm.library.persistence.ClienteDAO;
import es.uclm.library.persistence.ItemMenuDAO;
import es.uclm.library.persistence.PedidoDAO;
import es.uclm.library.persistence.RestauranteDAO;
import es.uclm.library.persistence.ServicioEntregaDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PedidoService {

    private static final Logger logger = LoggerFactory.getLogger(PedidoService.class);

    @Autowired
    private PedidoDAO pedidoDAO;

    @Autowired
    private ServicioEntregaDAO servicioEntregaDAO;

    @Autowired
    private ClienteDAO clienteDAO;

    @Autowired
    private RestauranteDAO restauranteDAO;

    @Autowired
    private ItemMenuDAO itemMenuDAO;
//
//    /**
//     * Realiza un nuevo pedido.
//     *
//     * @param cliente Cliente que realiza el pedido
//     * @param pago Información de pago del pedido
//     * @param items Lista de ítems del menú incluidos en el pedido
//     * @param restaurante Restaurante al que se realiza el pedido
//     * @param entrega Información sobre el servicio de entrega del pedido
//     * @param estado Estado actual del pedido
//     * @param fecha Fecha de realización del pedido
//     * @return Pedido creado
//     */
//    public Pedido realizarPedido(Cliente cliente, Pago pago, Collection<ItemMenu> items, Restaurante restaurante, ServicioEntrega entrega, EstadoPedido estado, Date fecha) {
//        Pedido pedido = new Pedido(cliente, pago, items, restaurante, entrega, estado, fecha);
//        pedidoDAO.save(pedido); // Guarda el pedido en la base de datos
//        logger.info("Pedido creado y guardado: {}", pedido);
//        return pedido;
//    }
//
//    /**
//     * Añade un ítem al pedido en marcha.
//     *
//     * @param pedido Pedido en el que se añadirá el ítem
//     * @param item Ítem del menú a añadir
//     */
//    public void anadirItemMenu(Pedido pedido, ItemMenu item) {
//        pedido.getItems().add(item);
//        pedidoDAO.save(pedido);
//        logger.info("Ítem añadido al pedido: {}", item);
//    }
//
//    /**
//     * Elimina un ítem del pedido en marcha.
//     *
//     * @param pedido Pedido del que se eliminará el ítem
//     * @param item Ítem del menú a eliminar
//     */
//    public void eliminarItemMenu(Pedido pedido, ItemMenu item) {
//        pedido.getItems().remove(item);
//        pedidoDAO.update(pedido);
//        logger.info("Ítem eliminado del pedido: {}", item);
//    }
//
//    /**
//     * Crea un servicio de entrega para un pedido.
//     *
//     * @param pedido Pedido para el cual se crea el servicio de entrega
//     * @param direccion Dirección de entrega del pedido
//     * @param repartidor Repartidor asignado al servicio de entrega
//     * @param fechaRecepcion Fecha en la que el pedido es recogido
//     * @param fechaEntrega Fecha estimada o real de entrega del pedido
//     * @return Servicio de entrega creado
//     */
//    public ServicioEntrega crearServicioEntrega(Pedido pedido, Direccion direccion, Repartidor repartidor, Date fechaRecepcion, Date fechaEntrega) {
//        ServicioEntrega servicioEntrega = new ServicioEntrega(pedido, direccion, repartidor, fechaRecepcion, fechaEntrega);
//        servicioEntregaDAO.save(servicioEntrega);
//        logger.info("Servicio de entrega creado para el pedido: {}", pedido);
//        return servicioEntrega;
//    }
//
//    /**
//     * Busca un cliente por su ID.
//     *
//     * @param id Identificador del cliente
//     * @return Cliente encontrado o null si no se encuentra
//     */
//    public Cliente findClienteById(Long id) {
//        return clienteDAO.findById(id).orElse(null);
//    }
//
//    /**
//     * Busca un restaurante por su ID.
//     *
//     * @param id Identificador del restaurante
//     * @return Restaurante encontrado o null si no se encuentra
//     */
//    public Restaurante findRestauranteById(Long id) {
//        return restauranteDAO.findById(id).orElse(null);
//    }
//
//    /**
//     * Busca una lista de ítems de menú por sus IDs.
//     *
//     * @param ids Lista de IDs de ítems del menú
//     * @return Lista de ítems del menú encontrados
//     */
//    public List<ItemMenu> findItemsByIds(List<Long> ids) {
//        return itemMenuDAO.findAllById(ids);
//    }
//
//    /**
//     * Busca un ítem de menú por su ID.
//     *
//     * @param id Identificador del ítem del menú
//     * @return Ítem del menú encontrado o null si no se encuentra
//     */
//    public ItemMenu findItemById(Long id) {
//        return itemMenuDAO.findById(id).orElse(null);
//    }
//
//    /**
//     * Comienza un nuevo pedido para un restaurante.
//     *
//     * @param restaurante Restaurante en el que se inicia el pedido
//     * @return Pedido inicializado para el restaurante
//     */
//    public Pedido comenzarPedido(Restaurante restaurante) {
//        return new Pedido(restaurante); // Asegúrate de que este constructor exista en Pedido
//    }
}
