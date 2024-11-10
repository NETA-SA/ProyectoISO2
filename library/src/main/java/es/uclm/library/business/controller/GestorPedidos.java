package es.uclm.library.business.controller;

import es.uclm.library.persistence.PedidoDAO;
import es.uclm.library.persistence.ServicioEntregaDAO;
import es.uclm.library.business.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class GestorPedidos {

	@Autowired
	private PedidoDAO pedidoDAO;

	@Autowired
	private ServicioEntregaDAO servicioEntregaDAO;

	private Pedido pedidoEnMarcha;

	/**
	 * Realiza un nuevo pedido.
	 *
	 * @param cliente   Cliente que realiza el pedido
	 * @param restaurante Restaurante al que se pide
	 * @param items     Lista de ítems del menú
	 */
	@PostMapping("/realizar")
	public void realizarPedido(@RequestBody Cliente cliente, @RequestBody Restaurante restaurante, @RequestBody List<ItemMenu> items) {
		// TODO - implementar lógica de realización de pedido
		throw new UnsupportedOperationException();
	}

	/**
	 * Realiza el pago de un pedido.
	 *
	 * @param pedido Pedido a pagar
	 * @return Verdadero si el pago se realizó con éxito
	 */
	private boolean realizarPago(Pedido pedido) {
		// TODO - implementar lógica de pago
		throw new UnsupportedOperationException();
	}

	/**
	 * Crea un servicio de entrega para un pedido.
	 *
	 * @param pedido   Pedido a entregar
	 * @param direccion Dirección de entrega
	 * @return Servicio de entrega creado
	 */
	private ServicioEntrega crearServicioEntrega(Pedido pedido, Direccion direccion) {
		// TODO - implementar creación de servicio de entrega
		throw new UnsupportedOperationException();
	}

	/**
	 * Añade un ítem al pedido en marcha.
	 *
	 * @param item Ítem del menú a añadir
	 */
	@PostMapping("/anadirItem")
	public void anadirItemMenu(@RequestBody ItemMenu item) {
		// TODO - implementar lógica para añadir ítem
		throw new UnsupportedOperationException();
	}

	/**
	 * Elimina un ítem del pedido en marcha.
	 *
	 * @param item Ítem del menú a eliminar
	 */
	@DeleteMapping("/eliminarItem")
	public void eliminarItemMenu(@RequestBody ItemMenu item) {
		// TODO - implementar lógica para eliminar ítem
		throw new UnsupportedOperationException();
	}

	/**
	 * Comienza un nuevo pedido para un restaurante.
	 *
	 * @param restaurante Restaurante en el que se inicia el pedido
	 */
	@PostMapping("/comenzar")
	public void comenzarPedido(@RequestBody Restaurante restaurante) {
		// TODO - implementar lógica para comenzar pedido
		throw new UnsupportedOperationException();
	}
}
