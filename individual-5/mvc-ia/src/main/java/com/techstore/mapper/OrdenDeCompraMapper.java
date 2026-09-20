package com.techstore.mapper;

import com.techstore.dto.DetalleOrdenCompraDTO;
import com.techstore.dto.OrdenDeCompraDTO;
import com.techstore.model.DetalleOrdenCompra;
import com.techstore.model.OrdenDeCompra;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * =============================================================================
 * CAPA MAPPER: OrdenDeCompraMapper
 * =============================================================================
 * Transforma entre OrdenDeCompra (entidad de dominio con composición de detalles)
 * y OrdenDeCompraDTO (modelo para la vista).
 */
@Component
public class OrdenDeCompraMapper {

    public OrdenDeCompraDTO toDTO(OrdenDeCompra orden) {
        if (orden == null) return null;

        OrdenDeCompraDTO dto = new OrdenDeCompraDTO();
        dto.setId(orden.getId());
        dto.setNumero(orden.getNumero());
        dto.setFechaEmision(orden.getFechaEmision());
        dto.setFechaEntregaEstimada(orden.getFechaEntregaEstimada());
        dto.setEstado(orden.getEstado());
        dto.setTotal(orden.getTotal());

        if (orden.getProveedor() != null) {
            dto.setProveedorId(orden.getProveedor().getId());
            dto.setProveedorRazonSocial(orden.getProveedor().getRazonSocial());
        }

        if (orden.getEncargadoCompras() != null) {
            dto.setEncargadoComprasId(orden.getEncargadoCompras().getId());
            dto.setEncargadoComprasNombre(orden.getEncargadoCompras().getNombreCompleto());
        }

        if (orden.getDetalles() != null) {
            dto.setDetalles(orden.getDetalles().stream()
                    .map(this::toDetalleDTO)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public DetalleOrdenCompraDTO toDetalleDTO(DetalleOrdenCompra detalle) {
        if (detalle == null) return null;

        DetalleOrdenCompraDTO dto = new DetalleOrdenCompraDTO();
        dto.setId(detalle.getId());
        if (detalle.getProducto() != null) {
            dto.setProductoId(detalle.getProducto().getId());
            dto.setProductoCodigo(detalle.getProducto().getCodigo());
            dto.setProductoNombre(detalle.getProducto().getNombre());
            dto.setProductoTipo(detalle.getProducto().getTipoProducto());
        }
        dto.setCantidad(detalle.getCantidad());
        dto.setPrecioUnitario(detalle.getPrecioUnitario());
        dto.setSubtotal(detalle.getSubtotal());
        return dto;
    }
}

