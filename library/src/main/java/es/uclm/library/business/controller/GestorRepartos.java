package es.uclm.library.business.controller;

import es.uclm.library.business.entity.CodigoPostal;
import es.uclm.library.business.entity.ServicioEntrega;
import es.uclm.library.business.service.RepartoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/repartos")
public class GestorRepartos {

	private static final Logger logger = LoggerFactory.getLogger(GestorRepartos.class);

	@Autowired
	private RepartoService repartoService;

	/**
	 * Marca un pedido como recogido.
	 *
	 * @param servicio Servicio de entrega asociado al pedido
	 * @param model Model para pasar datos a la vista
	 * @return Vista de confirmación de recogida
	 */
	@PostMapping("/marcarRecogido")
	public String marcarPedidoRecogido(@RequestBody ServicioEntrega servicio, Model model) {
		logger.info("Marcando el pedido como recogido para el servicio de entrega: {}", servicio);
		repartoService.marcarPedidoRecogido(servicio);
		model.addAttribute("mensaje", "Pedido marcado como recogido.");
		logger.info("Pedido marcado como recogido para el servicio: {}", servicio);
		return "confirmacionRecogida"; // Nombre de la plantilla Thymeleaf para confirmación de recogida
	}

	/**
	 * Marca un pedido como entregado.
	 *
	 * @param servicio Servicio de entrega asociado al pedido
	 * @param model Model para pasar datos a la vista
	 * @return Vista de confirmación de entrega
	 */
	@PostMapping("/marcarEntregado")
	public String marcarPedidoEntregado(@RequestBody ServicioEntrega servicio, Model model) {
		logger.info("Marcando el pedido como entregado para el servicio de entrega: {}", servicio);
		repartoService.marcarPedidoEntregado(servicio);
		model.addAttribute("mensaje", "Pedido marcado como entregado.");
		logger.info("Pedido marcado como entregado para el servicio: {}", servicio);
		return "confirmacionEntrega"; // Nombre de la plantilla Thymeleaf para confirmación de entrega
	}

	/**
	 * Muestra el formulario para registrar un nuevo repartidor.
	 *
	 * @return Nombre de la plantilla Thymeleaf para el formulario de registro de repartidor
	 */
	@GetMapping("/registrarRepartidor")
	public String mostrarFormularioRegistroRepartidor() {
		logger.info("Mostrando formulario para registrar un nuevo repartidor.");
		return "registroRepartidor"; // Nombre de la plantilla Thymeleaf para el formulario de registro
	}

	/**
	 * Registra un nuevo repartidor.
	 *
	 * @param nombre Nombre del repartidor
	 * @param apellidos Apellidos del repartidor
	 * @param nif NIF del repartidor
	 * @param zonas Lista de códigos postales en los que el repartidor puede entregar
	 * @param model Model para pasar datos a la vista
	 * @return Vista de confirmación de registro
	 */
	@PostMapping("/registrarRepartidor")
	public String registrarRepartidor(@RequestParam String nombre, @RequestParam String apellidos, @RequestParam String nif, @RequestBody List<CodigoPostal> zonas, Model model) {
		logger.info("Registrando un nuevo repartidor: {} {} con NIF: {}", nombre, apellidos, nif);
		repartoService.registrarRepartidor(nombre, apellidos, nif, zonas);
		model.addAttribute("mensaje", "Repartidor registrado con éxito.");
		logger.info("Repartidor registrado: {} {}", nombre, apellidos);
		return "confirmacionRegistroRepartidor"; // Nombre de la plantilla Thymeleaf para confirmación de registro
	}
}
