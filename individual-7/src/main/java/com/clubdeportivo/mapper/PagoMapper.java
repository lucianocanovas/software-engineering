package com.clubdeportivo.mapper;

import com.clubdeportivo.dto.response.PagoResponseDTO;
import com.clubdeportivo.entity.Pago;
import org.springframework.stereotype.Component;

/**
 * =========================================================================================
 * MAPPER: PagoMapper
 * =========================================================================================
 * Transforma la entidad Pago y sus medios polimórficos a PagoResponseDTO para la vista y recibos.
 */
@Component
public class PagoMapper {

    public PagoResponseDTO toResponseDTO(Pago p) {
        if (p == null) {
            return null;
        }
        PagoResponseDTO dto = new PagoResponseDTO();
        dto.setId(p.getId());
        dto.setNumeroRecibo(p.getNumeroRecibo());
        dto.setFechaPago(p.getFechaPago());
        dto.setMontoAbonado(p.getMontoAbonado());
        dto.setEstado(p.getEstado());
        dto.setNotas(p.getNotas());
        dto.setComprobanteTexto(p.generarComprobante());

        if (p.getMedioPago() != null) {
            dto.setTipoMedioPago(p.getMedioPago().getTipo());
            dto.setDetalleMedioPago(p.getMedioPago().obtenerDetalleTransaccion());
        }

        if (p.getCuota() != null) {
            dto.setCuotaId(p.getCuota().getId());
            dto.setCuotaPeriodo(p.getCuota().getPeriodo());
        }

        if (p.getSocio() != null) {
            dto.setSocioTitularId(p.getSocio().getId());
            dto.setSocioTitularNombre(p.getSocio().obtenerNombreCompleto());
            dto.setSocioNumero(p.getSocio().getNumeroSocio());
        }
        return dto;
    }
}
