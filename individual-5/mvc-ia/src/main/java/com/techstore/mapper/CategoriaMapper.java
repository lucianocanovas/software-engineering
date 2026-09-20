package com.techstore.mapper;

import com.techstore.dto.CategoriaDTO;
import com.techstore.model.Categoria;
import org.springframework.stereotype.Component;

/**
 * =============================================================================
 * CAPA MAPPER: CategoriaMapper
 * =============================================================================
 * Conversor bidireccional entre la entidad Categoria (ORM) y el DTO CategoriaDTO.
 * Aísla el modelo de base de datos de la vista Thymeleaf.
 */
@Component
public class CategoriaMapper {

    public CategoriaDTO toDTO(Categoria categoria) {
        if (categoria == null) return null;
        return new CategoriaDTO(
                categoria.getId(),
                categoria.getCodigo(),
                categoria.getNombre(),
                categoria.getDescripcion(),
                categoria.getCantidadProductos()
        );
    }

    public Categoria toEntity(CategoriaDTO dto) {
        if (dto == null) return null;
        Categoria categoria = new Categoria();
        categoria.setId(dto.getId());
        categoria.setCodigo(dto.getCodigo());
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());
        return categoria;
    }
}

