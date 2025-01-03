package es.uclm.library.business.controller;

import es.uclm.library.business.entity.*;
import es.uclm.library.business.service.PedidoService;
import es.uclm.library.business.service.RestauranteService;
import es.uclm.library.business.service.LoginService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/RealizarPedido")
@SessionAttributes("pedidoItems")
public class GestorPedidos {

	private static final Logger logger = LoggerFactory.getLogger(GestorPedidos.class);

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private RestauranteService restauranteService;

	@Autowired
	private LoginService loginService;

	@PersistenceContext
	private EntityManager entityManager;

	@ModelAttribute("pedidoItems")
	public List<ItemMenu> createPedidoItems() {
		return new ArrayList<>();
	}

	@ModelAttribute("itemFrequencies")
	public List<ItemFrequency> calculateItemFrequencies(@ModelAttribute("pedidoItems") List<ItemMenu> pedidoItems) {
		return pedidoItems.stream()
				.collect(Collectors.groupingBy(ItemMenu::getId, Collectors.counting()))
				.entrySet().stream()
				.map(entry -> new ItemFrequency(restauranteService.obtenerItemPorId(entry.getKey()), entry.getValue()))
				.collect(Collectors.toList());
	}

	public static class ItemFrequency {
		private final ItemMenu item;
		private final long frequency;

		public ItemFrequency(ItemMenu item, long frequency) {
			this.item = item;
			this.frequency = frequency;
		}

		public ItemMenu getItem() {
			return item;
		}

		public long getFrequency() {
			return frequency;
		}
	}

	@GetMapping("/cartas")
	public String cargarCartas(@RequestParam("restauranteId") Long restauranteId, Model model) {
		List<CartaMenu> cartasMenu = restauranteService.obtenerCartasPorRestaurante(restauranteId);
		model.addAttribute("cartasMenu", cartasMenu);
		return "cartas :: cartasFragment";
	}

	@GetMapping
	public String showRealizarPedido(Model model) {
		return "RealizarPedido"; // Asegúrate de que el nombre de la vista coincida con el archivo HTML
	}

	@GetMapping("/ListaRestaurantes")
	public String listarRestaurantes(Model model, HttpSession session) {
		String email = (String) session.getAttribute("email");
		if (email != null) {
			Usuario usuario = loginService.findUsuarioById(email);
			Cliente cliente = loginService.findClienteByUsuario(usuario);
			model.addAttribute("cliente", cliente);
		}
		List<Restaurante> restaurantes = restauranteService.obtenerTodosRestaurantes();
		model.addAttribute("restaurantes", restaurantes);
		return "RealizarPedido";
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

	@PostMapping("/RestaurantePedido/agregarItem")
	public String agregarItem(@RequestParam("itemId") Long itemId, @ModelAttribute("pedidoItems") List<ItemMenu> pedidoItems, Model model) {
		ItemMenu item = restauranteService.obtenerItemPorId(itemId);
		boolean itemExists = false;

		for (ItemMenu pedidoItem : pedidoItems) {
			if (pedidoItem.getId().equals(itemId)) {
				pedidoItem.setCantidad(pedidoItem.getCantidad() + 1); // Incrementa la cantidad
				itemExists = true;
				break;
			}
		}

		if (!itemExists) {
			item.setCantidad(1); // Establece la cantidad inicial a 1
			pedidoItems.add(item);
		}

		logger.info("Item añadido a la lista: " + item.getNombre());
		model.addAttribute("pedidoItems", pedidoItems);
		double total = pedidoItems.stream().mapToDouble(i -> i.getPrecio() * i.getCantidad()).sum(); // Recalcula el total
		model.addAttribute("total", total);
		model.addAttribute("itemFrequencies", calculateItemFrequencies(pedidoItems)); // Actualiza las frecuencias
		return "RestaurantePedido :: pedidoResumen";
	}

	@PostMapping("/RestaurantePedido/quitarItem")
	public String quitarItem(@RequestParam("itemId") Long itemId, @ModelAttribute("pedidoItems") List<ItemMenu> pedidoItems, Model model) {
		ItemMenu item = restauranteService.obtenerItemPorId(itemId);
		pedidoItems.removeIf(i -> i.getId().equals(itemId)); // Ensure the correct item is removed
		logger.info("Item eliminado de la lista: " + item.getNombre());
		model.addAttribute("pedidoItems", pedidoItems);
		double total = pedidoItems.stream().mapToDouble(ItemMenu::getPrecio).sum();
		model.addAttribute("total", total);
		model.addAttribute("itemFrequencies", calculateItemFrequencies(pedidoItems)); // Update frequencies
		return "RestaurantePedido :: pedidoResumen";
	}

	@PostMapping("/RestaurantePedido/realizarPedido")
	public String realizarPedido(@RequestParam("restauranteId") Long restauranteId, @ModelAttribute("pedidoItems") List<ItemMenu> pedidoItems, HttpSession session, Model model) {
		String email = (String) session.getAttribute("email");
		Cliente cliente = loginService.findClienteByUsuario(loginService.findUsuarioById(email));
		Restaurante restaurante = restauranteService.obtenerRestaurantePorId(restauranteId);

		Pedido pedido = new Pedido();
		pedido.setCliente(cliente);
		pedido.setRestaurante(restaurante);
		pedido.setItems(pedidoItems);
		pedido.setFecha(new Date());
		pedido.setEstado(EstadoPedido.PEDIDO);

		pedidoService.crearPedido(pedido);
		model.addAttribute("message", "Pedido realizado con éxito");
		return "redirect:/";
	}

	@PostMapping("/marcarFavorito")
	public String marcarFavorito(@RequestParam("restauranteId") Long restauranteId, HttpSession session, Model model) {
		try {
			String email = (String) session.getAttribute("email");
			if (email == null) {
				throw new IllegalStateException("Usuario no autenticado");
			}

			Usuario usuario = loginService.findUsuarioById(email);
			Cliente cliente = loginService.findClienteByUsuario(usuario);

			Restaurante restaurante = entityManager.find(Restaurante.class, restauranteId);
			cliente.getFavoritos().add(restaurante);
			loginService.updateCliente(cliente);

			model.addAttribute("message", "Restaurante marcado como favorito");
		} catch (Exception e) {
			logger.error("Error al marcar favorito", e);
			model.addAttribute("message", "Error al marcar favorito");
		}
		return "redirect:/RealizarPedido/ListaRestaurantes";
	}

	@PostMapping("/toggleFavorito")
	@ResponseBody
	public String toggleFavorito(@RequestParam("restauranteId") Long restauranteId, @RequestParam("isChecked") boolean isChecked, HttpSession session) {
		try {
			String email = (String) session.getAttribute("email");
			if (email == null) {
				throw new IllegalStateException("Usuario no autenticado");
			}

			Usuario usuario = loginService.findUsuarioById(email);
			Cliente cliente = loginService.findClienteByUsuario(usuario);

			Restaurante restaurante = entityManager.find(Restaurante.class, restauranteId);
			if (isChecked) {
				cliente.getFavoritos().add(restaurante);
			} else {
				cliente.getFavoritos().remove(restaurante);
			}
			loginService.updateCliente(cliente);

			return "Success";
		} catch (Exception e) {
			logger.error("Error al actualizar favorito", e);
			return "Error";
		}
	}
}