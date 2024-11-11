package es.uclm.library.business.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class GestorLogin {

	/**
	 * Muestra el formulario de inicio de sesión.
	 *
	 * @return Nombre de la plantilla Thymeleaf para el formulario de inicio de sesión
	 */
	@GetMapping
	public String mostrarFormularioLogin() {
		return "login"; // Nombre de la plantilla Thymeleaf (html)
	}

	/**
	 * Procesa el inicio de sesión de un usuario.
	 *
	 * @param id    Identificador del usuario
	 * @param pass  Contraseña del usuario
	 * @param model Model para pasar datos a la vista
	 * @return Redirección a una página de inicio o mensaje de error
	 */
	@PostMapping
	public String login(@RequestParam String id, @RequestParam String pass, Model model) {
		// TODO - implementar la lógica de autenticación
		boolean autenticado = false; // Simulación de autenticación; reemplaza con lógica real

		if (autenticado) {
			return "redirect:/home"; // Redirige a la página de inicio si la autenticación es exitosa
		} else {
			model.addAttribute("error", "Credenciales incorrectas");
			return "login"; // Vuelve al formulario de inicio de sesión si falla la autenticación
		}
	}
}
