// GestorPedidos.java
package es.uclm.library.business.controller;

import es.uclm.library.business.entity.Cliente;
import es.uclm.library.business.entity.Restaurante;
import es.uclm.library.business.entity.Usuario;
import es.uclm.library.business.service.LoginService;
import es.uclm.library.business.service.PedidoService;
import es.uclm.library.business.service.RestauranteService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;
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

	@Autowired
	private LoginService loginService;

	@PersistenceContext
	private EntityManager entityManager;

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