package es.uclm.library.business.controller;

import es.uclm.library.business.entity.Cliente;
import es.uclm.library.business.entity.Direccion;
import es.uclm.library.business.entity.Restaurante;
import es.uclm.library.business.entity.CodigoPostal;
import es.uclm.library.business.service.ClienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/clientes")
public class GestorClientes {

	private static final Logger logger = LoggerFactory.getLogger(GestorClientes.class);

	@Autowired
	private ClienteService clienteService;

	/**
	 * Muestra el formulario para buscar restaurantes en una zona específica.
	 *
	 * @return Nombre de la plantilla Thymeleaf para el formulario de búsqueda
	 */
	@GetMapping("/restaurantes/zona")
	public String mostrarFormularioBusquedaPorZona() {
		logger.info("Mostrando formulario para buscar restaurantes por zona.");
		return "buscarRestaurantesPorZona"; // Nombre de la plantilla Thymeleaf
	}

	/**
	 * Busca restaurantes en una zona específica.
	 *
	 * @param zona Código postal de la zona
	 * @param model Model para pasar datos a la vista
	 * @return Vista con la lista de restaurantes encontrados
	 */
	@PostMapping("/restaurantes/zona")
	public String buscarRestaurante(@RequestParam CodigoPostal zona, Model model) {
		logger.info("Buscando restaurantes en la zona: {}", zona);
		List<Restaurante> restaurantes = clienteService.buscarRestaurantePorZona(zona);
		model.addAttribute("restaurantes", restaurantes);
		logger.info("Encontrados {} restaurantes en la zona {}", restaurantes.size(), zona);
		return "listaRestaurantes"; // Vista Thymeleaf para mostrar resultados
	}

	/**
	 * Muestra el formulario para registrar un nuevo cliente.
	 *
	 * @return Nombre de la plantilla Thymeleaf para el formulario de registro
	 */
	@GetMapping("/registrar")
	public String mostrarFormularioRegistroCliente() {
		logger.info("Mostrando formulario de registro para un nuevo cliente.");
		return "registroCliente"; // Nombre de la plantilla Thymeleaf
	}

	/**
	 * Registra un nuevo cliente en el sistema.
	 *
	 * @param nombre Nombre del cliente
	 * @param apellido Apellido del cliente
	 * @param direccion Dirección del cliente
	 * @param model Model para pasar datos a la vista
	 * @return Redirección o vista de confirmación
	 */
	@PostMapping("/registrar")
	public String registrarCliente(@RequestParam String nombre, @RequestParam String apellido, @RequestBody Direccion direccion, Model model) {
		logger.info("Registrando nuevo cliente: {} {}", nombre, apellido);
		Cliente cliente = clienteService.registrarCliente(nombre, apellido, direccion);
		model.addAttribute("cliente", cliente);
		logger.info("Cliente registrado con éxito: {}", cliente);
		return "confirmacionRegistroCliente"; // Vista Thymeleaf para confirmar el registro
	}

	/**
	 * Marca un restaurante como favorito para un cliente.
	 *
	 * @param cliente Cliente que marca el favorito
	 * @param restaurante Restaurante a marcar como favorito
	 * @param model Model para pasar datos a la vista
	 * @return Vista de confirmación
	 */
	@PostMapping("/favorito")
	public String favorito(@RequestParam Cliente cliente, @RequestParam Restaurante restaurante, Model model) {
		logger.info("Marcando el restaurante {} como favorito para el cliente {}", restaurante, cliente);
		clienteService.marcarFavorito(cliente, restaurante);
		model.addAttribute("mensaje", "Restaurante añadido a favoritos.");
		logger.info("Restaurante {} añadido a favoritos del cliente {}", restaurante, cliente);
		return "confirmacionFavorito"; // Vista Thymeleaf para confirmar la acción
	}
}
