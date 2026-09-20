package com.techstore.controller;

import com.techstore.dto.CategoriaDTO;
import com.techstore.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * =============================================================================
 * CAPA CONTROLADOR: CategoriaController
 * =============================================================================
 * Administra las categorías que agrupan productos tecnológicos (Agregación UML).
 */
@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("categorias", categoriaService.listarTodas());
        return "categorias/list";
    }

    @GetMapping("/nueva")
    public String nueva(Model model) {
        model.addAttribute("categoria", new CategoriaDTO());
        model.addAttribute("tituloForm", "Nueva Categoría de Productos");
        return "categorias/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        return categoriaService.buscarPorId(id)
                .map(dto -> {
                    model.addAttribute("categoria", dto);
                    model.addAttribute("tituloForm", "Editar Categoría: " + dto.getNombre());
                    return "categorias/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("mensajeError", "Categoría no encontrada.");
                    return "redirect:/categorias";
                });
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("categoria") CategoriaDTO dto,
                          BindingResult bindingResult,
                          Model model,
                          RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("tituloForm", (dto.getId() == null ? "Nueva " : "Editar ") + "Categoría");
            return "categorias/form";
        }

        if (dto.getId() == null && categoriaService.existeCodigo(dto.getCodigo())) {
            bindingResult.rejectValue("codigo", "duplicate", "El código ya existe.");
            model.addAttribute("tituloForm", "Nueva Categoría");
            return "categorias/form";
        }

        categoriaService.guardar(dto);
        redirectAttributes.addFlashAttribute("mensajeExito", "Categoría guardada con éxito.");
        return "redirect:/categorias";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            categoriaService.eliminar(id);
            redirectAttributes.addFlashAttribute("mensajeExito", "Categoría eliminada.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "No se puede eliminar la categoría porque tiene productos vinculados.");
        }
        return "redirect:/categorias";
    }
}

