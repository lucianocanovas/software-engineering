package com.clubdeportivo.controller;

import com.clubdeportivo.dto.request.CuotaEmisionRequestDTO;
import com.clubdeportivo.dto.response.CuotaResponseDTO;
import com.clubdeportivo.dto.response.SocioResponseDTO;
import com.clubdeportivo.service.CuotaService;
import com.clubdeportivo.service.SocioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * =========================================================================================
 * CONTROLADOR: CuotaController
 * =========================================================================================
 * Supervisa la emisión de aranceles sociales mensuales y la consulta de estados de deuda.
 */
@Controller
@RequestMapping("/cuotas")
public class CuotaController {

    private final CuotaService cuotaService;
    private final SocioService socioService;

    public CuotaController(CuotaService cuotaService, SocioService socioService) {
        this.cuotaService = cuotaService;
        this.socioService = socioService;
    }

    @GetMapping
    public String listarCuotas(Model model) {
        cuotaService.actualizarEstadosVencimiento();
        List<CuotaResponseDTO> cuotas = cuotaService.obtenerTodasLasCuotas();
        model.addAttribute("cuotas", cuotas);
        return "cuotas/lista";
    }

    @GetMapping("/nueva")
    public String formularioNuevaCuota(@RequestParam(value = "socioId", required = false) Long socioId, Model model) {
        CuotaEmisionRequestDTO dto = new CuotaEmisionRequestDTO();
        if (socioId != null) {
            dto.setSocioTitularId(socioId);
        }
        // Sugiere el mes actual en formato YYYY-MM
        dto.setPeriodo(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM")));
        dto.setMontoBase(new BigDecimal("15000.00")); // Valor base sugerido
        dto.setMontoRecargo(new BigDecimal("2500.00")); // Recargo por mora sugerido
        dto.setFechaVencimiento(LocalDate.now().plusDays(10));

        List<SocioResponseDTO> socios = socioService.obtenerTodosLosSocios();
        model.addAttribute("cuotaDTO", dto);
        model.addAttribute("socios", socios);
        return "cuotas/formulario";
    }

    @PostMapping("/emitir")
    public String emitirCuota(@Valid @ModelAttribute("cuotaDTO") CuotaEmisionRequestDTO dto,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("socios", socioService.obtenerTodosLosSocios());
            return "cuotas/formulario";
        }

        try {
            cuotaService.emitirCuota(dto);
            redirectAttributes.addFlashAttribute("successMessage", "Cuota emitida exitosamente para el periodo " + dto.getPeriodo() + ".");
            return "redirect:/cuotas";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("socios", socioService.obtenerTodosLosSocios());
            return "cuotas/formulario";
        }
    }
}
