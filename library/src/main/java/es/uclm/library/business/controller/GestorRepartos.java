package es.uclm.library.business.controller;

import es.uclm.library.business.entity.CodigoPostal;
import es.uclm.library.business.entity.ServicioEntrega;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/repartos")
public class GestorRepartos {

	/**
	 * Marca un pedido como recogido.
	 *
	 * @param servicio Servicio de entrega asociado al pedido
	 * @param model Model para pasar datos a la vista
	 * @return Redirección o vista de confirmación
	 */
	@PostMapping("/marcarRecogido")
	public String marcarPedidoRecogido(@RequestParam ServicioEntrega servicio, Model model) {
		// TODO - implementar lógica para marcar pedido como recogido
		model.addAttribute("mensaje", "Pedido marcado como recogido.");
		return "confirmacionRecogida"; // Nombre de la plantilla Thymeleaf (html)
	}

	/**
	 * Marca un pedido como entregado.
	 *
	 * @param servicio Servicio de entrega asociado al pedido
	 * @param model Model para pasar datos a la vista
	 * @return Redirección o vista de confirmación
	 */
	@PostMapping("/marcarEntregado")
	public String marcarPedidoEntregado(@RequestParam ServicioEntrega servicio, Model model) {
		// TODO - implementar lógica para marcar pedido como entregado
		model.addAttribute("mensaje", "Pedido marcado como entregado.");
		return "confirmacionEntrega"; // Nombre de la plantilla Thymeleaf (html)
	}

	/**
	 * Muestra el formulario para registrar un nuevo repartidor.
	 *
	 * @return Nombre de la plantilla Thymeleaf para el formulario de registro
	 */
	@GetMapping("/registrarRepartidor")
	public String mostrarFormularioRegistrarRepartidor() {
		return "registroRepartidor"; //Nombre de la plantilla Thymeleaf (html)
	}

	/**
	 * Registra un nuevo repartidor.
	 *
	 * @param nombre   Nombre del repartidor
	 * @param apellidos Apellidos del repartidor
	 * @param nif       NIF del repartidor
	 * @param zonas     Lista de códigos postales en los que el repartidor puede entregar
	 * @param model Model para pasar datos a la vista
	 * @return Redirección o vista de confirmación
	 */
	@PostMapping("/registrarRepartidor")
	public String registrarRepartidor(@RequestParam String nombre, @RequestParam String apellidos, @RequestParam String nif, @RequestParam List<CodigoPostal> zonas, Model model) {
		// TODO - implementar lógica para registrar repartidor
		model.addAttribute("mensaje", "Repartidor registrado con éxito.");
		return "confirmacionRegistroRepartidor"; // Nombre de la plantilla Thymeleaf (html)
	}
}
