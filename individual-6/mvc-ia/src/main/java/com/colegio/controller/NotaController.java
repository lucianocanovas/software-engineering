package com.colegio.controller;

import com.colegio.dto.EvaluacionDTO;
import com.colegio.dto.NotaDTO;
import com.colegio.service.*;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador MVC para la administración de Evaluaciones y Calificaciones (Notas).
 */
@Controller
public class NotaController {

    private final NotaService notaService;
    private final EvaluacionService evaluacionService;
    private final AlumnoService alumnoService;
    private final MateriaService materiaService;
    private final DocenteService docenteService;

    public NotaController(NotaService notaService,
                          EvaluacionService evaluacionService,
                          AlumnoService alumnoService,
                          MateriaService materiaService,
                          DocenteService docenteService) {
        this.notaService = notaService;
        this.evaluacionService = evaluacionService;
        this.alumnoService = alumnoService;
        this.materiaService = materiaService;
        this.docenteService = docenteService;
    }

    @GetMapping("/notas")
    public String listarNotas(Model model) {
        model.addAttribute("notas", notaService.listarTodas());
        model.addAttribute("evaluaciones", evaluacionService.listarTodas());
        model.addAttribute("promedioGeneral", notaService.obtenerPromedioGeneral());
        return "notas/index";
    }

    @GetMapping("/notas/nueva")
    public String nuevaNotaForm(Model model) {
        if (!model.containsAttribute("nota")) {
            model.addAttribute("nota", new NotaDTO());
        }
        model.addAttribute("evaluaciones", evaluacionService.listarTodas());
        model.addAttribute("alumnos", alumnoService.listarTodos());
        return "notas/form";
    }

    @PostMapping("/notas/guardar")
    public String guardarNota(@Valid @ModelAttribute("nota") NotaDTO dto,
                              BindingResult bindingResult,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("evaluaciones", evaluacionService.listarTodas());
            model.addAttribute("alumnos", alumnoService.listarTodos());
            return "notas/form";
        }

        try {
            notaService.registrarNota(dto);
            redirectAttributes.addFlashAttribute("successMsg", "Calificación asentada con éxito.");
            return "redirect:/notas";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMsg", e.getMessage());
            model.addAttribute("evaluaciones", evaluacionService.listarTodas());
            model.addAttribute("alumnos", alumnoService.listarTodos());
            return "notas/form";
        }
    }

    @GetMapping("/evaluaciones/nueva")
    public String nuevaEvaluacionForm(Model model) {
        if (!model.containsAttribute("evaluacion")) {
            model.addAttribute("evaluacion", new EvaluacionDTO());
        }
        model.addAttribute("materias", materiaService.listarTodas());
        model.addAttribute("docentes", docenteService.listarTodos());
        return "notas/evaluacion-form";
    }

    @PostMapping("/evaluaciones/guardar")
    public String guardarEvaluacion(@Valid @ModelAttribute("evaluacion") EvaluacionDTO dto,
                                    BindingResult bindingResult,
                                    Model model,
                                    Authentication authentication,
                                    RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("materias", materiaService.listarTodas());
            model.addAttribute("docentes", docenteService.listarTodos());
            return "notas/evaluacion-form";
        }

        try {
            // Si no se asignó docente explícito, usar el docente autenticado
            if (dto.getProfesorId() == null && authentication != null) {
                var docente = docenteService.buscarPorEmail(authentication.getName());
                if (docente != null) {
                    dto.setProfesorId(docente.getId());
                }
            }

            evaluacionService.crearEvaluacion(dto);
            redirectAttributes.addFlashAttribute("successMsg", "Evaluación académica planificada con éxito.");
            return "redirect:/notas";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMsg", e.getMessage());
            model.addAttribute("materias", materiaService.listarTodas());
            model.addAttribute("docentes", docenteService.listarTodos());
            return "notas/evaluacion-form";
        }
    }
}

