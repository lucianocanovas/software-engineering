package com.techstore.controller;

import com.techstore.config.AuthInterceptor;
import com.techstore.dto.LoginDTO;
import com.techstore.dto.UsuarioDTO;
import com.techstore.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

/**
 * =============================================================================
 * CAPA CONTROLADOR: AuthController
 * =============================================================================
 * @Controller: Marca la clase como componente de presentación en Spring MVC.
 * Se encarga del flujo de autenticación de usuarios mediante credenciales:
 * "Agregue al problema planteado la posibilidad que quienes acceden al sistema
 *  lo realizan por medio de un usuario y contraseña."
 *
 * Utiliza LoginDTO para transportar de forma segura las credenciales recibidas.
 */
@Controller
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Muestra la vista de inicio de sesión con diseño Bootstrap 5.
     */
    @GetMapping("/login")
    public String mostrarLogin(Model model,
                               @RequestParam(value = "logout", required = false) String logout,
                               @RequestParam(value = "sesionExpirada", required = false) String sesionExpirada,
                               HttpSession session) {

        // Si ya está autenticado en la sesión actual, redirigir al Dashboard
        if (session.getAttribute(AuthInterceptor.SESSION_USER_KEY) != null) {
            return "redirect:/dashboard";
        }

        if (!model.containsAttribute("loginDTO")) {
            model.addAttribute("loginDTO", new LoginDTO());
        }

        if (logout != null) {
            model.addAttribute("mensajeInfo", "Has cerrado sesión correctamente.");
        }
        if (sesionExpirada != null) {
            model.addAttribute("mensajeAlerta", "Debes iniciar sesión para acceder a las funciones del sistema.");
        }

        return "auth/login";
    }

    /**
     * Procesa las credenciales ingresadas en el formulario de login.
     * Si son válidas, guarda el UsuarioDTO en la sesión HTTP y redirige al dashboard.
     */
    @PostMapping("/login")
    public String procesarLogin(@Valid @ModelAttribute("loginDTO") LoginDTO loginDTO,
                                BindingResult bindingResult,
                                HttpSession session,
                                Model model) {

        if (bindingResult.hasErrors()) {
            return "auth/login";
        }

        Optional<UsuarioDTO> usuarioOpt = usuarioService.autenticar(loginDTO);

        if (usuarioOpt.isEmpty()) {
            model.addAttribute("errorLogin", "Credenciales incorrectas o usuario inactivo. Verifique su usuario y contraseña.");
            return "auth/login";
        }

        // Guardar el usuario autenticado en la sesión HTTP
        UsuarioDTO usuarioAutenticado = usuarioOpt.get();
        session.setAttribute(AuthInterceptor.SESSION_USER_KEY, usuarioAutenticado);

        return "redirect:/dashboard";
    }

    /**
     * Cierra la sesión activa del usuario y redirige al formulario de acceso.
     */
    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session) {
        if (session != null) {
            session.removeAttribute(AuthInterceptor.SESSION_USER_KEY);
            session.invalidate();
        }
        return "redirect:/login?logout=true";
    }
}

