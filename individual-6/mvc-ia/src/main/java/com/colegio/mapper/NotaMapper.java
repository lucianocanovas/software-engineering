package com.colegio.mapper;

import com.colegio.dto.NotaDTO;
import com.colegio.model.Alumno;
import com.colegio.model.Evaluacion;
import com.colegio.model.Nota;
import org.springframework.stereotype.Component;

@Component
public class NotaMapper {

    public Nota toEntity(NotaDTO dto, Evaluacion evaluacion, Alumno alumno) {
        if (dto == null) {
            return null;
        }
        Nota nota = new Nota();
        nota.setPuntaje(dto.getPuntaje());
        nota.setFecha(dto.getFecha());
        nota.setObservaciones(dto.getObservaciones());
        nota.setEvaluacion(evaluacion);
        nota.setAlumno(alumno);
        return nota;
    }

    public NotaDTO toDTO(Nota entity) {
        if (entity == null) {
            return null;
        }
        NotaDTO dto = new NotaDTO();
        dto.setId(entity.getId());
        dto.setPuntaje(entity.getPuntaje());
        dto.setFecha(entity.getFecha());
        dto.setObservaciones(entity.getObservaciones());
        dto.setAprobada(entity.isAprobada());

        if (entity.getEvaluacion() != null) {
            dto.setEvaluacionId(entity.getEvaluacion().getId());
            dto.setEvaluacionTitulo(entity.getEvaluacion().getTitulo());
            if (entity.getEvaluacion().getMateria() != null) {
                dto.setMateriaNombre(entity.getEvaluacion().getMateria().getNombre());
            }
        }

        if (entity.getAlumno() != null) {
            dto.setAlumnoId(entity.getAlumno().getId());
            dto.setAlumnoNombre(entity.getAlumno().getNombreCompleto());
            dto.setAlumnoCodigo(entity.getAlumno().getCodigo());
        }

        return dto;
    }
}

