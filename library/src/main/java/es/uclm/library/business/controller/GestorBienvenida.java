<<<<<<< HEAD
package es.uclm.library.business.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
=======
package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
>>>>>>> feature-Login
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GestorBienvenida {
<<<<<<< HEAD

    private static final Logger logger = LoggerFactory.getLogger(GestorBienvenida.class);

    @GetMapping("/bienvenida")
    public String welcome() {
        logger.info("Handling welcome request");
        return "bienvenida";
    }
}
=======
    
    @GetMapping("/bienvenida")
    public String bienvenida(Model model) {
        // Agregamos un mensaje para que lo muestre en la plantilla
        model.addAttribute("mensaje", "¡Bienvenido a la aplicación!");
        return "bienvenida"; // El nombre de la plantilla sin la extensión .html
    }
}
>>>>>>> feature-Login
