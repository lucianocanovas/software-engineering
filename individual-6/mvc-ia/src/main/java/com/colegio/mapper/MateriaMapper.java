package com.colegio.mapper;

import com.colegio.dto.MateriaDTO;
import com.colegio.model.Grado;
import com.colegio.model.Materia;
import com.colegio.model.Profesor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MateriaMapper {

    public Materia toEntity(MateriaDTO dto, Grado grado) {
        if (dto == null) {
            return null;
        }
        Materia materia = new Materia();
        materia.setNombre(dto.getNombre().trim());
        materia.setHoras(dto.getHoras());
        materia.setDescripcion(dto.getDescripcion());
        materia.setGrado(grado);
        return materia;
    }

    public MateriaDTO toDTO(Materia entity) {
        if (entity == null) {
            return null;
        }
        MateriaDTO dto = new MateriaDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setHoras(entity.getHoras());
        dto.setDescripcion(entity.getDescripcion());

        if (entity.getGrado() != null) {
            dto.setGradoId(entity.getGrado().getId());
            dto.setGradoNombre(entity.getGrado().getNombreCompleto());
        }

        if (entity.getProfesores() != null) {
            dto.setDocentesNombres(entity.getProfesores().stream()
                    .map(Profesor::getNombreCompleto)
                    .collect(Collectors.toList()));
        }

        if (entity.getEvaluaciones() != null) {
            dto.setCantidadEvaluaciones(entity.getEvaluaciones().size());
        }

        return dto;
    }
}

