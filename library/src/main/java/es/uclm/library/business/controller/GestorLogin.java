package es.uclm.library.business.controller;

import es.uclm.library.business.service.LoginService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import es.uclm.library.business.entity.Login;
import es.uclm.library.business.entity.Usuario;
import es.uclm.library.business.entity.Cliente;


@Controller
@RequestMapping("/login")
public class GestorLogin {

	private static final Logger logger = LoggerFactory.getLogger(GestorLogin.class);

	@Autowired
	private LoginService loginService;

	// Metodo para mostrar el formulario de login 
	@GetMapping
	public String showLoginForm(Model model){
		//model.addAttribute("usuario", new Usuario());
		//return "login"; // Apunta al formulario de login de interfaces
		Usuario usuario = new Usuario();
        	model.addAttribute("usuario", usuario);
        	return "login"; // nombre de la plantilla
	}

	// Metodo para procesar el formulario login
	// GestorLogin.java
	@PostMapping
	public String processLogin(
			@RequestParam("idUsuario") String idUsuario,
			@RequestParam("pass") String pass,
			HttpSession session,
			Model model) {

		if (loginService.authenticate(idUsuario, pass)) {
			logger.info("Inicio de sesion exitoso para el usuario: " + idUsuario);
			Usuario usuario = loginService.findUsuarioById(idUsuario);
			session.setAttribute("email", idUsuario);
			if ("cliente".equals(usuario.getRol())) {
				return "redirect:/login/BienvenidaUsuario";
			}else {
				if ("restaurante".equals(usuario.getRol())) {
					logger.info("inicio como restaurante exitoso");
					return "redirect:/restaurantes/RestaurantesPag";
				}else {
					if ("repartidor".equals(usuario.getRol())) {
						return "redirect:/Repartos";
					}else {
						return "redirect:/";
					}
				}
			}
		} else {
			logger.warn("Inicio de sesion fallido para el usuario: " + idUsuario);
			model.addAttribute("error", "Credenciales incorrectas, intentalo de nuevo");
			return "redirect:/login"; // Vuelve a mostrar el formulario de login con un mensaje de error
		}
	}

	@GetMapping("/BienvenidaUsuario")
	public String bienvenidaUsuario(HttpSession session, Model model) {
		String email = (String) session.getAttribute("email");
		Usuario usuario = loginService.findUsuarioById(email);
		Cliente cliente = loginService.findClienteByUsuario(usuario);

		model.addAttribute("nombreUsuario", cliente.getNombre());
		return "BienvenidaUsuario";
	}
}
