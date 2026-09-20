package com.techstore.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * =============================================================================
 * CAPA SEGURIDAD / CONTROL DE ACCESO: AuthInterceptor
 * =============================================================================
 * Interceptor de peticiones HTTP en la arquitectura Spring MVC.
 *
 * CUMPLE EL REQUERIMIENTO:
 * "Agregue al problema planteado la posibilidad que quienes acceden al sistema
 *  lo realizan por medio de un usuario y contraseña."
 *
 * Verifica antes de ejecutar cualquier acción del controlador (preHandle)
 * que exista una sesión HTTP activa con un UsuarioDTO autenticado.
 * Si no está autenticado, redirige automáticamente a la vista de /login.
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    public static final String SESSION_USER_KEY = "usuarioLogueado";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();

        // Rutas públicas permitidas sin autenticación previa
        if (uri.startsWith("/login") || uri.startsWith("/css") || uri.startsWith("/js") ||
            uri.startsWith("/vendor") || uri.startsWith("/images") || uri.equals("/error")) {
            return true;
        }

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute(SESSION_USER_KEY) != null) {
            return true; // Acceso concedido
        }

        // Redirección al login con parámetro informativo
        response.sendRedirect(request.getContextPath() + "/login?sesionExpirada=true");
        return false;
    }
}

