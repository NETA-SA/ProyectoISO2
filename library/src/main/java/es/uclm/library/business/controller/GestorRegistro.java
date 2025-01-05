// GestorRegistro.java
package es.uclm.library.business.controller;

import es.uclm.library.business.service.LoginService;
import es.uclm.library.business.entity.Usuario;
import es.uclm.library.business.entity.Cliente;
import es.uclm.library.business.entity.Restaurante;
import es.uclm.library.business.entity.Repartidor;
import es.uclm.library.business.entity.Direccion;
import es.uclm.library.business.entity.CodigoPostal;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping
    public String showRegistrationForm() {
        return "Registro";
    }

    @PostMapping
    public String register(@RequestParam("role") String role,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password,
                           @RequestParam(value = "clienteNombre", required = false) String clienteNombre,
                           @RequestParam(value = "apellidos", required = false) String apellidos,
                           @RequestParam(value = "dni", required = false) String dni,
                           @RequestParam(value = "restauranteNombre", required = false) String restauranteNombre,
                           @RequestParam(value = "cif", required = false) String cif,
                           @RequestParam(value = "calle", required = false) String calle,
                           @RequestParam(value = "numero", required = false) String numero,
                           @RequestParam(value = "complemento", required = false) String complemento,
                           @RequestParam(value = "municipio", required = false) String municipio,
                           @RequestParam(value = "codigoPostal", required = false) String codigoPostalStr,
                           Model model) {
        try {
            entityManager.clear(); // Clear the EntityManager to avoid any stale data

            Usuario usuario = loginService.findUsuarioById(email);
            if (usuario == null) {
                usuario = new Usuario();
                usuario.setIdUsuario(email);
                usuario.setPass(password);
                usuario.setRol(role);
                loginService.registerUsuario(usuario);
            }

            switch (role) {
                case "cliente":
                    Cliente cliente = new Cliente(usuario, clienteNombre, apellidos, dni);
                    loginService.registerCliente(cliente);
                    break;
                case "restaurante":
                    CodigoPostal codigoPostal;
                    try {
                        codigoPostal = CodigoPostal.fromCode(codigoPostalStr);
                        if (!codigoPostal.getLocation().equalsIgnoreCase(municipio)) {
                            model.addAttribute("errorMessage", "El municipio no concuerda con el código postal.");
                            return "Registro";
                        }
                    } catch (IllegalArgumentException e) {
                        model.addAttribute("errorMessage", "Aún no hay servicio para esa localización.");
                        return "Registro";
                    }
                    Direccion direccion = new Direccion(codigoPostal, calle, numero, complemento, municipio);
                    Restaurante restaurante = new Restaurante();
                    restaurante.setUsuario(usuario);
                    restaurante.setNombre(restauranteNombre);
                    restaurante.setCif(cif);
                    restaurante.setDireccion(direccion);
                    loginService.registerRestaurante(restaurante);
                    break;
                case "repartidor":
                    Repartidor repartidor = new Repartidor();
                    repartidor.setUsuario(usuario);
                    loginService.registerRepartidor(repartidor);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid role: " + role);
            }
            model.addAttribute("successMessage", "Registro exitoso");

        } catch (Exception e) {
            logger.error("Registration error", e);
            model.addAttribute("errorMessage", "Registration failed");
        }
        return "Registro";
    }
}