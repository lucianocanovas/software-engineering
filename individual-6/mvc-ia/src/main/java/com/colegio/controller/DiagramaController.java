package com.colegio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controlador para la visualización del Diagrama de Clases UML y la documentación del Ejercicio "a".
 */
@Controller
@RequestMapping("/docs")
public class DiagramaController {

    @GetMapping("/diagrama")
    public String verDiagramaUml(Model model) {
        return "docs/diagrama";
    }
}

