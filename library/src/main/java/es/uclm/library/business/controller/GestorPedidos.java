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

	/**
	 * Muestra el formulario para realizar un nuevo pedido.
	 *
	 * @param model Model para pasar datos a la vista
	 * @return Nombre de la plantilla Thymeleaf para realizar un pedido
	 */
	@GetMapping("/realizar")
	public String mostrarFormularioPedido(Model model) {
		logger.info("Mostrando el formulario para realizar un nuevo pedido.");
		return "realizarPedido"; // Nombre de la plantilla Thymeleaf (html)
	}

	/**
	 * Realiza un nuevo pedido.
	 *
	 * @param cliente   Cliente que realiza el pedido
	 * @param restaurante Restaurante al que se pide
	 * @param items     Lista de ítems del menú
	 * @param model Model para pasar datos a la vista
	 * @return Redirección o vista de confirmación
	 */
	@PostMapping("/realizar")
	public String realizarPedido(@RequestParam Cliente cliente, @RequestParam Restaurante restaurante, @RequestParam List<ItemMenu> items, Model model) {
		logger.info("Iniciando la creación de un nuevo pedido para el cliente {} en el restaurante {}", cliente, restaurante);
		Pedido pedido = pedidoService.realizarPedido(cliente, restaurante, items);
		model.addAttribute("mensaje", "Pedido realizado con éxito.");
		logger.info("Pedido realizado con éxito: {}", pedido);
		return "confirmacionPedido"; // Nombre de la plantilla Thymeleaf (html)
	}

	/**
	 * Muestra un formulario para añadir un ítem al pedido en marcha.
	 *
	 * @return Nombre de la plantilla Thymeleaf para añadir ítem
	 */
	@GetMapping("/anadirItem")
	public String mostrarFormularioAnadirItem() {
		logger.info("Mostrando formulario para añadir un ítem al pedido.");
		return "anadirItem"; // Nombre de la plantilla Thymeleaf (html)
	}

	/**
	 * Añade un ítem al pedido en marcha.
	 *
	 * @param item Ítem del menú a añadir
	 * @param model Model para pasar datos a la vista
	 * @return Redirección a la vista del pedido actual
	 */
	@PostMapping("/anadirItem")
	public String anadirItemMenu(@RequestParam ItemMenu item, Model model) {
		logger.info("Añadiendo ítem {} al pedido en marcha.", item);
		pedidoService.anadirItemMenu(pedidoEnMarcha, item);
		model.addAttribute("mensaje", "Ítem añadido al pedido.");
		logger.info("Ítem {} añadido al pedido en marcha.", item);
		return "verPedido"; // Vista que muestra el pedido actual
	}

	/**
	 * Elimina un ítem del pedido en marcha.
	 *
	 * @param item Ítem del menú a eliminar
	 * @param model Model para pasar datos a la vista
	 * @return Redirección a la vista del pedido actual
	 */
	@PostMapping("/eliminarItem")
	public String eliminarItemMenu(@RequestParam ItemMenu item, Model model) {
		logger.info("Eliminando ítem {} del pedido en marcha.", item);
		pedidoService.eliminarItemMenu(pedidoEnMarcha, item);
		model.addAttribute("mensaje", "Ítem eliminado del pedido.");
		logger.info("Ítem {} eliminado del pedido en marcha.", item);
		return "verPedido"; // Vista que muestra el pedido actual
	}

	/**
	 * Comienza un nuevo pedido para un restaurante.
	 *
	 * @param restaurante Restaurante en el que se inicia el pedido
	 * @param model Model para pasar datos a la vista
	 * @return Vista para crear el pedido en el restaurante seleccionado
	 */
	@PostMapping("/comenzar")
	public String comenzarPedido(@RequestParam Restaurante restaurante, Model model) {
		logger.info("Comenzando un nuevo pedido para el restaurante {}", restaurante);
		pedidoEnMarcha = pedidoService.comenzarPedido(restaurante);
		model.addAttribute("restaurante", restaurante);
		logger.info("Formulario de creación de pedido mostrado para el restaurante {}", restaurante);
		return "crearPedido"; // Vista para comenzar un nuevo pedido
	}
}
