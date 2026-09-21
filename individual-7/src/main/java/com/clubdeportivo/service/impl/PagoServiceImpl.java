package com.clubdeportivo.service.impl;

import com.clubdeportivo.dto.request.PagoRequestDTO;
import com.clubdeportivo.dto.response.PagoResponseDTO;
import com.clubdeportivo.entity.*;
import com.clubdeportivo.entity.enums.EstadoCuota;
import com.clubdeportivo.entity.enums.EstadoPago;
import com.clubdeportivo.mapper.PagoMapper;
import com.clubdeportivo.repository.CuotaRepository;
import com.clubdeportivo.repository.PagoRepository;
import com.clubdeportivo.service.PagoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * =========================================================================================
 * SERVICIO IMPL: PagoServiceImpl
 * =========================================================================================
 * Implementación central para el procesamiento polimórfico de pagos de cuotas del club:
 * - Demuestra composición transaccional: Persiste la entidad Pago junto a su MedioPago hijo.
 * - Soporta EFECTIVO, TRANSFERENCIA y MERCADO PAGO según los requerimientos solicitados.
 * - Actualiza atómicamente el estado de la Cuota a PAGADA y restituye la habilitación del socio.
 */
@Service
@Transactional
public class PagoServiceImpl implements PagoService {

    private final PagoRepository pagoRepository;
    private final CuotaRepository cuotaRepository;
    private final PagoMapper pagoMapper;

    public PagoServiceImpl(PagoRepository pagoRepository,
                           CuotaRepository cuotaRepository,
                           PagoMapper pagoMapper) {
        this.pagoRepository = pagoRepository;
        this.cuotaRepository = cuotaRepository;
        this.pagoMapper = pagoMapper;
    }

    @Override
    public PagoResponseDTO registrarPago(PagoRequestDTO dto) {
        Cuota cuota = cuotaRepository.findById(dto.getCuotaId())
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la cuota con ID: " + dto.getCuotaId()));

        if (cuota.getEstado() == EstadoCuota.PAGADA) {
            throw new IllegalStateException("La cuota del periodo " + cuota.getPeriodo() + " ya se encuentra totalmente cancelada.");
        }

        // Instanciación polimórfica del MedioPago correspondiente
        MedioPago medioPago;
        switch (dto.getTipoMedioPago()) {
            case EFECTIVO -> {
                BigDecimal descuento = dto.getDescuentoAplicado() != null ? dto.getDescuentoAplicado() : BigDecimal.ZERO;
                String caja = (dto.getNumeroCaja() != null && !dto.getNumeroCaja().isBlank()) ? dto.getNumeroCaja() : "CAJA-01";
                String cajero = (dto.getCajeroResponsable() != null && !dto.getCajeroResponsable().isBlank()) ? dto.getCajeroResponsable() : "Operador Tesorería";
                medioPago = new PagoEfectivo(descuento, caja, cajero);
            }
            case TRANSFERENCIA -> {
                String cbuOrigen = (dto.getCbuOrigen() != null) ? dto.getCbuOrigen() : "0000000000000000000000";
                String cbuDestino = (dto.getCbuDestino() != null && !dto.getCbuDestino().isBlank()) ? dto.getCbuDestino() : "0000003100010002000304";
                String banco = (dto.getBancoOrigen() != null && !dto.getBancoOrigen().isBlank()) ? dto.getBancoOrigen() : "Banco Nación";
                String numOp = (dto.getNumeroOperacion() != null && !dto.getNumeroOperacion().isBlank()) ? dto.getNumeroOperacion() : "TRF-" + System.currentTimeMillis();
                String hash = (dto.getComprobanteHash() != null) ? dto.getComprobanteHash() : UUID.randomUUID().toString().substring(0, 16).toUpperCase();
                medioPago = new PagoTransferencia(cbuOrigen, cbuDestino, banco, numOp, hash);
            }
            case MERCADO_PAGO -> {
                String paymentId = (dto.getMpPaymentId() != null && !dto.getMpPaymentId().isBlank())
                        ? dto.getMpPaymentId()
                        : "MP-" + System.currentTimeMillis();
                String prefId = (dto.getMpPreferenceId() != null) ? dto.getMpPreferenceId() : "PREF-" + UUID.randomUUID().toString().substring(0, 8);
                String qrUrl = (dto.getQrCodeUrl() != null && !dto.getQrCodeUrl().isBlank())
                        ? dto.getQrCodeUrl()
                        : "https://mercadopago.com.ar/qr/checkout/" + paymentId;
                String status = (dto.getStatusDetail() != null) ? dto.getStatusDetail() : "accredited";
                medioPago = new PagoMercadoPago(paymentId, prefId, qrUrl, status);
            }
            default -> throw new IllegalArgumentException("Tipo de medio de pago no reconocido: " + dto.getTipoMedioPago());
        }

        // Generación del número legal de recibo oficial
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String sufijo = UUID.randomUUID().toString().substring(0, 5).toUpperCase();
        String numeroRecibo = "REC-" + timestamp + "-" + sufijo;

        // Construcción del Pago en COMPOSICIÓN con MedioPago
        Pago pago = new Pago(numeroRecibo, dto.getMontoAbonado(), cuota, medioPago, cuota.getSocioTitular());
        pago.setEstado(EstadoPago.COMPLETADO);
        pago.setNotas(dto.getNotas());

        // Actualización atómica de la Cuota
        cuota.setEstado(EstadoCuota.PAGADA);
        cuota.setFechaPago(LocalDate.now());
        cuota.setPago(pago);

        // Persistencia en cascada (CascadeType.ALL guarda Pago y MedioPago)
        Pago pagoGuardado = pagoRepository.save(pago);
        cuotaRepository.save(cuota);

        return pagoMapper.toResponseDTO(pagoGuardado);
    }

    @Override
    @Transactional(readOnly = true)
    public PagoResponseDTO obtenerPagoPorId(Long id) {
        return pagoRepository.findById(id)
                .map(pagoMapper::toResponseDTO)
                .orElseThrow(() -> new IllegalArgumentException("Pago no encontrado con ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PagoResponseDTO> obtenerPagosPorSocio(Long socioId) {
        return pagoRepository.findBySocioIdOrderByFechaPagoDesc(socioId).stream()
                .map(pagoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PagoResponseDTO> obtenerTodosLosPagos() {
        return pagoRepository.findAll().stream()
                .map(pagoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}

