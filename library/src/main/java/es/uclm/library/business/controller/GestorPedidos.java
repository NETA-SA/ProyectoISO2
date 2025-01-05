package es.uclm.library.business.controller;

import es.uclm.library.business.entity.*;
import es.uclm.library.business.service.PedidoService;
import es.uclm.library.business.service.RepartoService;
import es.uclm.library.business.service.RestauranteService;
import es.uclm.library.business.service.LoginService;
import es.uclm.library.persistence.ItemPedidoDAO;
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
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/RealizarPedido")
@SessionAttributes("pedidoItems")
public class GestorPedidos {

	private static final Logger logger = LoggerFactory.getLogger(GestorPedidos.class);

	@Autowired
	private PedidoService pedidoService;

	@Autowired RepartoService RepartoService;

	@Autowired
	private RestauranteService restauranteService;

	@Autowired
	private LoginService loginService;

	@Autowired
	private ItemPedidoDAO itemPedidoDAO;

	@PersistenceContext
	private EntityManager entityManager;

	/* ###########################################################
	 * #                         Bloque 1                        #
	 * ###########################################################
	 * Parte encargada de iniciar la lista de items para el pedido y calcular las frecuencias de los items para que se muestren en la vista.
	 * Esto se hace debido a que la vista es dinámica y se actualiza cada vez que se añade o elimina un item del pedido.
	 */

	@ModelAttribute("pedidoItems")
	public List<ItemPedido> createPedidoItems() {
		return new ArrayList<>();
	}

	@ModelAttribute("itemFrequencies")
	public List<ItemFrequency> calculateItemFrequencies(@ModelAttribute("pedidoItems") List<ItemPedido> pedidoItems) {
		return pedidoItems.stream()
				.filter(item -> item.getId() != null) // Filter out items with null IDs
				.collect(Collectors.groupingBy(ItemPedido::getId, Collectors.counting()))
				.entrySet().stream()
				.map(entry -> new ItemFrequency(itemPedidoDAO.findById(entry.getKey()).orElse(null), entry.getValue()))
				.collect(Collectors.toList());
	}

	public static class ItemFrequency {
		private final ItemPedido item;
		private final long frequency;

		public ItemFrequency(ItemPedido item, long frequency) {
			this.item = item;
			this.frequency = frequency;
		}

		public ItemPedido getItem() {
			return item;
		}

		public long getFrequency() {
			return frequency;
		}
	}

	/* ###########################################################
	 * #                         Bloque 2                     #
	 * ###########################################################
	 * Parte encargada de cargar las cartas de un restaurante en la vista y de mostrar los items de una carta en la vista, asi como de añadir y eliminar items del pedido.
	 * Tambien se encarga de listar los restaurantes.
	 */
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
		List<Restaurante> restaurantes;
		Cliente cliente = null;

		if (email != null) {
			Usuario usuario = loginService.findUsuarioById(email);
			cliente = loginService.findClienteByUsuario(usuario);
			model.addAttribute("cliente", cliente);
		}

		if (cliente != null) {
			List<Restaurante> favoritos = new ArrayList<>(cliente.getFavoritos());
			List<Restaurante> noFavoritos = restauranteService.obtenerTodosRestaurantes().stream()
					.filter(restaurante -> !favoritos.contains(restaurante))
					.collect(Collectors.toList());
			favoritos.addAll(noFavoritos);
			restaurantes = favoritos;
		} else {
			restaurantes = restauranteService.obtenerTodosRestaurantes();
		}

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
	public String agregarItem(@RequestParam("itemId") Long itemId, @ModelAttribute("pedidoItems") List<ItemPedido> pedidoItems, Model model) {
		ItemMenu itemMenu = restauranteService.obtenerItemPorId(itemId);
		boolean itemExists = false;

		for (ItemPedido pedidoItem : pedidoItems) {
			if (pedidoItem.getNombre().equals(itemMenu.getNombre())) {
				pedidoItem.setCantidad(pedidoItem.getCantidad() + 1);
				itemExists = true;
				break;
			}
		}

		if (!itemExists) {
			ItemPedido itemPedido = new ItemPedido(itemMenu.getTipo(), itemMenu.getNombre(), itemMenu.getPrecio(), 1);
			pedidoItems.add(itemPedido);
		}

		logger.info("Item anadido a la lista: " + itemMenu.getNombre());
		model.addAttribute("pedidoItems", pedidoItems);
		double total = pedidoItems.stream().mapToDouble(i -> i.getPrecio() * i.getCantidad()).sum();
		model.addAttribute("total", total);
		model.addAttribute("itemFrequencies", calculateItemFrequencies(pedidoItems));
		return "RestaurantePedido :: pedidoResumen";
	}

