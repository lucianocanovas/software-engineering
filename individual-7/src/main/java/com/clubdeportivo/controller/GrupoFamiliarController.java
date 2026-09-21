package com.clubdeportivo.controller;

import com.clubdeportivo.dto.request.FamiliarAdherenteRequestDTO;
import com.clubdeportivo.dto.response.CuotaResponseDTO;
import com.clubdeportivo.dto.response.GrupoFamiliarResponseDTO;
import com.clubdeportivo.entity.enums.Parentesco;
import com.clubdeportivo.service.CuotaService;
import com.clubdeportivo.service.FamiliarAdherenteService;
import com.clubdeportivo.service.GrupoFamiliarService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * =========================================================================================
 * CONTROLADOR: GrupoFamiliarController
 * =========================================================================================
 * Administra los grupos familiares suscritos y la agregación de familiares adherentes.
 */
@Controller
@RequestMapping("/familias")
public class GrupoFamiliarController {

    private final GrupoFamiliarService grupoFamiliarService;
    private final FamiliarAdherenteService familiarAdherenteService;
    private final CuotaService cuotaService;

    public GrupoFamiliarController(GrupoFamiliarService grupoFamiliarService,
                                   FamiliarAdherenteService familiarAdherenteService,
                                   CuotaService cuotaService) {
        this.grupoFamiliarService = grupoFamiliarService;
        this.familiarAdherenteService = familiarAdherenteService;
        this.cuotaService = cuotaService;
    }

    @GetMapping
    public String listarGruposFamiliares(Model model) {
        List<GrupoFamiliarResponseDTO> grupos = grupoFamiliarService.obtenerTodosLosGrupos();
        model.addAttribute("grupos", grupos);
        return "familias/lista";
    }

    @GetMapping("/{id}")
    public String verDetalleGrupo(@PathVariable("id") Long id, Model model) {
        GrupoFamiliarResponseDTO grupo = grupoFamiliarService.obtenerGrupoPorId(id);
        List<CuotaResponseDTO> cuotas = cuotaService.obtenerCuotasPorGrupoFamiliar(id);

        model.addAttribute("grupo", grupo);
        model.addAttribute("cuotas", cuotas);

        FamiliarAdherenteRequestDTO nuevoAdherente = new FamiliarAdherenteRequestDTO();
        nuevoAdherente.setSocioTitularId(grupo.getTitularId());
        model.addAttribute("adherenteDTO", nuevoAdherente);
        model.addAttribute("parentescos", Parentesco.values());

        return "familias/detalle";
    }

    @PostMapping("/adherente/guardar")
    public String guardarAdherente(@Valid @ModelAttribute("adherenteDTO") FamiliarAdherenteRequestDTO dto,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Datos incompletos o inválidos al registrar el familiar adherente.");
            return "redirect:/socios/" + dto.getSocioTitularId();
        }

        try {
            familiarAdherenteService.agregarAdherente(dto);
            redirectAttributes.addFlashAttribute("successMessage", "Familiar adherente incorporado exitosamente al grupo.");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }

        return "redirect:/socios/" + dto.getSocioTitularId();
    }

    @PostMapping("/adherente/eliminar/{id}")
    public String eliminarAdherente(@PathVariable("id") Long id,
                                   @RequestParam("socioId") Long socioId,
                                   RedirectAttributes redirectAttributes) {
        try {
            familiarAdherenteService.eliminarAdherente(id);
            redirectAttributes.addFlashAttribute("infoMessage", "Familiar adherente desvinculado del grupo familiar.");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/socios/" + socioId;
    }
}
