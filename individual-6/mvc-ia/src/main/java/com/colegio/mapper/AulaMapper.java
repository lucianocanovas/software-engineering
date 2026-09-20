package com.colegio.mapper;

import com.colegio.dto.AulaDTO;
import com.colegio.model.Aula;
import org.springframework.stereotype.Component;

@Component
public class AulaMapper {

    public AulaDTO toDTO(Aula entity) {
        if (entity == null) {
            return null;
        }
        AulaDTO dto = new AulaDTO();
        dto.setId(entity.getId());
        dto.setDivision(entity.getDivision());
        dto.setTurno(entity.getTurno());
        dto.setCapacidad(entity.getCapacidad());

        if (entity.getGrado() != null) {
            dto.setGradoId(entity.getGrado().getId());
            dto.setGradoNombre(entity.getGrado().getNombreCompleto());
        }

        if (entity.getAlumnos() != null) {
            dto.setCantidadAlumnos(entity.getAlumnos().size());
        }

        return dto;
    }
}

