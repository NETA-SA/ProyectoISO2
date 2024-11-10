package es.uclm.library.business.controller;

import es.uclm.library.business.entity.CodigoPostal;
import es.uclm.library.business.entity.ServicioEntrega;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/repartos")
public class GestorRepartos {

	/**
	 * Marca un pedido como recogido.
	 *
	 * @param servicio Servicio de entrega asociado al pedido
	 */
	@PostMapping("/marcarRecogido")
	public void marcarPedidoRecogido(@RequestBody ServicioEntrega servicio) {
		// TODO - implementar lógica para marcar pedido como recogido
		throw new UnsupportedOperationException();
	}

	/**
	 * Marca un pedido como entregado.
	 *
	 * @param servicio Servicio de entrega asociado al pedido
	 */
	@PostMapping("/marcarEntregado")
	public void marcarPedidoEntregado(@RequestBody ServicioEntrega servicio) {
		// TODO - implementar lógica para marcar pedido como entregado
		throw new UnsupportedOperationException();
	}

	/**
	 * Registra un nuevo repartidor.
	 *
	 * @param nombre   Nombre del repartidor
	 * @param apellidos Apellidos del repartidor
	 * @param nif       NIF del repartidor
	 * @param zonas     Lista de códigos postales en los que el repartidor puede entregar
	 */
	@PostMapping("/registrarRepartidor")
	public void registrarRepartidor(@RequestParam String nombre, @RequestParam String apellidos, @RequestParam String nif, @RequestBody List<CodigoPostal> zonas) {
		// TODO - implementar lógica para registrar repartidor
		throw new UnsupportedOperationException();
	}
}
