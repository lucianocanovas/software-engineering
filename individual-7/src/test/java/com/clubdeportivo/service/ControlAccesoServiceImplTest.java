package com.clubdeportivo.service;

import com.clubdeportivo.dto.request.RegistroAccesoRequestDTO;
import com.clubdeportivo.dto.response.RegistroAccesoResponseDTO;
import com.clubdeportivo.entity.PuntoDeAcceso;
import com.clubdeportivo.entity.RegistroAcceso;
import com.clubdeportivo.entity.Socio;
import com.clubdeportivo.entity.enums.CategoriaSocio;
import com.clubdeportivo.entity.enums.EstadoPuntoAcceso;
import com.clubdeportivo.entity.enums.TipoMovimiento;
import com.clubdeportivo.mapper.RegistroAccesoMapper;
import com.clubdeportivo.repository.PersonaRepository;
import com.clubdeportivo.repository.PuntoDeAccesoRepository;
import com.clubdeportivo.repository.RegistroAccesoRepository;
import com.clubdeportivo.service.impl.ControlAccesoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * =========================================================================================
 * PRUEBAS UNITARIAS: ControlAccesoServiceImplTest
 * =========================================================================================
 * Verifica la lógica de validación física en molinetes:
 * 1. Autorización de paso para socios al día con cuotas pagadas.
 * 2. Advertencia / bloqueo de acceso para socios morosos.
 * 3. Bloqueo cuando el molinete se encuentra en mantenimiento técnico.
 */
@ExtendWith(MockitoExtension.class)
class ControlAccesoServiceImplTest {

    @Mock
    private RegistroAccesoRepository registroAccesoRepository;

    @Mock
    private PersonaRepository personaRepository;

    @Mock
    private PuntoDeAccesoRepository puntoDeAccesoRepository;

    @Mock
    private RegistroAccesoMapper registroAccesoMapper;

    @InjectMocks
    private ControlAccesoServiceImpl controlAccesoService;

    private Socio socioMock;
    private PuntoDeAcceso puntoMock;

    @BeforeEach
    void setUp() {
        socioMock = new Socio("32456789", "Carlos", "Gómez", LocalDate.of(1986, 7, 20), "11556677", "carlos@test.com", "SOC-1001", CategoriaSocio.ACTIVO);
        socioMock.setId(1L);

        puntoMock = new PuntoDeAcceso("Molinete Principal", "Acceso Central", EstadoPuntoAcceso.OPERATIVO, "192.168.1.50");
        puntoMock.setId(10L);
    }

    @Test
    @DisplayName("Debe autorizar el paso si el socio está al día y el molinete está operativo")
    void testRegistrarEntradaSocioAlDia() {
        when(personaRepository.findByDni("32456789")).thenReturn(Optional.of(socioMock));
        when(puntoDeAccesoRepository.findById(10L)).thenReturn(Optional.of(puntoMock));
        when(registroAccesoRepository.save(any(RegistroAcceso.class))).thenAnswer(inv -> inv.getArgument(0));

        RegistroAccesoResponseDTO responseMock = new RegistroAccesoResponseDTO();
        responseMock.setId(1L);
        responseMock.setPersonaNombreCompleto("Carlos Gómez");
        responseMock.setTipoMovimiento(TipoMovimiento.ENTRADA);
        when(registroAccesoMapper.toResponseDTO(any(RegistroAcceso.class))).thenReturn(responseMock);

        RegistroAccesoRequestDTO request = new RegistroAccesoRequestDTO();
        request.setDniPersona("32456789");
        request.setPuntoDeAccesoId(10L);
        request.setTipoMovimiento(TipoMovimiento.ENTRADA);

        RegistroAccesoResponseDTO result = controlAccesoService.registrarMovimiento(request);

        assertNotNull(result);
        assertTrue(result.isHabilitado(), "El paso debe figurar habilitado");
        verify(registroAccesoRepository).save(any(RegistroAcceso.class));
    }

    @Test
    @DisplayName("Debe rechazar el paso si el molinete se encuentra fuera de servicio")
    void testRegistrarEntradaPuntoInactivo() {
        puntoMock.setEstado(EstadoPuntoAcceso.EN_MANTENIMIENTO);
        when(personaRepository.findByDni("32456789")).thenReturn(Optional.of(socioMock));
        when(puntoDeAccesoRepository.findById(10L)).thenReturn(Optional.of(puntoMock));

        RegistroAccesoRequestDTO request = new RegistroAccesoRequestDTO();
        request.setDniPersona("32456789");
        request.setPuntoDeAccesoId(10L);
        request.setTipoMovimiento(TipoMovimiento.ENTRADA);

        assertThrows(IllegalStateException.class, () -> controlAccesoService.registrarMovimiento(request),
                "Debe lanzar excepción si el molinete no está operativo");
    }
}

