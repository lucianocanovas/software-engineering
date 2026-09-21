package com.clubdeportivo.service;

import com.clubdeportivo.dto.request.PagoRequestDTO;
import com.clubdeportivo.dto.response.PagoResponseDTO;

import java.util.List;

/**
 * =========================================================================================
 * SERVICIO: PagoService (Módulo Central de Tesorería)
 * =========================================================================================
 * Procesa la cancelación de cuotas familiares utilizando distintos medios de pago:
 * 1. Efectivo: Caja presencial, con bonificación opcional.
 * 2. Transferencia: Conciliación interbancaria por CBU y comprobante.
 * 3. Mercado Pago: Pasarela fintech con payment_id, QR y confirmación digital.
 */
public interface PagoService {

    /**
     * Registra y aplica el pago a una cuota familiar pendiente o vencida.
     * Actualiza el estado de la cuota a PAGADA y restituye la habilitación del socio a AL DÍA.
     */
    PagoResponseDTO registrarPago(PagoRequestDTO dto);

    PagoResponseDTO obtenerPagoPorId(Long id);

    List<PagoResponseDTO> obtenerPagosPorSocio(Long socioId);

    List<PagoResponseDTO> obtenerTodosLosPagos();
}

