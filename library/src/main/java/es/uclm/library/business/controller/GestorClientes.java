package es.uclm.library.business.controller;

import es.uclm.library.persistence.RestauranteDAO;
import es.uclm.library.business.entity.Cliente;
import es.uclm.library.business.entity.Direccion;
import es.uclm.library.business.entity.Restaurante;
import es.uclm.library.business.entity.CodigoPostal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class GestorClientes {

	@Autowired
	private RestauranteDAO restauranteDAO;

	/**
	 * Busca restaurantes en una zona específica.
	 *
	 * @param zona Código postal de la zona
	 * @return Lista de restaurantes en la zona
	 */
	@GetMapping("/restaurantes/zona")
	public List<Restaurante> buscarRestaurante(@RequestParam CodigoPostal zona) {
		// TODO - implementar la lógica de búsqueda por zona
		throw new UnsupportedOperationException();
	}

	/**
	 * Busca restaurantes por cadena de búsqueda.
	 *
	 * @param cadenaBusqueda Palabra clave para buscar restaurantes
	 * @return Lista de restaurantes que coinciden con la búsqueda
	 */
	@GetMapping("/restaurantes/busqueda")
	public List<Restaurante> buscarRestaurante(@RequestParam String cadenaBusqueda) {
		// TODO - implementar la lógica de búsqueda por cadena
		throw new UnsupportedOperationException();
	}

	/**
	 * Marca un restaurante como favorito para un cliente.
	 *
	 * @param cliente Cliente que marca el favorito
	 * @param restaurante Restaurante a marcar como favorito
	 */
	@PostMapping("/favorito")
	public void favorito(@RequestBody Cliente cliente, @RequestBody Restaurante restaurante) {
		// TODO - implementar lógica para marcar favorito
		throw new UnsupportedOperationException();
	}

	/**
	 * Registra un nuevo cliente en el sistema.
	 *
	 * @param nombre Nombre del cliente
	 * @param apellido Apellido del cliente
	 * @param direccion Dirección del cliente
	 * @return Cliente registrado
	 */
	@PostMapping("/registrar")
	public Cliente registrarCliente(@RequestParam String nombre, @RequestParam String apellido, @RequestBody Direccion direccion) {
		// TODO - implementar lógica de registro de cliente
		throw new UnsupportedOperationException();
	}

	/**
	 * Da de alta una nueva dirección.
	 *
	 * @param calle Calle de la dirección
	 * @param numero Número de la dirección
	 * @param complemento Complemento de la dirección
	 * @param cp Código postal
	 * @param municipio Municipio de la dirección
	 * @return Nueva dirección creada
	 */
	private Direccion altaDireccion(String calle, String numero, String complemento, CodigoPostal cp, String municipio) {
		// TODO - implementar lógica para alta de dirección
		throw new UnsupportedOperationException();
	}
}
