package com.clubdeportivo.domain;

import com.clubdeportivo.entity.Cuota;
import com.clubdeportivo.entity.Socio;
import com.clubdeportivo.entity.enums.CategoriaSocio;
import com.clubdeportivo.entity.enums.EstadoCuota;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * =========================================================================================
 * PRUEBAS UNITARIAS: SocioDomainTest
 * =========================================================================================
 * Evalúa las reglas de habilitación financiera y condición de socio al día:
 * 1. Socio sin cuotas o con cuotas pagadas -> estaAlDia() == true.
 * 2. Socio con cuota vencida o pendiente fuera de término -> estaAlDia() == false.
 * 3. Socio dado de baja -> estaAlDia() == false.
 */
class SocioDomainTest {

    @Test
    @DisplayName("Socio nuevo sin cuotas debe estar al día por defecto")
    void testSocioSinCuotasEstaAlDia() {
        Socio socio = new Socio("12345678", "Ana", "López", LocalDate.of(1995, 4, 12), "112233", "ana@test.com", "SOC-201", CategoriaSocio.ACTIVO);
        assertTrue(socio.estaAlDia());
    }

    @Test
    @DisplayName("Socio con cuota PAGADA debe permanecer al día")
    void testSocioConCuotaPagadaEstaAlDia() {
        Socio socio = new Socio("12345678", "Ana", "López", LocalDate.of(1995, 4, 12), "112233", "ana@test.com", "SOC-201", CategoriaSocio.ACTIVO);

        Cuota cuota = new Cuota("2026-08", new BigDecimal("15000.00"), BigDecimal.ZERO, LocalDate.now().minusDays(10), socio, null);
        cuota.setEstado(EstadoCuota.PAGADA);
        socio.setCuotas(List.of(cuota));

        assertTrue(socio.estaAlDia(), "El socio con cuota PAGADA debe figurar al día");
    }

    @Test
    @DisplayName("Socio con cuota VENCIDA debe figurar como moroso (no al día)")
    void testSocioConCuotaVencidaNoEstaAlDia() {
        Socio socio = new Socio("12345678", "Ana", "López", LocalDate.of(1995, 4, 12), "112233", "ana@test.com", "SOC-201", CategoriaSocio.ACTIVO);

        Cuota cuota = new Cuota("2026-08", new BigDecimal("15000.00"), new BigDecimal("2000.00"), LocalDate.now().minusDays(5), socio, null);
        cuota.setEstado(EstadoCuota.VENCIDA);
        socio.setCuotas(List.of(cuota));

        assertFalse(socio.estaAlDia(), "El socio con cuota VENCIDA no debe figurar al día");
    }

    @Test
    @DisplayName("Socio dado de baja debe perder la condición de habilitado")
    void testSocioDadoDeBaja() {
        Socio socio = new Socio("12345678", "Ana", "López", LocalDate.of(1995, 4, 12), "112233", "ana@test.com", "SOC-201", CategoriaSocio.ACTIVO);
        socio.darDeBaja();

        assertFalse(socio.getActivo(), "El socio debe quedar inactivo");
        assertFalse(socio.estaAlDia(), "Un socio inactivo no puede estar al día para ingresar al club");
    }
}

