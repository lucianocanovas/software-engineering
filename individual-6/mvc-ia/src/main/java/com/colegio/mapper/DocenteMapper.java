package com.colegio.mapper;

import com.colegio.dto.DocenteDTO;
import com.colegio.dto.DocenteRegistroDTO;
import com.colegio.model.Aula;
import com.colegio.model.Materia;
import com.colegio.model.Profesor;
import com.colegio.model.Rol;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * =========================================================================================
 * CAPA DE MAPEO: DocenteMapper
 * =========================================================================================
 * Responsable de transformar entre las Entidades de Persistencia (Profesor) y los Objetos
 * de Transferencia de Datos (DocenteRegistroDTO, DocenteDTO).
 *
 * Garantiza el desacoplamiento estricto requerido:
 * "La información que pasa entre las capas deberá utilizar DTO."
 */
@Component
public class DocenteMapper {

    /**
     * Convierte el DTO de registro en una nueva entidad de persistencia Profesor.
     */
    public Profesor toEntity(DocenteRegistroDTO dto, String encodedPassword) {
        if (dto == null) {
            return null;
        }
        Profesor profesor = new Profesor();
        profesor.setNombre(dto.getNombre().trim());
        profesor.setApellido(dto.getApellido().trim());
        profesor.setEmail(dto.getEmail().trim().toLowerCase());
        profesor.setSexo(dto.getSexo());
        profesor.setFechaNacimiento(dto.getFechaNacimiento());
        profesor.setDni(dto.getDni().trim());
        profesor.setLegajo(dto.getLegajo());
        profesor.setEspecialidad(dto.getEspecialidad().trim());
        profesor.setSalario(dto.getSalario());
        profesor.setPassword(encodedPassword);
        profesor.setRol(Rol.ROLE_DOCENTE);
        return profesor;
    }

    /**
     * Transforma una entidad persistida Profesor en un DTO para consumo de la vista.
     */
    public DocenteDTO toDTO(Profesor entity) {
        if (entity == null) {
            return null;
        }
        DocenteDTO dto = new DocenteDTO();
        dto.setId(entity.getId());
        dto.setDni(entity.getDni());
        dto.setNombre(entity.getNombre());
        dto.setApellido(entity.getApellido());
        dto.setEmail(entity.getEmail());
        dto.setSexo(entity.getSexo());
        dto.setFechaNacimiento(entity.getFechaNacimiento());
        dto.setLegajo(entity.getLegajo());
        dto.setEspecialidad(entity.getEspecialidad());
        dto.setSalario(entity.getSalario());
        dto.setNombreCompleto(entity.getNombreCompleto());

        if (entity.getMaterias() != null) {
            dto.setMateriasNombres(entity.getMaterias().stream()
                    .map(Materia::getNombre)
                    .collect(Collectors.toList()));
        }

        if (entity.getAulas() != null) {
            dto.setAulasNombres(entity.getAulas().stream()
                    .map(Aula::getNombreCompleto)
                    .collect(Collectors.toList()));
        }

        // Datos de auditoría
        dto.setFechaCreacion(entity.getFechaCreacion());
        dto.setFechaModificacion(entity.getFechaModificacion());
        dto.setCreadoPor(entity.getCreadoPor());
        dto.setModificadoPor(entity.getModificadoPor());

        return dto;
    }
}

