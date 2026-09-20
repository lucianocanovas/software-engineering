package com.colegio.controller;

import com.colegio.dto.DocenteRegistroDTO;
import com.colegio.model.Sexo;
import com.colegio.service.DocenteService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * =========================================================================================
 * CONTROLADOR: AuthController
 * =========================================================================================
 * Gestiona los flujos de Autenticación y Registro de Docentes en la arquitectura MVC:
 * - /login: Despacho del formulario visual de ingreso.
 * - /registro: Alta de nuevos docentes con los atributos exigidos:
 *   "Nombre", "Apellido", "Sexo" y "Fecha de Nacimiento".
 *   El usuario es el correo personal del docente.
 *   Envío de correo de bienvenida al registrarse.
 */
@Controller
public class AuthController {

    private final DocenteService docenteService;

    public AuthController(DocenteService docenteService) {
        this.docenteService = docenteService;
    }

    /**
     * Muestra la vista del formulario de inicio de sesión.
     */
    @GetMapping("/login")
    public String loginForm(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        if (error != null) {
            model.addAttribute("errorMsg", "Correo electrónico o contraseña incorrectos. Por favor intente nuevamente.");
        }
        if (logout != null) {
            model.addAttribute("logoutMsg", "Ha cerrado sesión de forma segura.");
        }
        return "auth/login";
    }

    /**
     * Muestra el formulario de registro de un nuevo docente.
     */
    @GetMapping("/registro")
    public String registroForm(Model model) {
        if (!model.containsAttribute("docente")) {
            model.addAttribute("docente", new DocenteRegistroDTO());
        }
        model.addAttribute("sexos", Sexo.values());
        return "auth/registro";
    }

    /**
     * Procesa la solicitud POST de registro de docente.
     * Valida el DTO, persiste la información y dispara el correo de bienvenida.
     */
    @PostMapping("/registro")
    public String procesarRegistro(@Valid @ModelAttribute("docente") DocenteRegistroDTO dto,
                                   BindingResult bindingResult,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("sexos", Sexo.values());
            return "auth/registro";
        }

        try {
            docenteService.registrarDocente(dto);
            redirectAttributes.addFlashAttribute("successMsg",
                    "¡Registro exitoso! Se ha enviado un correo de bienvenida a su dirección personal ("
                    + dto.getEmail() + "). Ya puede iniciar sesión con sus credenciales.");
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMsg", e.getMessage());
            model.addAttribute("sexos", Sexo.values());
            return "auth/registro";
        }
    }
}

