package es.uclm.library.business.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GestorBienvenida {

    private static final Logger logger = LoggerFactory.getLogger(GestorBienvenida.class);
    
    @GetMapping("/")
    public String bienvenida(Model model) {
        logger.info("Handling welcome request");
	model.addAttribute("mensaje", "¡Bienvenido a la aplicación!");
        return "Bienvenida"; // El nombre de la plantilla sin la extensión .html
    }
}
