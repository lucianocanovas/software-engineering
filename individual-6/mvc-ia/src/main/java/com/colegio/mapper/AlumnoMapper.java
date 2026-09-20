package com.colegio.mapper;

import com.colegio.dto.AlumnoDTO;
import com.colegio.model.Alumno;
import com.colegio.model.Aula;
import com.colegio.model.Rol;
import org.springframework.stereotype.Component;

/**
 * Mapper para la transformación entre la entidad Alumno y AlumnoDTO.
 */
@Component
public class AlumnoMapper {

    public Alumno toEntity(AlumnoDTO dto, Aula aula, String defaultPassword) {
        if (dto == null) {
            return null;
        }
        Alumno alumno = new Alumno();
        alumno.setDni(dto.getDni().trim());
        alumno.setNombre(dto.getNombre().trim());
        alumno.setApellido(dto.getApellido().trim());
        alumno.setEmail(dto.getEmail().trim().toLowerCase());
        alumno.setSexo(dto.getSexo());
        alumno.setFechaNacimiento(dto.getFechaNacimiento());
        alumno.setCodigo(dto.getCodigo());
        alumno.setFechaIngreso(dto.getFechaIngreso());
        alumno.setAula(aula);
        alumno.setPassword(defaultPassword);
        alumno.setRol(Rol.ROLE_ALUMNO);
        return alumno;
    }

    public AlumnoDTO toDTO(Alumno entity) {
        if (entity == null) {
            return null;
        }
        AlumnoDTO dto = new AlumnoDTO();
        dto.setId(entity.getId());
        dto.setDni(entity.getDni());
        dto.setNombre(entity.getNombre());
        dto.setApellido(entity.getApellido());
        dto.setEmail(entity.getEmail());
        dto.setSexo(entity.getSexo());
        dto.setFechaNacimiento(entity.getFechaNacimiento());
        dto.setCodigo(entity.getCodigo());
        dto.setFechaIngreso(entity.getFechaIngreso());

        if (entity.getAula() != null) {
            dto.setAulaId(entity.getAula().getId());
            dto.setAulaNombre(entity.getAula().getNombreCompleto());
            if (entity.getAula().getGrado() != null) {
                dto.setGradoNombre(entity.getAula().getGrado().getNombreCompleto());
            }
        }

        dto.setPromedio(entity.calcularPromedio());
        return dto;
    }
}

