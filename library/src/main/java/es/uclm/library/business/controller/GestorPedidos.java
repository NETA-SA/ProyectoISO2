package es.uclm.library.business.controller;

import es.uclm.library.business.entity.Cliente;
import es.uclm.library.business.entity.ItemMenu;
import es.uclm.library.business.entity.Pedido;
import es.uclm.library.business.entity.Restaurante;
import es.uclm.library.business.service.PedidoService;
import es.uclm.library.business.service.RestauranteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/RealizarPedido")
public class GestorPedidos {

	private static final Logger logger = LoggerFactory.getLogger(GestorPedidos.class);

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private RestauranteService restauranteService;

	@GetMapping
	public String showRealizarPedido(Model model) {
		return "RealizarPedido"; // Asegúrate de que el nombre de la vista coincida con el archivo HTML
	}

	@GetMapping("/ListaRestaurantes")
	public String listarRestaurantes(Model model) {
		List<Restaurante> restaurantes = restauranteService.obtenerTodosRestaurantes();
		model.addAttribute("restaurantes", restaurantes);
		return "RealizarPedido";
	}
}
