package com.techstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * =============================================================================
 * CAPA CONTROLADOR: DocsController
 * =============================================================================
 * Presenta de manera interactiva la documentación técnica del sistema:
 * - Diagrama de Clases UML con explicación de las 4 relaciones fundamentales:
 *   Herencia, Agregación, Composición y Asociación.
 * - Diagrama de Secuencia del ABM de Productos (Alta, Modificación, Baja, Consulta).
 * - Arquitectura MVC con Thymeleaf, SQLite y DTOs.
 */
@Controller
@RequestMapping("/docs")
public class DocsController {

    @GetMapping("/diagramas")
    public String diagramas(Model model) {
        return "docs/diagramas";
    }
}

