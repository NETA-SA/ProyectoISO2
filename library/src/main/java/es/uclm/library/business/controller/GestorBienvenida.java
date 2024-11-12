package es.uclm.library.business.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GestorBienvenida {

    private static final Logger logger = LoggerFactory.getLogger(GestorBienvenida.class);

    @GetMapping("/")
    public String welcome() {
        logger.info("Handling welcome request");
        return "Bienvenida";
    }
}