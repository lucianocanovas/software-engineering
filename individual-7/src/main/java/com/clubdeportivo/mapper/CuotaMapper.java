package com.clubdeportivo.mapper;

import com.clubdeportivo.dto.response.CuotaResponseDTO;
import com.clubdeportivo.entity.Cuota;
import org.springframework.stereotype.Component;

/**
 * =========================================================================================
 * MAPPER: CuotaMapper
 * =========================================================================================
 * Proyecta la entidad Cuota a CuotaResponseDTO calculando totales y estados vencidos.
 */
@Component
public class CuotaMapper {

    public CuotaResponseDTO toResponseDTO(Cuota c) {
        if (c == null) {
            return null;
        }
        CuotaResponseDTO dto = new CuotaResponseDTO();
        dto.setId(c.getId());
        dto.setPeriodo(c.getPeriodo());
        dto.setMontoBase(c.getMontoBase());
        dto.setMontoRecargo(c.getMontoRecargo());
        dto.setTotalExigible(c.calcularTotal());
        dto.setFechaVencimiento(c.getFechaVencimiento());
        dto.setFechaPago(c.getFechaPago());
        dto.setEstado(c.getEstado());
        dto.setVencida(c.estaVencida());

        if (c.getSocioTitular() != null) {
            dto.setSocioTitularId(c.getSocioTitular().getId());
            dto.setSocioTitularNombre(c.getSocioTitular().obtenerNombreCompleto());
            dto.setSocioTitularNumero(c.getSocioTitular().getNumeroSocio());
        }

        if (c.getGrupoFamiliar() != null) {
            dto.setGrupoFamiliarId(c.getGrupoFamiliar().getId());
            dto.setGrupoFamiliarCodigo(c.getGrupoFamiliar().getCodigo());
        }

        if (c.getPago() != null) {
            dto.setPagoId(c.getPago().getId());
            dto.setNumeroRecibo(c.getPago().getNumeroRecibo());
        }
        return dto;
    }
}
