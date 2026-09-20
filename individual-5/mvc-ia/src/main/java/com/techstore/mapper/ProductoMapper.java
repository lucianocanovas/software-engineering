package com.techstore.mapper;

import com.techstore.dto.ProductoDTO;
import com.techstore.model.*;
import org.springframework.stereotype.Component;

/**
 * =============================================================================
 * CAPA MAPPER: ProductoMapper
 * =============================================================================
 * Conversor polimórfico que transforma entre la jerarquía de herencia Producto
 * (ProductoFisico / ProductoDigital) y el objeto de transferencia ProductoDTO.
 *
 * Invoca el método polimórfico calcularPrecioFinal() definido en el dominio.
 */
@Component
public class ProductoMapper {

    public ProductoDTO toDTO(Producto producto) {
        if (producto == null) return null;

        ProductoDTO dto = new ProductoDTO();
        dto.setId(producto.getId());
        dto.setCodigo(producto.getCodigo());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecioVenta(producto.getPrecioVenta());
        dto.setPrecioFinal(producto.calcularPrecioFinal());
        dto.setActivo(producto.getActivo());

        if (producto.getCategoria() != null) {
            dto.setCategoriaId(producto.getCategoria().getId());
            dto.setCategoriaNombre(producto.getCategoria().getNombre());
        }

        if (producto instanceof ProductoFisico) {
            ProductoFisico fisico = (ProductoFisico) producto;
            dto.setTipoProducto("FISICO");
            dto.setStock(fisico.getStock());
            dto.setStockMinimo(fisico.getStockMinimo());
            dto.setPeso(fisico.getPeso());
            dto.setUbicacionDeposito(fisico.getUbicacionDeposito());
            dto.setBajoStock(fisico.isBajoStock());
        } else if (producto instanceof ProductoDigital) {
            ProductoDigital digital = (ProductoDigital) producto;
            dto.setTipoProducto("DIGITAL");
            dto.setClaveLicencia(digital.getClaveLicencia());
            dto.setUrlDescarga(digital.getUrlDescarga());
            dto.setVigenciaDias(digital.getVigenciaDias());
            dto.setBajoStock(false);
        }

        return dto;
    }

    public Producto toEntity(ProductoDTO dto, Categoria categoria) {
        if (dto == null) return null;

        if ("DIGITAL".equalsIgnoreCase(dto.getTipoProducto())) {
            ProductoDigital digital = new ProductoDigital();
            digital.setId(dto.getId());
            digital.setCodigo(dto.getCodigo());
            digital.setNombre(dto.getNombre());
            digital.setDescripcion(dto.getDescripcion());
            digital.setPrecioVenta(dto.getPrecioVenta());
            digital.setActivo(dto.getActivo() != null ? dto.getActivo() : true);
            digital.setCategoria(categoria);
            digital.setClaveLicencia(dto.getClaveLicencia() != null && !dto.getClaveLicencia().isBlank()
                    ? dto.getClaveLicencia() : digital.generarClaveLicencia());
            digital.setUrlDescarga(dto.getUrlDescarga());
            digital.setVigenciaDias(dto.getVigenciaDias() != null ? dto.getVigenciaDias() : 365);
            return digital;
        } else {
            // Por defecto Producto Físico
            ProductoFisico fisico = new ProductoFisico();
            fisico.setId(dto.getId());
            fisico.setCodigo(dto.getCodigo());
            fisico.setNombre(dto.getNombre());
            fisico.setDescripcion(dto.getDescripcion());
            fisico.setPrecioVenta(dto.getPrecioVenta());
            fisico.setActivo(dto.getActivo() != null ? dto.getActivo() : true);
            fisico.setCategoria(categoria);
            fisico.setStock(dto.getStock() != null ? dto.getStock() : 0);
            fisico.setStockMinimo(dto.getStockMinimo() != null ? dto.getStockMinimo() : 5);
            fisico.setPeso(dto.getPeso() != null ? dto.getPeso() : 0.0);
            fisico.setUbicacionDeposito(dto.getUbicacionDeposito());
            return fisico;
        }
    }
}

