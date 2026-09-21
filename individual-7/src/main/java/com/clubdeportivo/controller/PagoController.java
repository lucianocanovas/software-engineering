package com.clubdeportivo.controller;

import com.clubdeportivo.dto.request.PagoRequestDTO;
import com.clubdeportivo.dto.response.CuotaResponseDTO;
import com.clubdeportivo.dto.response.PagoResponseDTO;
import com.clubdeportivo.entity.enums.TipoMedioPago;
import com.clubdeportivo.service.CuotaService;
import com.clubdeportivo.service.PagoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * =========================================================================================
 * CONTROLADOR: PagoController (Módulo de Cobranzas y Medios de Pago)
 * =========================================================================================
 * Atiende las operaciones del requerimiento clave del ejercicio:
 * "Registrar el pago de la cuota del club para cada familia, donde el pago se puede realizar
 * con distintos medios de pago (Efectivo, Transferencia, Mercado Pago)".
 *
 * Flujo:
 * 1. El operador o socio selecciona una cuota impaga.
 * 2. Se presenta el formulario interactivo con los 3 medios de pago (Efectivo, Transferencia, Mercado Pago).
 * 3. Al confirmar, se valida y procesa el pago a través del servicio transaccional.
 * 4. Se emite el recibo oficial de cobranza con comprobante descargable / imprimible.
 */
@Controller
@RequestMapping("/pagos")
public class PagoController {

    private final PagoService pagoService;
    private final CuotaService cuotaService;

    public PagoController(PagoService pagoService, CuotaService cuotaService) {
        this.pagoService = pagoService;
        this.cuotaService = cuotaService;
    }

    @GetMapping
    public String listarPagos(Model model) {
        List<PagoResponseDTO> pagos = pagoService.obtenerTodosLosPagos();
        model.addAttribute("pagos", pagos);
        return "pagos/lista";
    }

    @GetMapping("/registrar/{cuotaId}")
    public String formularioRegistrarPago(@PathVariable("cuotaId") Long cuotaId, Model model) {
        CuotaResponseDTO cuota = cuotaService.obtenerCuotaPorId(cuotaId);

        if (cuota.getEstado().name().equals("PAGADA")) {
            return "redirect:/pagos/recibo/" + cuota.getPagoId();
        }

        PagoRequestDTO pagoDTO = new PagoRequestDTO();
        pagoDTO.setCuotaId(cuotaId);
        pagoDTO.setMontoAbonado(cuota.getTotalExigible());
        pagoDTO.setTipoMedioPago(TipoMedioPago.EFECTIVO); // Predeterminado

        model.addAttribute("pagoDTO", pagoDTO);
        model.addAttribute("cuota", cuota);
        model.addAttribute("mediosPago", TipoMedioPago.values());

        return "pagos/registrar";
    }

    @PostMapping("/procesar")
    public String procesarPago(@Valid @ModelAttribute("pagoDTO") PagoRequestDTO dto,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            CuotaResponseDTO cuota = cuotaService.obtenerCuotaPorId(dto.getCuotaId());
            model.addAttribute("cuota", cuota);
            model.addAttribute("mediosPago", TipoMedioPago.values());
            return "pagos/registrar";
        }

        try {
            PagoResponseDTO response = pagoService.registrarPago(dto);
            redirectAttributes.addFlashAttribute("successMessage",
                    "¡Pago registrado exitosamente! Se emitió el Recibo Oficial N° " + response.getNumeroRecibo());
            return "redirect:/pagos/recibo/" + response.getId();
        } catch (Exception ex) {
            CuotaResponseDTO cuota = cuotaService.obtenerCuotaPorId(dto.getCuotaId());
            model.addAttribute("errorMessage", "Error al procesar el pago: " + ex.getMessage());
            model.addAttribute("cuota", cuota);
            model.addAttribute("mediosPago", TipoMedioPago.values());
            return "pagos/registrar";
        }
    }

    @GetMapping("/recibo/{id}")
    public String verRecibo(@PathVariable("id") Long id, Model model) {
        PagoResponseDTO pago = pagoService.obtenerPagoPorId(id);
        model.addAttribute("pago", pago);
        return "pagos/recibo";
    }
}
