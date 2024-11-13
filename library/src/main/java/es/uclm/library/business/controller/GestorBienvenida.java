package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GestorBienvenida {
    
    @GetMapping("/bienvenida")
    public String bienvenida(Model model) {
        // Agregamos un mensaje para que lo muestre en la plantilla
        model.addAttribute("mensaje", "¡Bienvenido a la aplicación!");
        return "bienvenida"; // El nombre de la plantilla sin la extensión .html
    }
}
