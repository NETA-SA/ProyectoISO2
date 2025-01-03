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

    public void crearPedido(Pedido pedido) {
        pedidoDAO.save(pedido);
    }

}
