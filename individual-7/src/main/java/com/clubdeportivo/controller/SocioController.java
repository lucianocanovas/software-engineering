package com.clubdeportivo.controller;

import com.clubdeportivo.dto.request.SocioRequestDTO;
import com.clubdeportivo.dto.response.CuotaResponseDTO;
import com.clubdeportivo.dto.response.GrupoFamiliarResponseDTO;
import com.clubdeportivo.dto.response.SocioResponseDTO;
import com.clubdeportivo.entity.enums.CategoriaSocio;
import com.clubdeportivo.service.CuotaService;
import com.clubdeportivo.service.GrupoFamiliarService;
import com.clubdeportivo.service.SocioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * =========================================================================================
 * CONTROLADOR: SocioController
 * =========================================================================================
 * Orquesta las vistas y peticiones para el registro, edición, consulta y baja de Socios:
 * - Cumple con el desacoplamiento estricto usando SocioRequestDTO y SocioResponseDTO.
 * - Integra validaciones automáticas con @Valid y captura errores en BindingResult.
 * - Despliega fotografías de rostro y estados de habilitación de acceso.
 */
@Controller
@RequestMapping("/socios")
public class SocioController {

    private final SocioService socioService;
    private final GrupoFamiliarService grupoFamiliarService;
    private final CuotaService cuotaService;

    public SocioController(SocioService socioService,
                           GrupoFamiliarService grupoFamiliarService,
                           CuotaService cuotaService) {
        this.socioService = socioService;
        this.grupoFamiliarService = grupoFamiliarService;
        this.cuotaService = cuotaService;
    }

    @GetMapping
    public String listarSocios(@RequestParam(value = "buscar", required = false) String buscar, Model model) {
        List<SocioResponseDTO> socios = (buscar != null && !buscar.isBlank())
                ? socioService.buscarSocios(buscar)
                : socioService.obtenerTodosLosSocios();

        model.addAttribute("socios", socios);
        model.addAttribute("buscar", buscar);
        return "socios/lista";
    }

    @GetMapping("/nuevo")
    public String formularioNuevoSocio(Model model) {
        model.addAttribute("socioDTO", new SocioRequestDTO());
        model.addAttribute("categorias", CategoriaSocio.values());
        model.addAttribute("esEdicion", false);
        return "socios/formulario";
    }

    @PostMapping("/guardar")
    public String guardarSocio(@Valid @ModelAttribute("socioDTO") SocioRequestDTO dto,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categorias", CategoriaSocio.values());
            model.addAttribute("esEdicion", dto.getId() != null);
            return "socios/formulario";
        }

        try {
            if (dto.getId() == null) {
                socioService.crearSocio(dto);
                redirectAttributes.addFlashAttribute("successMessage", "Socio y Grupo Familiar creados exitosamente.");
            } else {
                socioService.actualizarSocio(dto.getId(), dto);
                redirectAttributes.addFlashAttribute("successMessage", "Datos del socio actualizados exitosamente.");
            }
            return "redirect:/socios";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("categorias", CategoriaSocio.values());
            model.addAttribute("esEdicion", dto.getId() != null);
            return "socios/formulario";
        }
    }

    @GetMapping("/{id}")
    public String verDetalleSocio(@PathVariable("id") Long id, Model model) {
        SocioResponseDTO socio = socioService.obtenerSocioPorId(id);
        model.addAttribute("socio", socio);

        try {
            GrupoFamiliarResponseDTO grupo = grupoFamiliarService.obtenerGrupoPorSocioTitular(id);
            model.addAttribute("grupoFamiliar", grupo);
        } catch (IllegalArgumentException e) {
            model.addAttribute("grupoFamiliar", null);
        }

        List<CuotaResponseDTO> cuotas = cuotaService.obtenerCuotasPorSocio(id);
        model.addAttribute("cuotas", cuotas);

        return "socios/detalle";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditarSocio(@PathVariable("id") Long id, Model model) {
        SocioResponseDTO socio = socioService.obtenerSocioPorId(id);
        SocioRequestDTO dto = new SocioRequestDTO();
        dto.setId(socio.getId());
        dto.setDni(socio.getDni());
        dto.setNombre(socio.getNombre());
        dto.setApellido(socio.getApellido());
        dto.setFechaNacimiento(socio.getFechaNacimiento());
        dto.setTelefono(socio.getTelefono());
        dto.setEmail(socio.getEmail());
        dto.setNumeroSocio(socio.getNumeroSocio());
        dto.setCategoria(socio.getCategoria());
        dto.setFotoData(socio.getRutaFoto());

        model.addAttribute("socioDTO", dto);
        model.addAttribute("categorias", CategoriaSocio.values());
        model.addAttribute("esEdicion", true);
        return "socios/formulario";
    }

    @PostMapping("/baja/{id}")
    public String darDeBaja(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            socioService.darDeBajaSocio(id);
            redirectAttributes.addFlashAttribute("warningMessage", "El socio ha sido dado de baja en el sistema.");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/socios";
    }

    @PostMapping("/reactivar/{id}")
    public String reactivar(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            socioService.reactivarSocio(id);
            redirectAttributes.addFlashAttribute("successMessage", "El socio ha sido reactivado exitosamente.");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/socios";
    }
}
