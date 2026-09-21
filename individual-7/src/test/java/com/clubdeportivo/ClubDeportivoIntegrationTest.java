package com.clubdeportivo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * =========================================================================================
 * PRUEBAS DE INTEGRACIÓN: ClubDeportivoIntegrationTest
 * =========================================================================================
 * Evalúa el ciclo completo MVC, arranque del contexto de Spring Boot, seguridad y Thymeleaf:
 * 1. Página de login accesible sin credenciales.
 * 2. Redirección al login para usuarios no autenticados en rutas protegidas.
 * 3. Acceso exitoso al Dashboard y Padrón para usuario autenticado.
 * 4. Disponibilidad del módulo de pagos y recaudación.
 */
@SpringBootTest
@AutoConfigureMockMvc
class ClubDeportivoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Página de Login debe responder 200 OK públicamente")
    void testLoginPublico() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/login"));
    }

    @Test
    @DisplayName("Rutas protegidas deben redirigir a /login para usuarios anónimos")
    void testRutaProtegidaRedirige() throws Exception {
        mockMvc.perform(get("/dashboard"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Dashboard debe cargar con éxito (200 OK) para usuario autenticado")
    void testDashboardAutenticado() throws Exception {
        mockMvc.perform(get("/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard/index"))
                .andExpect(model().attributeExists("stats"))
                .andExpect(model().attributeExists("ultimosAccesos"))
                .andExpect(model().attributeExists("ultimosPagos"));
    }

    @Test
    @WithMockUser(username = "operador", roles = {"OPERADOR"})
    @DisplayName("Padrón de socios debe ser visible para operador autenticado")
    void testSociosListaAutenticado() throws Exception {
        mockMvc.perform(get("/socios"))
                .andExpect(status().isOk())
                .andExpect(view().name("socios/lista"))
                .andExpect(model().attributeExists("socios"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Historial de pagos debe responder 200 OK con modelo de datos")
    void testPagosListaAutenticado() throws Exception {
        mockMvc.perform(get("/pagos"))
                .andExpect(status().isOk())
                .andExpect(view().name("pagos/lista"))
                .andExpect(model().attributeExists("pagos"));
    }
}

