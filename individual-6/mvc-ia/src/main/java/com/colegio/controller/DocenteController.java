package com.colegio.controller;

import com.colegio.dto.DocenteDTO;
import com.colegio.service.DocenteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

/**
 * Controlador MVC para la consulta y visualización del cuerpo docente.
 */
@Controller
@RequestMapping("/docentes")
public class DocenteController {

    private final DocenteService docenteService;

    public DocenteController(DocenteService docenteService) {
        this.docenteService = docenteService;
    }

    @GetMapping
    public String listarDocentes(Model model) {
        List<DocenteDTO> docentes = docenteService.listarTodos();
        model.addAttribute("docentes", docentes);
        return "docentes/index";
    }

    @GetMapping("/{id}")
    public String verDetalle(@PathVariable("id") UUID id, Model model) {
        DocenteDTO docente = docenteService.buscarPorId(id);
        if (docente == null) {
            return "redirect:/docentes";
        }
        model.addAttribute("docente", docente);
        return "docentes/detalle";
    }
}

