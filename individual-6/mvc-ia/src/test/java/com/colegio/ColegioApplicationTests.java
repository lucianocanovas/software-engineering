package com.colegio;

import com.colegio.dto.DocenteDTO;
import com.colegio.dto.DocenteRegistroDTO;
import com.colegio.model.Sexo;
import com.colegio.service.DocenteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ColegioApplicationTests {

    @Autowired
    private DocenteService docenteService;

    @Test
    void contextLoads() {
        assertNotNull(docenteService, "El servicio DocenteService debe inicializarse en el contexto Spring");
    }

    @Test
    void testRegistroDocenteYConsulta() {
        DocenteRegistroDTO nuevoDocente = new DocenteRegistroDTO();
        nuevoDocente.setNombre("Valeria");
        nuevoDocente.setApellido("Montes");
        nuevoDocente.setSexo(Sexo.FEMENINO);
        nuevoDocente.setFechaNacimiento(LocalDate.of(1990, 8, 14));
        nuevoDocente.setEmail("valeria.montes.test@colegio.edu.ar");
        nuevoDocente.setDni("35987123");
        nuevoDocente.setLegajo(2005);
        nuevoDocente.setEspecialidad("Biología y Química");
        nuevoDocente.setSalario(890000.0);
        nuevoDocente.setPassword("secreta123");
        nuevoDocente.setConfirmPassword("secreta123");

        DocenteDTO guardado = docenteService.registrarDocente(nuevoDocente);

        assertNotNull(guardado.getId(), "El ID del docente debe haber sido generado con UUID");
        assertEquals("Valeria Montes", guardado.getNombreCompleto());
        assertEquals("valeria.montes.test@colegio.edu.ar", guardado.getEmail());
        assertEquals(2005, guardado.getLegajo());

        List<DocenteDTO> docentes = docenteService.listarTodos();
        assertFalse(docentes.isEmpty(), "La lista de docentes no debe estar vacía");
    }
}

