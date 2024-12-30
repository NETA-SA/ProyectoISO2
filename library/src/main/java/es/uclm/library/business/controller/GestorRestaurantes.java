package es.uclm.library.business.controller;

import es.uclm.library.business.entity.Direccion;
import es.uclm.library.business.entity.ItemMenu;
import es.uclm.library.business.entity.CartaMenu;
import es.uclm.library.business.entity.Restaurante;
import es.uclm.library.business.entity.TipoItemMenu;
import es.uclm.library.persistence.CartaMenuDAO;
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
			@RequestParam(value = "nuevaCarta", required = false) String nuevaCarta, // Campo para nueva carta
			@RequestParam(value = "cartaMenuId", required = false) Long cartaMenuId, // Campo para seleccionar carta existente
			Model model) {

		if (result.hasErrors()) {
			model.addAttribute("error", "La información proporcionada contiene errores.");
			return "DarAltaMenu";
		}

		try {
			String idUsuario = (String) session.getAttribute("idUsuario");
			Long idRestaurante = restauranteService.obtenerIdRestaurantePorUsuario(idUsuario);

			// Asociar restaurante al ítem del menú
			Restaurante restaurante = new Restaurante(idRestaurante);
			itemMenu.setRestaurante(restaurante);

			itemMenuDAO.save(itemMenu);


			// Validar si es una nueva carta o una carta existente
			if (nuevaCarta != null && !nuevaCarta.trim().isEmpty()) {
				// Crear una nueva carta
				CartaMenu nuevaCartaMenu = new CartaMenu();
				nuevaCartaMenu.setNombre(nuevaCarta.trim());
				nuevaCartaMenu.setRestaurante(restaurante);
				nuevaCartaMenu = restauranteService.guardarNuevaCarta(nuevaCartaMenu);

				// Asociar ítem del menú a la nueva carta
				nuevaCartaMenu.getItems().add(itemMenu);
				restauranteService.actualizarCarta(nuevaCartaMenu);

			} else if (cartaMenuId != null) {
				// Usar una carta existente
				CartaMenu cartaSeleccionada = restauranteService.obtenerCartaPorId(cartaMenuId);
				if (cartaSeleccionada == null || !cartaSeleccionada.getRestaurante().getId().equals(idRestaurante)) {
					model.addAttribute("error", "Carta seleccionada inválida o no pertenece al restaurante del usuario.");
					return "DarAltaMenu";
				}

				// Asociar ítem del menú a la carta existente
				cartaSeleccionada.getItems().add(itemMenu);
				restauranteService.actualizarCarta(cartaSeleccionada);

			} else {
				model.addAttribute("error", "Debes ingresar una nueva carta o seleccionar una existente.");
				return "DarAltaMenu";
			}

			model.addAttribute("success", "Ítem agregado con éxito.");
			return "DarAltaMenu";

		} catch (Exception e) {
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
