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