	@PostMapping("/RestaurantePedido/quitarItem")
	public String quitarItem(@RequestParam("itemId") Long itemId, @ModelAttribute("pedidoItems") List<ItemPedido> pedidoItems, Model model) {
		ItemMenu itemMenu = restauranteService.obtenerItemPorId(itemId);
		pedidoItems.removeIf(i -> i.getNombre().equals(itemMenu.getNombre())); // Compare by name instead of id
		logger.info("Item eliminado de la lista: " + itemMenu.getNombre());
		model.addAttribute("pedidoItems", pedidoItems);
		double total = pedidoItems.stream().mapToDouble(i -> i.getPrecio() * i.getCantidad()).sum(); // Recalculate the total
		model.addAttribute("total",total);
		model.addAttribute("itemFrequencies", calculateItemFrequencies(pedidoItems)); // Update frequencies
		return "RestaurantePedido :: pedidoResumen";
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
	/* ###########################################################
	 * #                         Bloque 3                 #
	 * ###########################################################
	 * Parte encargada de añadir o eliminar un restaurante de la lista de favoritos de un cliente.
	 */

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

	/* ###########################################################
	 * #                         Bloque 4                      #
	 * ###########################################################
	 * Parte encargada de realizar el pedido y de realizar el pago del pedido.
	 */
	@PostMapping("/RestaurantePedido/realizarPedido")
	public String realizarPedido(@RequestParam("restauranteId") Long restauranteId, @ModelAttribute("pedidoItems") List<ItemPedido> pedidoItems, HttpSession session, Model model) {
		String email = (String) session.getAttribute("email");
		Cliente cliente = loginService.findClienteByUsuario(loginService.findUsuarioById(email));
		Restaurante restaurante = restauranteService.obtenerRestaurantePorId(restauranteId);

		Pedido pedido = new Pedido();
		pedido.setCliente(cliente);
		pedido.setRestaurante(restaurante);
		pedido.setFecha(new Date());
		pedido.setEstado(EstadoPedido.PEDIDO);

		// Set the restaurant for each ItemPedido and persist them
		for (ItemPedido itemPedido : pedidoItems) {
			itemPedido.setRestaurante(restaurante);
			itemPedidoDAO.save(itemPedido);
		}
		pedido.setItems(pedidoItems);

		pedidoService.crearPedido(pedido);

		// Redirect to the payment interface
		return "redirect:/RealizarPedido/PagoPedido?pedidoId=" + pedido.getId();
	}

	@GetMapping("/PagoPedido")
	public String mostrarPagoPedido(@RequestParam("pedidoId") Long pedidoId, Model model, HttpSession session) {
		String email = (String) session.getAttribute("email");
		Cliente cliente = loginService.findClienteByUsuario(loginService.findUsuarioById(email));
		Pedido pedido = pedidoService.obtenerPedidoPorId(pedidoId);
		Restaurante restaurante = pedido.getRestaurante();

		double total = pedido.getItems().stream().mapToDouble(i -> i.getPrecio() * i.getCantidad()).sum();

		model.addAttribute("cliente", cliente);
		model.addAttribute("pedido", pedido);
		model.addAttribute("restaurante", restaurante);
		model.addAttribute("total", total);
		return "PagoPedido"; // Ensure this matches the name of your HTML template
	}

	// GestorPedidos.java
	// GestorPedidos.java
	@PostMapping("/PagoPedido/realizarPago")
	public String realizarPago(@RequestParam("pedidoId") Long pedidoId, @RequestParam("calle") String calle, @RequestParam("numero") String numero, @RequestParam("complemento") String complemento, @RequestParam("municipio") String municipio, @RequestParam("codigoPostal") String codigoPostal, @RequestParam("metodoPago") MetodoPago metodoPago, HttpSession session, Model model) {
		logger.info("Entrando en realizarPago");
		String email = (String) session.getAttribute("email");
		Cliente cliente = loginService.findClienteByUsuario(loginService.findUsuarioById(email));
		Pedido pedido = pedidoService.obtenerPedidoPorId(pedidoId);

		if (cliente == null || pedido == null) {
			logger.error("Cliente o pedido no encontrado");
			model.addAttribute("message", "Error: Cliente o pedido no encontrado");
			return "error";
		}

		CodigoPostal codigopostal;
		try {
			codigopostal = CodigoPostal.fromCode(codigoPostal);
			if (!codigopostal.getLocation().equalsIgnoreCase(municipio)) {
				model.addAttribute("message", "El municipio no concuerda con el código postal.");
				return mostrarPagoPedido(pedidoId, model, session);
			}
		} catch (IllegalArgumentException e) {
			model.addAttribute("message", "Aún no hay servicio para esa localización.");
			return mostrarPagoPedido(pedidoId, model, session);
		}

		Direccion direccion = new Direccion(codigopostal, calle, numero, complemento, municipio);
		pedidoService.guardarDireccion(direccion);
		cliente.getDirecciones().add(direccion);
		direccion.setCliente(cliente);
		loginService.updateCliente(cliente);

		Pago pago = new Pago(pedido, metodoPago, UUID.randomUUID(), new Date());
		logger.info("Guardando pago con datos: " + pago.getPedido().getId() + ", " + pago.getTipo() + ", " + pago.getIdTransaccion() + ", " + pago.getFechaTransaccion());
		pago = pedidoService.guardarPago(pago);
		pedido.setPago(pago);
		pedido.setEstado(EstadoPedido.PAGADO);
		pedidoService.actualizarPedido(pedido);

		Restaurante restaurante = pedido.getRestaurante();
		Direccion direccionRestaurante = restaurante.getDireccion();
		Direccion direccionCliente = direccion;
		Repartidor repartidor = RepartoService.obtenerRepartidorDisponible();

		if (repartidor == null) {
			logger.error("No hay repartidores disponibles en este momento");
			model.addAttribute("message", "No hay repartidores disponibles en este momento");
			return mostrarPagoPedido(pedidoId, model, session);
		}

		ServicioEntrega servicioEntrega = new ServicioEntrega(pedido, direccionCliente, direccionRestaurante, repartidor, new Date(), null);
		servicioEntrega = pedidoService.guardarServicioEntrega(servicioEntrega);

		pedido.setEntrega(servicioEntrega);
		pedidoService.actualizarPedido(pedido);

		// Marcar el repartidor como no disponible
		repartidor.setDisponible(false);
		RepartoService.actualizarRepartidor(repartidor);

		logger.info("Pago realizado con exito para el pedido: " + pedidoId);
		model.addAttribute("message", "Pago realizado con éxito");
		return "redirect:/";
	}

	@GetMapping("/EstadoPedido")
	public String estadoPedido(HttpSession session, Model model) {
		String email = (String) session.getAttribute("email");
		Usuario usuario = loginService.findUsuarioById(email);
		Cliente cliente = loginService.findClienteByUsuario(usuario);

		model.addAttribute("pedidos", cliente.getPedidos());
		return "EstadoPedido";
	}

}