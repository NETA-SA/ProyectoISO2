package es.uclm.library.business.service;

import es.uclm.library.business.entity.Cliente;
import es.uclm.library.business.entity.Direccion;
import es.uclm.library.business.entity.ItemMenu;
import es.uclm.library.business.entity.Pedido;
import es.uclm.library.business.entity.Restaurante;
import es.uclm.library.business.entity.ServicioEntrega;
import es.uclm.library.persistence.PedidoDAO;
import es.uclm.library.persistence.ServicioEntregaDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    private static final Logger logger = LoggerFactory.getLogger(PedidoService.class);

    @Autowired
    private PedidoDAO pedidoDAO;

    @Autowired
    private ServicioEntregaDAO servicioEntregaDAO;

    /**
     * Realiza un nuevo pedido.
     *
     * @param cliente Cliente que realiza el pedido
     * @param restaurante Restaurante al que se pide
     * @param items Lista de ítems del menú
     * @return Pedido creado
     */
    public Pedido realizarPedido(Cliente cliente, Restaurante restaurante, List<ItemMenu> items) {
        Pedido pedido = new Pedido(cliente, restaurante, items);
        pedidoDAO.save(pedido);
        logger.info("Pedido creado y guardado: {}", pedido);
        return pedido;
    }

    /**
     * Añade un ítem al pedido en marcha.
     *
     * @param pedido Pedido en el que se añadirá el ítem
     * @param item Ítem del menú a añadir
     */
    public void anadirItemMenu(Pedido pedido, ItemMenu item) {
        pedido.getItems().add(item);
        pedidoDAO.update(pedido);
        logger.info("Ítem añadido al pedido: {}", item);
    }

    /**
     * Elimina un ítem del pedido en marcha.
     *
     * @param pedido Pedido del que se eliminará el ítem
     * @param item Ítem del menú a eliminar
     */
    public void eliminarItemMenu(Pedido pedido, ItemMenu item) {
        pedido.getItems().remove(item);
        pedidoDAO.update(pedido);
        logger.info("Ítem eliminado del pedido: {}", item);
    }

    /**
     * Crea un servicio de entrega para un pedido.
     *
     * @param pedido Pedido a entregar
     * @param direccion Dirección de entrega
     * @return Servicio de entrega creado
     */
    public ServicioEntrega crearServicioEntrega(Pedido pedido, Direccion direccion) {
        ServicioEntrega servicioEntrega = new ServicioEntrega(pedido, direccion);
        servicioEntregaDAO.save(servicioEntrega);
        logger.info("Servicio de entrega creado para el pedido: {}", pedido);
        return servicioEntrega;
    }
}
