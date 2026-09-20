package com.techstore.controller;

import com.techstore.dto.ProveedorDTO;
import com.techstore.service.ProveedorService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * =============================================================================
 * CAPA CONTROLADOR: ProveedorController
 * =============================================================================
 * Administra proveedores mayoristas (Asociación UML con OrdenDeCompra).
 */
@Controller
@RequestMapping("/proveedores")
public class ProveedorController {

    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("proveedores", proveedorService.listarTodos());
        return "proveedores/list";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("proveedor", new ProveedorDTO());
        model.addAttribute("tituloForm", "Nuevo Proveedor Mayorista");
        return "proveedores/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        return proveedorService.buscarPorId(id)
                .map(dto -> {
                    model.addAttribute("proveedor", dto);
                    model.addAttribute("tituloForm", "Editar Proveedor: " + dto.getRazonSocial());
                    return "proveedores/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("mensajeError", "Proveedor no encontrado.");
                    return "redirect:/proveedores";
                });
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("proveedor") ProveedorDTO dto,
                          BindingResult bindingResult,
                          Model model,
                          RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("tituloForm", (dto.getId() == null ? "Nuevo " : "Editar ") + "Proveedor");
            return "proveedores/form";
        }

        if (dto.getId() == null && proveedorService.existeCodigo(dto.getCodigo())) {
            bindingResult.rejectValue("codigo", "duplicate", "El código de proveedor ya existe.");
            model.addAttribute("tituloForm", "Nuevo Proveedor");
            return "proveedores/form";
        }

        if (dto.getId() == null && proveedorService.existeCuit(dto.getCuit())) {
            bindingResult.rejectValue("cuit", "duplicate", "El CUIT ya se encuentra registrado.");
            model.addAttribute("tituloForm", "Nuevo Proveedor");
            return "proveedores/form";
        }

        proveedorService.guardar(dto);
        redirectAttributes.addFlashAttribute("mensajeExito", "Proveedor registrado correctamente.");
        return "redirect:/proveedores";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        proveedorService.darDeBaja(id);
        redirectAttributes.addFlashAttribute("mensajeExito", "Proveedor dado de baja.");
        return "redirect:/proveedores";
    }
}

