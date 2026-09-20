package com.colegio.mapper;

import com.colegio.dto.GradoDTO;
import com.colegio.model.Grado;
import org.springframework.stereotype.Component;

@Component
public class GradoMapper {

    public GradoDTO toDTO(Grado entity) {
        if (entity == null) {
            return null;
        }
        GradoDTO dto = new GradoDTO();
        dto.setId(entity.getId());
        dto.setNivel(entity.getNivel());
        dto.setAnio(entity.getAnio());

        if (entity.getColegio() != null) {
            dto.setColegioId(entity.getColegio().getId());
            dto.setColegioNombre(entity.getColegio().getNombre());
        }

        return dto;
    }
}

