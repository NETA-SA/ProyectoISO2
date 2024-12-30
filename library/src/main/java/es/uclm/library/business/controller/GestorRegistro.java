package es.uclm.library.business.controller;

import es.uclm.library.business.service.LoginService;
import es.uclm.library.business.entity.Usuario;
import es.uclm.library.business.entity.Restaurante;
import es.uclm.library.business.entity.Repartidor;
import es.uclm.library.business.entity.Direccion;
import es.uclm.library.business.entity.CodigoPostal;
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
        return "Registro";
    }

    @PostMapping
    public String register(@RequestParam("role") String role,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password,
                           @RequestParam(value = "nombre", required = false) String nombre,
                           @RequestParam(value = "cif", required = false) String cif,
                           @RequestParam(value = "calle", required = false) String calle,
                           @RequestParam(value = "numero", required = false) String numero,
                           @RequestParam(value = "complemento", required = false) String complemento,
                           @RequestParam(value = "municipio", required = false) String municipio,
                           @RequestParam(value = "codigoPostal", required = false) String codigoPostalStr,
                           Model model) {
        try {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(email);
            usuario.setPass(password);
            usuario.setRol(role);

            loginService.registerUsuario(usuario);

            switch (role) {
                case "restaurante":
                    CodigoPostal codigoPostal = CodigoPostal.fromCode(codigoPostalStr);
                    Direccion direccion = new Direccion(codigoPostal, calle, numero, complemento, municipio);
                    Restaurante restaurante = new Restaurante();
                    restaurante.setUsuario(usuario);
                    restaurante.setNombre(nombre);
                    restaurante.setCif(cif);
                    restaurante.setDireccion(direccion);

                    loginService.registerRestaurante(restaurante);
                    break;
                case "repartidor":
                    Repartidor repartidor = new Repartidor();
                    repartidor.setUsuario(usuario);
                    loginService.registerRepartidor(repartidor);
                    break;
                case "usuario":
                    // User is already saved
                    break;
                default:
                    throw new IllegalArgumentException("Invalid role: " + role);
            }
            model.addAttribute("message", "Registration successful");

        } catch (Exception e) {
            logger.error("Registration error", e);
            model.addAttribute("message", "Registration failed");
        }
        return "redirect:/";
    }
}