package com.colegio.controller;

import com.colegio.dto.DocenteDTO;
import com.colegio.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador principal del Dashboard administrativo del colegio.
 */
@Controller
public class DashboardController {

    private final DocenteService docenteService;
    private final AlumnoService alumnoService;
    private final MateriaService materiaService;
    private final EvaluacionService evaluacionService;
    private final NotaService notaService;

    public DashboardController(DocenteService docenteService,
                               AlumnoService alumnoService,
                               MateriaService materiaService,
                               EvaluacionService evaluacionService,
                               NotaService notaService) {
        this.docenteService = docenteService;
        this.alumnoService = alumnoService;
        this.materiaService = materiaService;
        this.evaluacionService = evaluacionService;
        this.notaService = notaService;
    }

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model, Authentication authentication) {
        String email = authentication != null ? authentication.getName() : null;
        DocenteDTO docenteActual = email != null ? docenteService.buscarPorEmail(email) : null;

        model.addAttribute("usuarioActual", docenteActual);
        model.addAttribute("totalAlumnos", alumnoService.contarAlumnos());
        model.addAttribute("totalMaterias", materiaService.contarMaterias());
        model.addAttribute("totalEvaluaciones", evaluacionService.contarEvaluaciones());
        model.addAttribute("totalNotas", notaService.contarNotas());
        model.addAttribute("promedioGeneral", notaService.obtenerPromedioGeneral());

        model.addAttribute("alumnosRecientes", alumnoService.listarTodos());
        model.addAttribute("ultimasNotas", notaService.listarTodas());

        return "dashboard/index";
    }
}

