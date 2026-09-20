package com.techstore.mapper;

import com.techstore.dto.ProveedorDTO;
import com.techstore.model.Proveedor;
import org.springframework.stereotype.Component;

/**
 * =============================================================================
 * CAPA MAPPER: ProveedorMapper
 * =============================================================================
 * Conversor entre Proveedor y ProveedorDTO.
 */
@Component
public class ProveedorMapper {

    public ProveedorDTO toDTO(Proveedor proveedor) {
        if (proveedor == null) return null;

        ProveedorDTO dto = new ProveedorDTO();
        dto.setId(proveedor.getId());
        dto.setCodigo(proveedor.getCodigo());
        dto.setRazonSocial(proveedor.getRazonSocial());
        dto.setCuit(proveedor.getCuit());
        dto.setTelefono(proveedor.getTelefono());
        dto.setEmail(proveedor.getEmail());
        dto.setDireccion(proveedor.getDireccion());
        dto.setActivo(proveedor.getActivo());
        dto.setCantidadOrdenes(proveedor.getOrdenesCompra() != null ? proveedor.getOrdenesCompra().size() : 0);
        return dto;
    }

    public Proveedor toEntity(ProveedorDTO dto) {
        if (dto == null) return null;

        Proveedor p = new Proveedor();
        p.setId(dto.getId());
        p.setCodigo(dto.getCodigo());
        p.setRazonSocial(dto.getRazonSocial());
        p.setCuit(dto.getCuit());
        p.setTelefono(dto.getTelefono());
        p.setEmail(dto.getEmail());
        p.setDireccion(dto.getDireccion());
        p.setActivo(dto.getActivo() != null ? dto.getActivo() : true);
        return p;
    }
}

