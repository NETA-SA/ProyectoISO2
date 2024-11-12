package es.uclm.library.business.controller;

import es.uclm.library.business.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import es.uclm.library.business.entity.Login;

@Controller
@RequestMapping("/login")
public class GestorLogin {

	private static final Logger logger = LoggerFactory.getLogger(GestorLogin.class);

	@Autowired
	private LoginService loginService;

	// Metodo para mostrar el formulario de login 
	@GetMapping
	public String showLoginForm(Model model){
		model.addAttribute("login", new Login());
		return "login"; // Apunta al formulario de login de interfaces
	}

	// Metodo para procesar el formulario login
	@PostMapping
	public String processLogin(
            @RequestParam("idUsuario") String idUsuario,
            @RequestParam("pass") String pass,
            Model model) {

        // Llama al servicio para autenticar el usuario
        if (loginService.authenticate(idUsuario, pass)) {
            logger.info("Inicio de sesión exitoso para el usuario: " + idUsuario);
            model.addAttribute("mensaje", "Inicio de sesión exitoso");
            return "redirect:/bienvenida"; // Redirige a una página de bienvenida o de inicio
        } else {
            logger.warn("Inicio de sesión fallido para el usuario: " + idUsuario);
            model.addAttribute("error", "Credenciales incorrectas, inténtalo de nuevo");
            return "redirect:/login"; // Vuelve a mostrar el formulario de login con un mensaje de error
        }
    }
}
