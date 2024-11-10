package es.uclm.library.business.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class GestorLogin {

	/**
	 * Maneja el inicio de sesión de un usuario.
	 *
	 * @param id   Identificador del usuario
	 * @param pass Contraseña del usuario
	 */
	@PostMapping
	public void login(@RequestParam String id, @RequestParam String pass) {
		// TODO - implementar la lógica de autenticación
		throw new UnsupportedOperationException();
	}
}
