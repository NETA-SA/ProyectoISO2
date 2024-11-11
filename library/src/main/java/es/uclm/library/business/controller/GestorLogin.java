package es.uclm.library.business.controller;

import es.uclm.library.business.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class GestorLogin {

	private static final Logger logger = LoggerFactory.getLogger(GestorLogin.class);

	@Autowired
	private LoginService loginService;

	/**
	 * Muestra el formulario de inicio de sesión.
	 *
	 * @return Nombre de la plantilla Thymeleaf para el formulario de inicio de sesión
	 */
	@GetMapping
	public String mostrarFormularioLogin() {
		logger.info("Mostrando el formulario de inicio de sesión.");
		return "login"; // Nombre de la plantilla Thymeleaf para el formulario de inicio de sesión
	}

	/**
	 * Maneja el proceso de inicio de sesión de un usuario.
	 *
	 * @param id   Identificador del usuario
	 * @param pass Contraseña del usuario
	 * @param model Model para pasar datos a la vista
	 * @return Vista de redirección o de error en caso de fallo en la autenticación
	 */
	@PostMapping
	public String login(@RequestParam String id, @RequestParam String pass, Model model) {
		logger.info("Intentando iniciar sesión con ID: {}", id);

		boolean autenticado = loginService.autenticar(id, pass); // Llama a LoginService para manejar la autenticación

		if (autenticado) {
			logger.info("Inicio de sesión exitoso para el usuario: {}", id);
			return "redirect:/home"; // Redirección a la página principal o de bienvenida
		} else {
			logger.warn("Falló el inicio de sesión para el usuario: {}", id);
			model.addAttribute("error", "Credenciales incorrectas. Inténtalo de nuevo.");
			return "login"; // Nombre de la plantilla Thymeleaf en caso de error de autenticación
		}
	}
}
