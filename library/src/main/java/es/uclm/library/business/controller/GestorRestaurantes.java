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

	@GetMapping
	public String listarRestaurantes(Model model) {
		logger.info("Solicitud para listar todos los restaurantes recibida");
		List<Restaurante> restaurantes = restauranteService.obtenerListaDeRestaurantes();
		logger.debug("Número de restaurantes: {}", restaurantes.size());
		model.addAttribute("restaurantes", restaurantes);
		return "lista_restaurantes";
	}

	@GetMapping("/{id}/menu")
	public String listarItemsDelMenu(@PathVariable int id, Model model) {
		logger.info("Solicitud para listar ítems del menú del restaurante con ID: {}", id);
		try {
			List<ItemMenu> items = restauranteService.obtenerItemsDeMenu((long) id);
			logger.info("Items recibidos");
			model.addAttribute("items", items);
			return "lista_items_menu";
		} catch (IllegalArgumentException e) {
			logger.error("Error al intentar recuperar ítems del menú para el restaurante con ID: {}", id, e);
			model.addAttribute("mensajeError", e.getMessage());
			return "/redirect:/restaurantes";
		}
	}
}


