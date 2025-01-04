package es.uclm.library.business.service;

import es.uclm.library.business.entity.Cliente;
import es.uclm.library.business.entity.Direccion;
import es.uclm.library.business.entity.ItemMenu;
import es.uclm.library.business.entity.Pedido;
import es.uclm.library.business.entity.Restaurante;
import es.uclm.library.business.entity.ServicioEntrega;
import es.uclm.library.business.entity.Pago;
import es.uclm.library.persistence.*;
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

    @Autowired
    private ItemPedidoDAO itemPedidoDAO;

    @Autowired
    private DireccionDAO direccionDAO;

    @Autowired
    private PagoDAO pagoDAO;


    public void crearPedido(Pedido pedido) {
        pedidoDAO.save(pedido);
    }

    public void actualizarPedido(Pedido pedido) {
        pedidoDAO.save(pedido);
    }

    public Pedido obtenerPedidoPorId(Long pedidoId) {
        return pedidoDAO.findById(pedidoId).orElse(null);
    }

    public Pago guardarPago(Pago pago) {
        return pagoDAO.save(pago);
    }

    public Direccion guardarDireccion(Direccion direccion) {
        return direccionDAO.save(direccion);
    }

    public void guardarServicioEntrega(ServicioEntrega servicioEntrega) {
        servicioEntregaDAO.save(servicioEntrega);
    }
}
