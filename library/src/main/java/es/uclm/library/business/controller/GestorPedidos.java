package es.uclm.library.business.controller;

import es.uclm.library.business.entity.Cliente;
import es.uclm.library.business.entity.ItemMenu;
import es.uclm.library.business.entity.Pedido;
import es.uclm.library.business.entity.Restaurante;
import es.uclm.library.business.service.PedidoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/pedidos")
public class GestorPedidos {

	private static final Logger logger = LoggerFactory.getLogger(GestorPedidos.class);

	@Autowired
	private PedidoService pedidoService;

//	private Pedido pedidoEnMarcha;
//
//	@GetMapping("/realizar")
//	public String mostrarFormularioPedido(Model model) {
//		logger.info("Mostrando el formulario para realizar un nuevo pedido.");
//		return "realizarPedido"; // Nombre de la plantilla Thymeleaf (html)
//	}
//
//	@PostMapping("/realizar")
//	public String realizarPedido(@RequestParam Long clienteId, @RequestParam Long restauranteId, @RequestParam List<Long> itemIds, Model model) {
//		Cliente cliente = pedidoService.findClienteById(clienteId); // Implementa este método en PedidoService
//		Restaurante restaurante = pedidoService.findRestauranteById(restauranteId); // Implementa este método en PedidoService
//		List<ItemMenu> items = pedidoService.findItemsByIds(itemIds); // Implementa este método en PedidoService
//
//		logger.info("Iniciando la creación de un nuevo pedido para el cliente {} en el restaurante {}", cliente, restaurante);
//		Pedido pedido = pedidoService.realizarPedido(cliente, restaurante, items);
//		model.addAttribute("mensaje", "Pedido realizado con éxito.");
//		logger.info("Pedido realizado con éxito: {}", pedido);
//		return "confirmacionPedido"; // Nombre de la plantilla Thymeleaf (html)
//	}
//
//	@GetMapping("/anadirItem")
//	public String mostrarFormularioAnadirItem() {
//		logger.info("Mostrando formulario para añadir un ítem al pedido.");
//		return "anadirItem"; // Nombre de la plantilla Thymeleaf (html)
//	}
//
//	@PostMapping("/anadirItem")
//	public String anadirItemMenu(@RequestParam Long itemId, Model model) {
//		ItemMenu item = pedidoService.findItemById(itemId); // Implementa este método en PedidoService
//		logger.info("Añadiendo ítem {} al pedido en marcha.", item);
//		pedidoService.anadirItemMenu(pedidoEnMarcha, item);
//		model.addAttribute("mensaje", "Ítem añadido al pedido.");
//		logger.info("Ítem {} añadido al pedido en marcha.", item);
//		return "verPedido"; // Vista que muestra el pedido actual
//	}
//
//	@PostMapping("/eliminarItem")
//	public String eliminarItemMenu(@RequestParam Long itemId, Model model) {
//		ItemMenu item = pedidoService.findItemById(itemId); // Implementa este método en PedidoService
//		logger.info("Eliminando ítem {} del pedido en marcha.", item);
//		pedidoService.eliminarItemMenu(pedidoEnMarcha, item);
//		model.addAttribute("mensaje", "Ítem eliminado del pedido.");
//		logger.info("Ítem {} eliminado del pedido en marcha.", item);
//		return "verPedido"; // Vista que muestra el pedido actual
//	}
//
//	@PostMapping("/comenzar")
//	public String comenzarPedido(@RequestParam Long restauranteId, Model model) {
//		Restaurante restaurante = pedidoService.findRestauranteById(restauranteId); // Implementa este método en PedidoService
//		logger.info("Comenzando un nuevo pedido para el restaurante {}", restaurante);
//		pedidoEnMarcha = pedidoService.comenzarPedido(restaurante);
//		model.addAttribute("restaurante", restaurante);
//		logger.info("Formulario de creación de pedido mostrado para el restaurante {}", restaurante);
//		return "crearPedido"; // Vista para comenzar un nuevo pedido
//	}
}
