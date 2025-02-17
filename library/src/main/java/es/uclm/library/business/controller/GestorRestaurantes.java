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
import org.springframework.validation.BindingResult;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

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
		String idUsuario = (String) session.getAttribute("email");

		if (idUsuario == null) {
			model.addAttribute("error", "Usuario no autenticado.");
			return "redirect:/login";
		}

		Long idRestaurante = restauranteService.obtenerIdRestaurantePorUsuario(idUsuario);
		model.addAttribute("idRestaurante", idRestaurante);

		return "RestaurantesPag";
	}

	@GetMapping("/RestaurantePedido")
	public String mostrarRestaurantePedido(@RequestParam("restauranteId") Long restauranteId, @RequestParam(value = "cartaId", required = false) Long cartaId, Model model) {
		List<CartaMenu> cartasMenu = restauranteService.obtenerCartasPorRestaurante(restauranteId);
		model.addAttribute("cartasMenu", cartasMenu);
		model.addAttribute("restauranteId", restauranteId);


		if (cartaId != null) {
			CartaMenu carta = restauranteService.obtenerCartaPorId(cartaId);
			model.addAttribute("items", carta.getItems());
			model.addAttribute("selectedCartaId", cartaId);
		}

		return "RestaurantePedido";
	}


	@GetMapping("/DarAltaMenu")
	public String mostrarFormularioAltaMenu(HttpSession session, Model model) {
		// Obtener el ID del restaurante del usuario desde la sesión
		String idUsuario = (String) session.getAttribute("email");
		Long idRestaurante = restauranteService.obtenerIdRestaurantePorUsuario(idUsuario);

		// Obtener las cartas asociadas al restaurante
		List<CartaMenu> cartasMenu = restauranteService.obtenerCartasPorRestaurante(idRestaurante);

		// Pasar las cartas al modelo para Thymeleaf
		model.addAttribute("cartasMenu", cartasMenu);

		// Crear un nuevo ItemMenu para vincular al formulario
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
			String idUsuario = (String) session.getAttribute("email");
			Long idRestaurante = restauranteService.obtenerIdRestaurantePorUsuario(idUsuario);

			// Asociar restaurante al ítem del menú
			Restaurante restaurante = new Restaurante(idRestaurante);
			itemMenu.setRestaurante(restaurante);

			itemMenuDAO.save(itemMenu);


			// Manejo de errores en la validación
			if (result.hasErrors()) {
				// Si hay errores, volver a cargar las cartas existentes
				List<CartaMenu> cartasMenu = restauranteService.obtenerCartasPorRestaurante(idRestaurante);
				model.addAttribute("cartasMenu", cartasMenu);

				return "DarAltaMenu"; // Vuelve a la misma página con errores
			}

			// Crear o buscar la carta relacionada
			CartaMenu cartaSeleccionada = null;

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

				// Crear una nueva carta si se ingresó su nombre
				if (!restauranteService.cartaExiste(nuevaCarta, idRestaurante)) {
					cartaSeleccionada = new CartaMenu();
					cartaSeleccionada.setNombre(nuevaCarta);
					cartaSeleccionada.setRestaurante(new Restaurante(idRestaurante));

					cartaSeleccionada = restauranteService.guardarNuevaCarta(cartaSeleccionada);
				} //else {
					//model.addAttribute("error", "La carta ya existe.");
				//}

			} else if (cartaMenuId != null) {
				// Usar una carta existente
				cartaSeleccionada = restauranteService.obtenerCartaPorId(cartaMenuId);
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

			// Recargar las cartas existentes y añadirlas al modelo para la vista
			List<CartaMenu> cartasMenu = restauranteService.obtenerCartasPorRestaurante(idRestaurante);
			model.addAttribute("cartasMenu", cartasMenu);

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

	@GetMapping("/verCartas")
	public String mostrarCartas(HttpSession session, Model model) {
		String idUsuario = (String) session.getAttribute("email");
		Long idRestaurante = restauranteService.obtenerIdRestaurantePorUsuario(idUsuario);

		List<CartaMenu> cartasMenu = restauranteService.obtenerCartasPorRestaurante(idRestaurante);
		model.addAttribute("cartasMenu", cartasMenu);
		return "verCartas"; // Nombre de la nueva vista que crearemos
	}

	@GetMapping("/verItems")
	public String mostrarItems(@RequestParam("cartaId") Long cartaId, Model model) {
		CartaMenu carta = restauranteService.obtenerCartaPorId(cartaId);
		if (carta == null) {
			model.addAttribute("error", "Carta no encontrada.");
			return "error"; // Vista de error (si no existe o no corresponde al restaurante del usuario)
		}

		model.addAttribute("items", carta.getItems());
		model.addAttribute("cartaId", cartaId);
		return "verItems"; // Nombre de la nueva vista que crearemos
	}

	@GetMapping("/editarItem")
	public String editarItem(@RequestParam("itemId") Long itemId, @RequestParam("cartaId") Long cartaId, Model model) {
		ItemMenu item = itemMenuDAO.findById(itemId).orElse(null);
		if (item == null) {
			model.addAttribute("error", "Item no encontrado.");
			return "error";
		}

		List<CartaMenu> cartasMenu = restauranteService.obtenerCartasPorRestaurante(item.getRestaurante().getId());
		model.addAttribute("item", item);
		model.addAttribute("cartasMenu", cartasMenu);
		model.addAttribute("cartaId", cartaId);
		return "editarItem"; // Nueva vista
	}

	@PostMapping("/guardarEdicionItem")
	public String guardarEdicionItem(
			HttpSession session,
			@ModelAttribute @Valid ItemMenu itemMenu,
			BindingResult result,
			@RequestParam("nuevaCartaId") Long nuevaCartaId,
			Model model) {

		String idUsuario = (String) session.getAttribute("email");
		Long idRestaurante = restauranteService.obtenerIdRestaurantePorUsuario(idUsuario);

		// Verificar errores en los datos del formulario
		if (result.hasErrors()) {
			model.addAttribute("error", "Error en la edición del ítem.");
			model.addAttribute("cartasMenu", restauranteService.obtenerCartasPorRestaurante(idRestaurante)); // Recargar cartas
			model.addAttribute("item", itemMenu); // Volver a cargar el ítem
			return "editarItem";
		}

		// Recuperar la carta asociada
		CartaMenu nuevaCarta = restauranteService.obtenerCartaPorId(nuevaCartaId);
		if (nuevaCarta == null) {
			model.addAttribute("error", "Carta no encontrada.");
			model.addAttribute("cartasMenu", restauranteService.obtenerCartasPorRestaurante(idRestaurante)); // Recargar cartas
			model.addAttribute("item", itemMenu); // Volver a cargar el ítem
			return "editarItem";
		}

		itemMenu.setRestaurante(nuevaCarta.getRestaurante());

		// Guardar el ítem actualizado
		itemMenuDAO.save(itemMenu);

		// Actualizar la carta en la base de datos (si es necesario)
		nuevaCarta.getItems().add(itemMenu);
		restauranteService.actualizarCarta(nuevaCarta);

		// Redirigir con mensaje de éxito
		model.addAttribute("success", "Ítem editado correctamente.");
		return "RestaurantesPag";
	}

}