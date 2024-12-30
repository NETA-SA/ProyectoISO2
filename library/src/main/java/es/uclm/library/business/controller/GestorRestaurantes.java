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
import es.uclm.library.persistence.ItemMenuDAO;
import jakarta.validation.Valid; // Usar Jakarta en lugar de javax (según las dependencias).
import org.springframework.validation.BindingResult;
import jakarta.servlet.http.HttpSession;

import java.util.List;

@Controller
@RequestMapping("/restaurantes")
public class GestorRestaurantes {

	private static final Logger logger = LoggerFactory.getLogger(GestorRestaurantes.class);

	@Autowired
	private RestauranteService restauranteService;

	@Autowired
	private ItemMenuDAO itemMenuDAO;

	@GetMapping("/RestaurantesPag")
	public String mostrarPaginaRestaurantes(HttpSession session, Model model) {
		String idUsuario = (String) session.getAttribute("idUsuario");

		if (idUsuario == null) {
			model.addAttribute("error", "Usuario no autenticado.");
			return "redirect:/login";
		}

		Long idRestaurante = restauranteService.obtenerIdRestaurantePorUsuario(idUsuario);
		model.addAttribute("idRestaurante", idRestaurante);

		return "RestaurantesPag";
	}


	@GetMapping("/DarAltaMenu")
	public String mostrarFormularioAltaMenu(Model model) {
		model.addAttribute("itemMenu", new ItemMenu());
		return "DarAltaMenu";
	}

	@PostMapping("/guardarMenu")
	public String guardarMenu(
			HttpSession session,
			@ModelAttribute @Valid ItemMenu itemMenu,
			BindingResult result,
			Model model) {

		if (result.hasErrors()) {
			model.addAttribute("error", "La información proporcionada contiene errores.");
			return "DarAltaMenu";
		}

		try {
			String idUsuario = (String) session.getAttribute("idUsuario");
			Long idRestaurante = restauranteService.obtenerIdRestaurantePorUsuario(idUsuario);

			// Usa el nuevo constructor para crear un objeto Restaurante con el ID
			Restaurante restaurante = new Restaurante(idRestaurante);

			// Asocia el restaurante al ítem del menú
			itemMenu.setRestaurante(restaurante);

			itemMenuDAO.save(itemMenu);
			model.addAttribute("success", "Ítem agregado con éxito.");
			return "DarAltaMenu";

		} catch (Exception e) {
			// Error al procesar la solicitud
			model.addAttribute("error", e.getMessage());
			return "DarAltaMenu";
		}
	}

	@GetMapping("/ListaRestaurantes")
	public String listarRestaurantes(Model model) {
		// Lógica para obtener listado de restaurantes de la base de datos
		List<Restaurante> listaRestaurantes = restauranteService.obtenerTodosRestaurantes();

		// Usar logger para debug
		if (listaRestaurantes.isEmpty()) {
			logger.info("No se encontraron restaurantes en la base de datos.");
		} else {
			logger.info("Restaurantes encontrados: " + listaRestaurantes.size());
			listaRestaurantes.forEach(restaurante -> logger.info(restaurante.toString()));
		}

		model.addAttribute("restaurantes", listaRestaurantes);
		// Enviar al template correspondiente con los datos
		return "ListaRestaurantes";



	}
}
