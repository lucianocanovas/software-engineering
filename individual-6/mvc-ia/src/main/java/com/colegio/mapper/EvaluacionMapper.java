package com.colegio.mapper;

import com.colegio.dto.EvaluacionDTO;
import com.colegio.model.Evaluacion;
import com.colegio.model.Materia;
import com.colegio.model.Profesor;
import org.springframework.stereotype.Component;

@Component
public class EvaluacionMapper {

    public Evaluacion toEntity(EvaluacionDTO dto, Materia materia, Profesor profesor) {
        if (dto == null) {
            return null;
        }
        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setTitulo(dto.getTitulo().trim());
        evaluacion.setTipo(dto.getTipo().trim());
        evaluacion.setFecha(dto.getFecha());
        evaluacion.setPonderacion(dto.getPonderacion());
        evaluacion.setMateria(materia);
        evaluacion.setProfesor(profesor);
        return evaluacion;
    }

    public EvaluacionDTO toDTO(Evaluacion entity) {
        if (entity == null) {
            return null;
        }
        EvaluacionDTO dto = new EvaluacionDTO();
        dto.setId(entity.getId());
        dto.setTitulo(entity.getTitulo());
        dto.setTipo(entity.getTipo());
        dto.setFecha(entity.getFecha());
        dto.setPonderacion(entity.getPonderacion());

        if (entity.getMateria() != null) {
            dto.setMateriaId(entity.getMateria().getId());
            dto.setMateriaNombre(entity.getMateria().getNombre());
        }

        if (entity.getProfesor() != null) {
            dto.setProfesorId(entity.getProfesor().getId());
            dto.setProfesorNombre(entity.getProfesor().getNombreCompleto());
        }

        if (entity.getNotas() != null) {
            dto.setCantidadNotas(entity.getNotas().size());
        }

        return dto;
    }
}

