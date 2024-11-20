package es.uclm.library.business.controller;

import es.uclm.library.business.service.LoginService;
import es.uclm.library.business.entity.Usuario;
import es.uclm.library.business.entity.Restaurante;
import es.uclm.library.business.entity.Repartidor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/Registro")
public class GestorRegistro {

    private static final Logger logger = LoggerFactory.getLogger(GestorRegistro.class);

    @Autowired
    private LoginService loginService;

    @GetMapping
    public String showRegistrationForm() {
        return "Registro"; // Asegúrate de que este archivo HTML exista en src/main/resources/templates
    }

    @PostMapping
    public String register(@RequestParam("role") String role,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password,
                           Model model) {
        try {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(email); // Assuming email is used as the user ID
            usuario.setPass(password);
            usuario.setRol(role);

            switch (role) {
                case "restaurante":
                    Restaurante restaurante = new Restaurante();
                    restaurante.setUsuario(usuario);
                    loginService.registerRestaurante(restaurante);
                    break;
                case "repartidor":
                    Repartidor repartidor = new Repartidor();
                    repartidor.setUsuario(usuario);
                    loginService.registerRepartidor(repartidor);
                    break;
                case "usuario":
                    loginService.registerUsuario(usuario);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid role: " + role);
            }
            model.addAttribute("message", "Registration successful");
        } catch (Exception e) {
            logger.error("Registration error", e);
            model.addAttribute("message", "Registration failed");
        }
        return "Registro";
    }
}