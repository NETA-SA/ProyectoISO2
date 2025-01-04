package es.uclm.library.business.controller;

import es.uclm.library.business.entity.*;
import es.uclm.library.business.service.RepartoService;
import es.uclm.library.business.service.PedidoService;
import es.uclm.library.business.service.LoginService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/Repartos")
public class GestorRepartos {

	private static final Logger logger = LoggerFactory.getLogger(GestorRepartos.class);

	@Autowired
	private RepartoService repartoService;

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private LoginService loginService;

	@GetMapping
	public String mostrarRepartos(HttpSession session, Model model) {
		String email = (String) session.getAttribute("email");
		Usuario usuario = loginService.findUsuarioById(email);
		Repartidor repartidor = loginService.findRepartidorByUsuario(usuario);

		List<ServicioEntrega> serviciosEntrega = repartoService.obtenerServiciosPorRepartidor(repartidor);
		model.addAttribute("serviciosEntrega", serviciosEntrega);
		return "Repartos";
	}

	@PostMapping("/recogerPedido")
	public String recogerPedido(@RequestParam("servicioId") Long servicioId, Model model) {
		logger.info("Recoger pedido");
		ServicioEntrega servicioEntrega = repartoService.obtenerServicioPorId(servicioId);
		Pedido pedido = servicioEntrega.getPedido();
		if (pedido.getEstado() == EstadoPedido.PAGADO) {
			pedido.setEstado(EstadoPedido.RECOGIDO);
			pedidoService.actualizarPedido(pedido);
			model.addAttribute("message", "Pedido recogido con éxito");
		}
		return "redirect:/Repartos";
	}

	@PostMapping("/entregarPedido")
	public String entregarPedido(@RequestParam("servicioId") Long servicioId, Model model) {
		logger.info("Entregar pedido");
		ServicioEntrega servicioEntrega = repartoService.obtenerServicioPorId(servicioId);
		Pedido pedido = servicioEntrega.getPedido();
		if (pedido.getEstado() == EstadoPedido.RECOGIDO) {
			pedido.setEstado(EstadoPedido.ENTREGADO);
			pedidoService.actualizarPedido(pedido);
			servicioEntrega.setFechaEntrega(new Date());
			repartoService.actualizarServicioEntrega(servicioEntrega);

			// Eliminar la entrega del repartidor
			Repartidor repartidor = servicioEntrega.getRepartidor();
			repartidor.getServiciosEntrega().remove(servicioEntrega);
			repartoService.actualizarRepartidor(repartidor);

			model.addAttribute("message", "Pedido entregado con éxito");
		}
		return "redirect:/Repartos";
	}
}