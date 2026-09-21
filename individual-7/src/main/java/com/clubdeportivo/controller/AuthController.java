package com.clubdeportivo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * =========================================================================================
 * CONTROLADOR: AuthController
 * =========================================================================================
 * Maneja el ciclo de vida de autenticación y la pantalla de inicio de sesión (Login)
 * integrada con Spring Security.
 */
@Controller
public class AuthController {

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Credenciales incorrectas o usuario inactivo. Intente nuevamente.");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "Ha cerrado su sesión de forma exitosa.");
        }
        return "auth/login";
    }
}
