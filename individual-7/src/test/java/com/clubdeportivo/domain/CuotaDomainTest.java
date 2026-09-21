package com.clubdeportivo.domain;

import com.clubdeportivo.entity.Cuota;
import com.clubdeportivo.entity.enums.EstadoCuota;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * =========================================================================================
 * PRUEBAS UNITARIAS: CuotaDomainTest
 * =========================================================================================
 * Evalúa las reglas financieras de vencimiento y cálculo automático de recargos por mora.
 */
class CuotaDomainTest {

    @Test
    @DisplayName("Cuota con fecha futura de vencimiento no debe figurar vencida")
    void testCuotaEnTermino() {
        Cuota cuota = new Cuota("2026-10", new BigDecimal("15000.00"), new BigDecimal("2000.00"), LocalDate.now().plusDays(10), null, null);

        assertFalse(cuota.estaVencida());
        assertEquals(new BigDecimal("15000.00"), cuota.calcularTotal());
    }

    @Test
    @DisplayName("Cuota con fecha vencida debe sumar recargo por mora al total exigible")
    void testCuotaVencidaAplicaRecargo() {
        Cuota cuota = new Cuota("2026-08", new BigDecimal("15000.00"), new BigDecimal("2500.00"), LocalDate.now().minusDays(5), null, null);
        cuota.setEstado(EstadoCuota.VENCIDA);

        assertTrue(cuota.estaVencida());
        assertEquals(new BigDecimal("17500.00"), cuota.calcularTotal());
    }

    @Test
    @DisplayName("Cuota PAGADA nunca se considera vencida aun con fecha pasada")
    void testCuotaPagadaNoVencida() {
        Cuota cuota = new Cuota("2026-08", new BigDecimal("15000.00"), new BigDecimal("2500.00"), LocalDate.now().minusDays(5), null, null);
        cuota.setEstado(EstadoCuota.PAGADA);

        assertFalse(cuota.estaVencida());
        assertEquals(new BigDecimal("15000.00"), cuota.calcularTotal());
    }
}

