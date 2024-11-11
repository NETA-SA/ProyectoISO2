package es.uclm.library.business.controller;

import es.uclm.library.persistence.RestauranteDAO;
import es.uclm.library.business.entity.Cliente;
import es.uclm.library.business.entity.Direccion;
import es.uclm.library.business.entity.Restaurante;
import es.uclm.library.business.entity.CodigoPostal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/clientes")
public class GestorClientes {

	@Autowired
	private RestauranteDAO restauranteDAO;

	/**
	 * Muestra una vista de restaurantes en una zona específica.
	 *
	 * @param zona Código postal de la zona
	 * @param model Model para pasar datos a la vista
	 * @return Nombre de la plantilla Thymeleaf para la lista de restaurantes
	 */
	@GetMapping("/restaurantes/zona")
	public String buscarRestaurante(@RequestParam CodigoPostal zona, Model model) {
		// TODO - implementar la lógica de búsqueda por zona
		List<Restaurante> restaurantes = // lógica para obtener restaurantes;
				model.addAttribute("restaurantes", restaurantes);
		return "restaurantes"; // Nombre de la plantilla Thymeleaf (html)
	}

	/**
	 * Muestra una vista de restaurantes que coinciden con la búsqueda.
	 *
	 * @param cadenaBusqueda Palabra clave para buscar restaurantes
	 * @param model Model para pasar datos a la vista
	 * @return Nombre de la plantilla Thymeleaf para la lista de restaurantes
	 */
	@GetMapping("/restaurantes/busqueda")
	public String buscarRestaurante(@RequestParam String cadenaBusqueda, Model model) {
		// TODO - implementar la lógica de búsqueda por cadena
		List<Restaurante> restaurantes = // lógica para obtener restaurantes;
				model.addAttribute("restaurantes", restaurantes);
		return "restaurantes";
	}

	/**
	 * Muestra una vista para registrar un cliente.
	 *
	 * @return Nombre de la plantilla Thymeleaf para el formulario de registro
	 */
	@GetMapping("/registrar")
	public String mostrarFormularioRegistro() {
		return "registroCliente"; //Nombre de la plantilla Thymeleaf (html)
	}

	/**
	 * Procesa el registro de un nuevo cliente.
	 *
	 * @param nombre Nombre del cliente
	 * @param apellido Apellido del cliente
	 * @param direccion Dirección del cliente
	 * @param model Model para pasar datos a la vista
	 * @return Redirección o confirmación de registro
	 */
	@PostMapping("/registrar")
	public String registrarCliente(@RequestParam String nombre, @RequestParam String apellido, @RequestBody Direccion direccion, Model model) {
		// TODO - implementar lógica de registro de cliente
		Cliente cliente = // lógica para registrar cliente;
				model.addAttribute("cliente", cliente);
		return "confirmacionRegistro"; // Nombre de la plantilla Thymeleaf (html)
	}


}
