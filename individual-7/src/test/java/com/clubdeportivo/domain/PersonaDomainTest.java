package com.clubdeportivo.domain;

import com.clubdeportivo.entity.FamiliarAdherente;
import com.clubdeportivo.entity.Fotografia;
import com.clubdeportivo.entity.Socio;
import com.clubdeportivo.entity.enums.CategoriaSocio;
import com.clubdeportivo.entity.enums.Parentesco;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * =========================================================================================
 * PRUEBAS UNITARIAS: PersonaDomainTest
 * =========================================================================================
 * Evalúa las reglas de negocio del dominio Persona, FamiliarAdherente y Fotografia:
 * 1. Cálculo exacto de edad cronológica.
 * 2. Determinación de minoridad de edad (< 18 años).
 * 3. Formateo legal de nombre completo.
 * 4. Validación biométrica facial de Fotografia.
 */
class PersonaDomainTest {

    @Test
    @DisplayName("Debe calcular correctamente la edad cronológica")
    void testCalcularEdad() {
        LocalDate nacimiento = LocalDate.now().minusYears(25).minusMonths(2);
        Socio socio = new Socio("12345678", "Juan", "Pérez", nacimiento, "11223344", "juan@test.com", "SOC-101", CategoriaSocio.ACTIVO);

        assertEquals(25, socio.calcularEdad());
    }

    @Test
    @DisplayName("Debe determinar si un familiar adherente es menor de 18 años")
    void testEsMenorDeEdad() {
        FamiliarAdherente menor = new FamiliarAdherente("50111222", "Tomás", "Gómez",
                LocalDate.now().minusYears(10), "", "", Parentesco.HIJO);

        FamiliarAdherente mayor = new FamiliarAdherente("35111222", "Esteban", "Gómez",
                LocalDate.now().minusYears(20), "", "", Parentesco.HIJO);

        assertTrue(menor.esMenorDeEdad(), "El familiar de 10 años debe ser catalogado como menor de edad");
        assertFalse(mayor.esMenorDeEdad(), "El familiar de 20 años debe ser catalogado como mayor de edad");
    }

    @Test
    @DisplayName("Debe formatear el nombre completo en formato Apellido, Nombre")
    void testObtenerNombreCompleto() {
        Socio socio = new Socio("12345678", "Carlos", "Gómez", LocalDate.of(1990, 5, 10), "11223344", "carlos@test.com", "SOC-102", CategoriaSocio.ACTIVO);

        assertEquals("GÓMEZ, Carlos", socio.obtenerNombreCompleto());
    }

    @Test
    @DisplayName("Debe validar la fotografía facial según formato y tamaño")
    void testValidarRostro() {
        Fotografia fotoValida = new Fotografia("/uploads/fotos/rostro1.jpg", "image/jpeg", 50000L);
        assertTrue(fotoValida.validarRostro(), "La foto JPEG de 50KB debe ser válida para reconocimiento");

        Fotografia fotoSinRuta = new Fotografia("", "image/jpeg", 50000L);
        assertFalse(fotoSinRuta.validarRostro(), "Foto sin ruta debe ser rechazada");

        Fotografia fotoFormatoInvalido = new Fotografia("/uploads/doc.pdf", "application/pdf", 50000L);
        assertFalse(fotoFormatoInvalido.validarRostro(), "Formato PDF debe ser rechazado para rostro");

        Fotografia fotoVacia = new Fotografia("/uploads/foto.png", "image/png", 500L);
        assertFalse(fotoVacia.validarRostro(), "Archivos menores a 1KB deben ser rechazados");
    }
}

