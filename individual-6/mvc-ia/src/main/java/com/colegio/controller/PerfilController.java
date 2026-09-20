package com.colegio.controller;

import com.colegio.dto.CambioPasswordDTO;
import com.colegio.service.DocenteService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * =========================================================================================
 * CONTROLADOR: PerfilController
 * =========================================================================================
 * Provee la funcionalidad exigida:
 * "además el sistema debe tener la posibilidad de cambiar la contraseña."
 *
 * Permite al docente autenticado modificar su clave de acceso validando su contraseña actual
 * y re-encriptándola con BCrypt.
 */
@Controller
@RequestMapping("/perfil")
public class PerfilController {

    private final DocenteService docenteService;

    public PerfilController(DocenteService docenteService) {
        this.docenteService = docenteService;
    }

    @GetMapping("/cambiar-password")
    public String formCambiarPassword(Model model) {
        if (!model.containsAttribute("cambioPassword")) {
            model.addAttribute("cambioPassword", new CambioPasswordDTO());
        }
        return "perfil/cambiar-password";
    }

    @PostMapping("/cambiar-password")
    public String procesarCambioPassword(@Valid @ModelAttribute("cambioPassword") CambioPasswordDTO dto,
                                         BindingResult bindingResult,
                                         Authentication authentication,
                                         Model model,
                                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "perfil/cambiar-password";
        }

        try {
            String emailDocente = authentication.getName();
            docenteService.cambiarPassword(emailDocente, dto);
            redirectAttributes.addFlashAttribute("successMsg", "¡Contraseña actualizada exitosamente! Puede continuar utilizando el sistema.");
            return "redirect:/dashboard";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMsg", e.getMessage());
            return "perfil/cambiar-password";
        }
    }
}

