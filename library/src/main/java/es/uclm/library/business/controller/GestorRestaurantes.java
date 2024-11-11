package es.uclm.library.business.controller;

import es.uclm.library.business.entity.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/restaurantes")
public class GestorRestaurantes {

	/**
	 * Muestra el formulario para registrar un nuevo restaurante.
	 *
	 * @return Nombre de la plantilla Thymeleaf para el formulario de registro
	 */
	@GetMapping("/registrar")
	public String mostrarFormularioRegistroRestaurante() {
		return "registroRestaurante"; // Nombre de la plantilla Thymeleaf (html)
	}

	/**
	 * Registra un nuevo restaurante.
	 *
	 * @param nombre    Nombre del restaurante
	 * @param cif       CIF del restaurante
	 * @param direccion Dirección del restaurante
	 * @param model     Model para pasar datos a la vista
	 * @return Redirección o vista de confirmación
	 */
	@PostMapping("/registrar")
	public String registrarRestaurante(@RequestParam String nombre, @RequestParam String cif, @RequestBody Direccion direccion, Model model) {
		// TODO - implementar lógica para registrar un restaurante
		model.addAttribute("mensaje", "Restaurante registrado con éxito.");
		return "confirmacionRegistroRestaurante"; // Nombre de la plantilla Thymeleaf (html)
	}

	/**
	 * Muestra el formulario para editar la carta de un restaurante.
	 *
	 * @param model Model para pasar datos a la vista
	 * @return Nombre de la plantilla Thymeleaf para editar la carta
	 */
	@GetMapping("/editarCarta")
	public String mostrarFormularioEditarCarta(Model model) {
		// Agregar datos al modelo, si es necesario
		return "editarCarta"; // Nombre de la plantilla Thymeleaf (html)
	}

	/**
	 * Edita la carta de un restaurante.
	 *
	 * @param nombre Nombre del restaurante
	 * @param items  Lista de ítems del menú
	 * @param model Model para pasar datos a la vista
	 * @return Vista de confirmación de edición de la carta
	 */
	@PostMapping("/editarCarta")
	public String editarCarta(@RequestParam String nombre, @RequestParam List<ItemMenu> items, Model model) {
		// TODO - implementar lógica para editar la carta
		model.addAttribute("mensaje", "Carta actualizada con éxito.");
		return "confirmacionEdicionCarta"; // Nombre de la plantilla Thymeleaf (html)
	}

	/**
	 * Crea un ítem de menú (sin interfaz expuesta, solo como método interno).
	 *
	 * @param nombre Nombre del ítem
	 * @param precio Precio del ítem
	 * @param tipo   Tipo del ítem
	 * @return Nuevo ítem de menú creado
	 */
	private ItemMenu crearItem(String nombre, double precio, TipoItemMenu tipo) {
		// TODO - implementar lógica para crear un ítem
		throw new UnsupportedOperationException();
	}
}
