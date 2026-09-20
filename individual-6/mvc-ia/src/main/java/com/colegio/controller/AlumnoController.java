package com.colegio.controller;

import com.colegio.dto.AlumnoDTO;
import com.colegio.model.Sexo;
import com.colegio.service.AlumnoService;
import com.colegio.service.AulaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador MVC para la gestión de Alumnos (Estudiantes).
 */
@Controller
@RequestMapping("/alumnos")
public class AlumnoController {

    private final AlumnoService alumnoService;
    private final AulaService aulaService;

    public AlumnoController(AlumnoService alumnoService, AulaService aulaService) {
        this.alumnoService = alumnoService;
        this.aulaService = aulaService;
    }

    @GetMapping
    public String listarAlumnos(Model model) {
        model.addAttribute("alumnos", alumnoService.listarTodos());
        return "alumnos/index";
    }

    @GetMapping("/nuevo")
    public String nuevoAlumnoForm(Model model) {
        if (!model.containsAttribute("alumno")) {
            model.addAttribute("alumno", new AlumnoDTO());
        }
        model.addAttribute("sexos", Sexo.values());
        model.addAttribute("aulas", aulaService.listarTodas());
        return "alumnos/form";
    }

    @PostMapping("/guardar")
    public String guardarAlumno(@Valid @ModelAttribute("alumno") AlumnoDTO dto,
                                BindingResult bindingResult,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("sexos", Sexo.values());
            model.addAttribute("aulas", aulaService.listarTodas());
            return "alumnos/form";
        }

        try {
            alumnoService.registrarAlumno(dto);
            redirectAttributes.addFlashAttribute("successMsg", "Alumno matriculado correctamente.");
            return "redirect:/alumnos";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMsg", e.getMessage());
            model.addAttribute("sexos", Sexo.values());
            model.addAttribute("aulas", aulaService.listarTodas());
            return "alumnos/form";
        }
    }
}

