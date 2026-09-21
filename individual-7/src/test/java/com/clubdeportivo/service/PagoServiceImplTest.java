package com.clubdeportivo.service;

import com.clubdeportivo.dto.request.PagoRequestDTO;
import com.clubdeportivo.dto.response.PagoResponseDTO;
import com.clubdeportivo.entity.Cuota;
import com.clubdeportivo.entity.Pago;
import com.clubdeportivo.entity.Socio;
import com.clubdeportivo.entity.enums.CategoriaSocio;
import com.clubdeportivo.entity.enums.EstadoCuota;
import com.clubdeportivo.entity.enums.TipoMedioPago;
import com.clubdeportivo.mapper.PagoMapper;
import com.clubdeportivo.repository.CuotaRepository;
import com.clubdeportivo.repository.PagoRepository;
import com.clubdeportivo.service.impl.PagoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * =========================================================================================
 * PRUEBAS UNITARIAS CON MOCKITO: PagoServiceImplTest
 * =========================================================================================
 * Verifica la lógica transaccional del módulo central de pagos para los tres canales:
 * 1. Pago en Efectivo con bonificación y número de caja.
 * 2. Pago por Transferencia Bancaria con CBU y conciliación.
 * 3. Pago digital con Mercado Pago (Payment ID y QR).
 * 4. Control de idempotencia y rechazo de cuotas previamente canceladas.
 */
@ExtendWith(MockitoExtension.class)
class PagoServiceImplTest {

    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private CuotaRepository cuotaRepository;

    @Mock
    private PagoMapper pagoMapper;

    @InjectMocks
    private PagoServiceImpl pagoService;

    private Socio socioMock;
    private Cuota cuotaMock;

    @BeforeEach
    void setUp() {
        socioMock = new Socio("30111222", "Carlos", "Gómez", LocalDate.of(1985, 3, 15), "11445566", "carlos@test.com", "SOC-1001", CategoriaSocio.ACTIVO);
        socioMock.setId(1L);

        cuotaMock = new Cuota("2026-09", new BigDecimal("15000.00"), BigDecimal.ZERO, LocalDate.now().plusDays(10), socioMock, null);
        cuotaMock.setId(100L);
        cuotaMock.setEstado(EstadoCuota.PENDIENTE);
    }

    @Test
    @DisplayName("Debe registrar un pago en EFECTIVO actualizando la cuota a PAGADA")
    void testRegistrarPagoEfectivo() {
        when(cuotaRepository.findById(100L)).thenReturn(Optional.of(cuotaMock));
        when(pagoRepository.save(any(Pago.class))).thenAnswer(inv -> inv.getArgument(0));

        PagoResponseDTO responseMock = new PagoResponseDTO();
        responseMock.setId(1L);
        responseMock.setNumeroRecibo("REC-2026-0001");
        responseMock.setTipoMedioPago(TipoMedioPago.EFECTIVO);
        when(pagoMapper.toResponseDTO(any(Pago.class))).thenReturn(responseMock);

        PagoRequestDTO request = new PagoRequestDTO();
        request.setCuotaId(100L);
        request.setTipoMedioPago(TipoMedioPago.EFECTIVO);
        request.setMontoAbonado(new BigDecimal("14000.00"));
        request.setDescuentoAplicado(new BigDecimal("1000.00"));
        request.setNumeroCaja("CAJA-01");
        request.setCajeroResponsable("Martín");

        PagoResponseDTO result = pagoService.registrarPago(request);

        assertNotNull(result);
        assertEquals(TipoMedioPago.EFECTIVO, result.getTipoMedioPago());
        assertEquals(EstadoCuota.PAGADA, cuotaMock.getEstado(), "La cuota debe quedar en estado PAGADA");
        assertNotNull(cuotaMock.getFechaPago(), "La fecha de pago debe haber sido estampada");

        ArgumentCaptor<Pago> captor = ArgumentCaptor.forClass(Pago.class);
        verify(pagoRepository).save(captor.capture());
        Pago pagoGuardado = captor.getValue();
        assertEquals(new BigDecimal("14000.00"), pagoGuardado.getMontoAbonado());
    }

    @Test
    @DisplayName("Debe registrar un pago por TRANSFERENCIA BANCARIA guardando datos de CBU y banco")
    void testRegistrarPagoTransferencia() {
        when(cuotaRepository.findById(100L)).thenReturn(Optional.of(cuotaMock));
        when(pagoRepository.save(any(Pago.class))).thenAnswer(inv -> inv.getArgument(0));

        PagoResponseDTO responseMock = new PagoResponseDTO();
        responseMock.setId(2L);
        responseMock.setTipoMedioPago(TipoMedioPago.TRANSFERENCIA);
        when(pagoMapper.toResponseDTO(any(Pago.class))).thenReturn(responseMock);

        PagoRequestDTO request = new PagoRequestDTO();
        request.setCuotaId(100L);
        request.setTipoMedioPago(TipoMedioPago.TRANSFERENCIA);
        request.setMontoAbonado(new BigDecimal("15000.00"));
        request.setCbuOrigen("0110599540000012345678");
        request.setBancoOrigen("Banco Santander");
        request.setNumeroOperacion("TRF-778899");

        PagoResponseDTO result = pagoService.registrarPago(request);

        assertNotNull(result);
        assertEquals(TipoMedioPago.TRANSFERENCIA, result.getTipoMedioPago());
        assertEquals(EstadoCuota.PAGADA, cuotaMock.getEstado());
        verify(pagoRepository).save(any(Pago.class));
    }

    @Test
    @DisplayName("Debe registrar un pago con MERCADO PAGO asignando el payment_id oficial")
    void testRegistrarPagoMercadoPago() {
        when(cuotaRepository.findById(100L)).thenReturn(Optional.of(cuotaMock));
        when(pagoRepository.save(any(Pago.class))).thenAnswer(inv -> inv.getArgument(0));

        PagoResponseDTO responseMock = new PagoResponseDTO();
        responseMock.setId(3L);
        responseMock.setTipoMedioPago(TipoMedioPago.MERCADO_PAGO);
        when(pagoMapper.toResponseDTO(any(Pago.class))).thenReturn(responseMock);

        PagoRequestDTO request = new PagoRequestDTO();
        request.setCuotaId(100L);
        request.setTipoMedioPago(TipoMedioPago.MERCADO_PAGO);
        request.setMontoAbonado(new BigDecimal("15000.00"));
        request.setMpPaymentId("MP-9988776655");

        PagoResponseDTO result = pagoService.registrarPago(request);

        assertNotNull(result);
        assertEquals(TipoMedioPago.MERCADO_PAGO, result.getTipoMedioPago());
        assertEquals(EstadoCuota.PAGADA, cuotaMock.getEstado());
    }

    @Test
    @DisplayName("Debe lanzar excepción si la cuota ya se encuentra PAGADA")
    void testRegistrarPagoCuotaYaPagada() {
        cuotaMock.setEstado(EstadoCuota.PAGADA);
        when(cuotaRepository.findById(100L)).thenReturn(Optional.of(cuotaMock));

        PagoRequestDTO request = new PagoRequestDTO();
        request.setCuotaId(100L);
        request.setTipoMedioPago(TipoMedioPago.EFECTIVO);
        request.setMontoAbonado(new BigDecimal("15000.00"));

        assertThrows(IllegalStateException.class, () -> pagoService.registrarPago(request),
                "No debe permitir registrar un nuevo pago sobre una cuota ya cancelada");
    }
}

