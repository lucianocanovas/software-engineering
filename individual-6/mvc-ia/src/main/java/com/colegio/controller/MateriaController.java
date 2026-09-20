package com.colegio.controller;

import com.colegio.dto.MateriaDTO;
import com.colegio.service.AulaService;
import com.colegio.service.MateriaService;
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
 * Controlador MVC para el catálogo de Materias curriculares.
 */
@Controller
@RequestMapping("/materias")
public class MateriaController {

    private final MateriaService materiaService;
    private final AulaService aulaService;

    public MateriaController(MateriaService materiaService, AulaService aulaService) {
        this.materiaService = materiaService;
        this.aulaService = aulaService;
    }

    @GetMapping
    public String listarMaterias(Model model) {
        model.addAttribute("materias", materiaService.listarTodas());
        return "materias/index";
    }

    @GetMapping("/nueva")
    public String nuevaMateriaForm(Model model) {
        if (!model.containsAttribute("materia")) {
            model.addAttribute("materia", new MateriaDTO());
        }
        model.addAttribute("grados", aulaService.listarGrados());
        return "materias/form";
    }

    @PostMapping("/guardar")
    public String guardarMateria(@Valid @ModelAttribute("materia") MateriaDTO dto,
                                 BindingResult bindingResult,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("grados", aulaService.listarGrados());
            return "materias/form";
        }

        try {
            materiaService.registrarMateria(dto);
            redirectAttributes.addFlashAttribute("successMsg", "Materia creada correctamente.");
            return "redirect:/materias";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMsg", e.getMessage());
            model.addAttribute("grados", aulaService.listarGrados());
            return "materias/form";
        }
    }
}

