package es.uclm.library.business.controller;

import es.uclm.library.business.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import es.uclm.library.business.entity.Login;
import es.uclm.library.business.entity.Usuario;
import jakarta.servlet.http.HttpSession;


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
	@PostMapping
	public String processLogin(
            @RequestParam("idUsuario") String idUsuario,
            @RequestParam("pass") String pass,
            Model model,
			HttpSession session) { // Inyectamos la sesión, HAY QUE RECUPERARLA

		if (loginService.authenticate(idUsuario, pass)) {
			session.setAttribute("idUsuario", idUsuario); // Guardas el idUsuario en la sesión
			Usuario usuario = loginService.obtenerUsuarioPorId(idUsuario);
			logger.info("Inicio de sesión exitoso para el usuario: " + idUsuario);
			model.addAttribute("mensaje", "Inicio de sesion exitoso");

			if ("restaurante".equals(usuario.getRol())) {
				return "redirect:/restaurantes/RestaurantesPag";
			} else {
				return "redirect:/";
			}
		} else {
			logger.warn("Inicio de sesion fallido para el usuario: " + idUsuario);
			model.addAttribute("error", "Credenciales incorrectas, inténtalo de nuevo");
			return "redirect:/login";
		}
    }
}
