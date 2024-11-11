package es.uclm.library.business.controller;

import es.uclm.library.persistence.PedidoDAO;
import es.uclm.library.persistence.ServicioEntregaDAO;
import es.uclm.library.business.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/pedidos")
public class GestorPedidos {

	@Autowired
	private PedidoDAO pedidoDAO;

	@Autowired
	private ServicioEntregaDAO servicioEntregaDAO;

	private Pedido pedidoEnMarcha;

	/**
	 * Muestra el formulario para realizar un nuevo pedido.
	 *
	 * @param model Model para pasar datos a la vista
	 * @return Nombre de la plantilla Thymeleaf para realizar un pedido
	 */
	@GetMapping("/realizar")
	public String mostrarFormularioPedido(Model model) {
		// Agrega datos necesarios al modelo, si es necesario
		// model.addAttribute("cliente", new Cliente());
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
		// TODO - implementar lógica de realización de pedido
		model.addAttribute("mensaje", "Pedido realizado con éxito.");
		return "confirmacionPedido"; // Nombre de la plantilla Thymeleaf (html)
	}

	/**
	 * Muestra un formulario para añadir un ítem al pedido en marcha.
	 *
	 * @return Nombre de la plantilla Thymeleaf para añadir ítem
	 */
	@GetMapping("/anadirItem")
	public String mostrarFormularioAnadirItem() {
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
		// TODO - implementar lógica para añadir ítem al pedido en marcha
		model.addAttribute("mensaje", "Ítem añadido al pedido.");
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
		// TODO - implementar lógica para eliminar ítem del pedido en marcha
		model.addAttribute("mensaje", "Ítem eliminado del pedido.");
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
		// TODO - implementar lógica para comenzar el pedido
		model.addAttribute("restaurante", restaurante);
		return "crearPedido"; // Vista para comenzar un nuevo pedido
	}
}
