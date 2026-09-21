package com.clubdeportivo.controller;

import com.clubdeportivo.dto.request.RegistroAccesoRequestDTO;
import com.clubdeportivo.dto.response.RegistroAccesoResponseDTO;
import com.clubdeportivo.entity.PuntoDeAcceso;
import com.clubdeportivo.entity.enums.TipoMovimiento;
import com.clubdeportivo.service.ControlAccesoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * =========================================================================================
 * CONTROLADOR: ControlAccesoController
 * =========================================================================================
 * Consola operativa para guardias y recepcionistas en los molinetes del club:
 * - Registra entradas y salidas mediante DNI / Carnet con validación de rostro y cuotas.
 * - Monitorea en tiempo real el paso de socios, familiares adherentes y personal.
 */
@Controller
@RequestMapping("/accesos")
public class ControlAccesoController {

    private final ControlAccesoService controlAccesoService;

    public ControlAccesoController(ControlAccesoService controlAccesoService) {
        this.controlAccesoService = controlAccesoService;
    }

    @GetMapping
    public String consolaAcceso(Model model) {
        RegistroAccesoRequestDTO requestDTO = new RegistroAccesoRequestDTO();
        List<PuntoDeAcceso> puntos = controlAccesoService.obtenerPuntosDeAcceso();
        List<RegistroAccesoResponseDTO> ultimos = controlAccesoService.obtenerUltimosAccesos();

        model.addAttribute("accesoDTO", requestDTO);
        model.addAttribute("puntosAcceso", puntos);
        model.addAttribute("ultimosAccesos", ultimos);
        model.addAttribute("tiposMovimiento", TipoMovimiento.values());

        return "accesos/consola";
    }

    @PostMapping("/registrar")
    public String registrarPaso(@Valid @ModelAttribute("accesoDTO") RegistroAccesoRequestDTO dto,
                                BindingResult bindingResult,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("puntosAcceso", controlAccesoService.obtenerPuntosDeAcceso());
            model.addAttribute("ultimosAccesos", controlAccesoService.obtenerUltimosAccesos());
            model.addAttribute("tiposMovimiento", TipoMovimiento.values());
            return "accesos/consola";
        }

        try {
            RegistroAccesoResponseDTO resultado = controlAccesoService.registrarMovimiento(dto);
            if (resultado.isHabilitado()) {
                redirectAttributes.addFlashAttribute("successMessage",
                        "PASO HABILITADO (" + resultado.getTipoMovimiento() + "): " + resultado.getPersonaNombreCompleto() +
                        " [" + resultado.getTipoPersona() + "] - Molinete: " + resultado.getPuntoDeAccesoNombre());
            } else {
                redirectAttributes.addFlashAttribute("warningMessage",
                        "ACCESO REGISTRADO CON OBSERVACIÓN: " + resultado.getPersonaNombreCompleto() +
                        ". Motivo: " + resultado.getMotivoBloqueo());
            }
            return "redirect:/accesos";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error en el control de acceso: " + ex.getMessage());
            return "redirect:/accesos";
        }
    }

    @GetMapping("/historial")
    public String historialAccesos(Model model) {
        List<RegistroAccesoResponseDTO> accesos = controlAccesoService.obtenerUltimosAccesos();
        model.addAttribute("accesos", accesos);
        return "accesos/historial";
    }
}
