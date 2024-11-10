package es.uclm.library.business.controller;

import es.uclm.library.business.entity.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes")
public class GestorRestaurantes {

	/**
	 * Registra un nuevo restaurante.
	 *
	 * @param nombre Nombre del restaurante
	 * @param cif    CIF del restaurante
	 * @param direccion Dirección del restaurante
	 * @return Restaurante registrado
	 */
	@PostMapping("/registrar")
	public Restaurante registrarRestaurante(@RequestParam String nombre, @RequestParam String cif, @RequestBody Direccion direccion) {
		// TODO - implementar lógica para registrar un restaurante
		throw new UnsupportedOperationException();
	}

	/**
	 * Edita la carta de un restaurante.
	 *
	 * @param nombre Nombre del restaurante
	 * @param items  Lista de ítems del menú
	 */
	@PutMapping("/editarCarta")
	public void editarCarta(@RequestParam String nombre, @RequestBody List<ItemMenu> items) {
		// TODO - implementar lógica para editar la carta
		throw new UnsupportedOperationException();
	}

	/**
	 * Crea un ítem de menú.
	 *
	 * @param nombre Nombre del ítem
	 * @param precio Precio del ítem
	 * @param tipo   Tipo del ítem
	 * @return Nuevo ítem de menú creado
	 */
	private ItemMenu crearItem(String nombre, double precio, TipoItemMenu tipo) {
		// TODO - implementar lógica para crear un ítem
		throw new UnsupportedOperationException();
	}
}
