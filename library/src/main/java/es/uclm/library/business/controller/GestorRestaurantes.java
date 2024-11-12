package es.uclm.library.business.controller;

import es.uclm.library.business.entity.Direccion;
import es.uclm.library.business.entity.ItemMenu;
import es.uclm.library.business.entity.Restaurante;
import es.uclm.library.business.entity.TipoItemMenu;
import es.uclm.library.business.service.RestauranteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/restaurantes")
public class GestorRestaurantes {

	private static final Logger logger = LoggerFactory.getLogger(GestorRestaurantes.class);

	@Autowired
	private RestauranteService restauranteService;
//
//	/**
//	 * Muestra el formulario para registrar un nuevo restaurante.
//	 *
//	 * @return Nombre de la plantilla Thymeleaf para el formulario de registro de restaurante
//	 */
//	@GetMapping("/registrar")
//	public String mostrarFormularioRegistroRestaurante() {
//		logger.info("Mostrando formulario para registrar un nuevo restaurante.");
//		return "registroRestaurante"; // Nombre de la plantilla Thymeleaf para el formulario de registro
//	}
//
//	/**
//	 * Registra un nuevo restaurante.
//	 *
//	 * @param nombre Nombre del restaurante
//	 * @param cif    CIF del restaurante
//	 * @param direccion Dirección del restaurante
//	 * @param model Model para pasar datos a la vista
//	 * @return Vista de confirmación de registro
//	 */
//	@PostMapping("/registrar")
//	public String registrarRestaurante(@RequestParam String nombre, @RequestParam String cif, @RequestBody Direccion direccion, Model model) {
//		logger.info("Registrando nuevo restaurante con nombre: {} y CIF: {}", nombre, cif);
//		Restaurante restaurante = restauranteService.registrarRestaurante(nombre, cif, direccion);
//		model.addAttribute("restaurante", restaurante);
//		logger.info("Restaurante registrado: {}", restaurante);
//		return "confirmacionRegistroRestaurante"; // Vista Thymeleaf para confirmar el registro
//	}
//
//	/**
//	 * Muestra el formulario para editar la carta de un restaurante.
//	 *
//	 * @return Nombre de la plantilla Thymeleaf para el formulario de edición de carta
//	 */
//	@GetMapping("/editarCarta")
//	public String mostrarFormularioEditarCarta() {
//		logger.info("Mostrando formulario para editar la carta de un restaurante.");
//		return "editarCarta"; // Nombre de la plantilla Thymeleaf para editar la carta
//	}
//
//	/**
//	 * Edita la carta de un restaurante.
//	 *
//	 * @param nombre Nombre del restaurante
//	 * @param items  Lista de ítems del menú
//	 * @param model Model para pasar datos a la vista
//	 * @return Vista de confirmación de edición
//	 */
//	@PostMapping("/editarCarta")
//	public String editarCarta(@RequestParam String nombre, @RequestBody List<ItemMenu> items, Model model) {
//		logger.info("Editando carta para el restaurante: {}", nombre);
//		restauranteService.editarCarta(nombre, items);
//		model.addAttribute("mensaje", "Carta actualizada con éxito.");
//		logger.info("Carta actualizada para el restaurante: {}", nombre);
//		return "confirmacionEdicionCarta"; // Vista Thymeleaf para confirmar la edición de la carta
//	}
//
//	/**
//	 * Crea un ítem de menú.
//	 *
//	 * @param nombre Nombre del ítem
//	 * @param precio Precio del ítem
//	 * @param tipo   Tipo del ítem
//	 * @return Nuevo ítem de menú creado
//	 */
//	private ItemMenu crearItem(String nombre, double precio, TipoItemMenu tipo) {
//		logger.info("Creando nuevo ítem de menú con nombre: {}, precio: {}, tipo: {}", nombre, precio, tipo);
//		ItemMenu item = restauranteService.crearItem(nombre, precio, tipo);
//		logger.info("Ítem de menú creado: {}", item);
//		return item;
//	}
}
