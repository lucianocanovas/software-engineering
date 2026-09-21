package com.clubdeportivo.mapper;

import com.clubdeportivo.dto.request.FamiliarAdherenteRequestDTO;
import com.clubdeportivo.dto.response.FamiliarAdherenteResponseDTO;
import com.clubdeportivo.entity.FamiliarAdherente;
import com.clubdeportivo.entity.Fotografia;
import org.springframework.stereotype.Component;

/**
 * =========================================================================================
 * MAPPER: FamiliarAdherenteMapper
 * =========================================================================================
 * Conversión entre entidades FamiliarAdherente y sus DTOs correspondientes.
 */
@Component
public class FamiliarAdherenteMapper {

    public FamiliarAdherente toEntity(FamiliarAdherenteRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        FamiliarAdherente adherente = new FamiliarAdherente(
            dto.getDni(),
            dto.getNombre(),
            dto.getApellido(),
            dto.getFechaNacimiento(),
            dto.getTelefono(),
            dto.getEmail(),
            dto.getParentesco()
        );
        adherente.setId(dto.getId());

        if (dto.getFotoData() != null && !dto.getFotoData().trim().isEmpty()) {
            Fotografia foto = new Fotografia(dto.getFotoData(), "image/jpeg", (long) dto.getFotoData().length());
            adherente.setFotografia(foto);
        }
        return adherente;
    }

    public FamiliarAdherenteResponseDTO toResponseDTO(FamiliarAdherente a) {
        if (a == null) {
            return null;
        }
        FamiliarAdherenteResponseDTO dto = new FamiliarAdherenteResponseDTO();
        dto.setId(a.getId());
        dto.setDni(a.getDni());
        dto.setNombre(a.getNombre());
        dto.setApellido(a.getApellido());
        dto.setNombreCompleto(a.obtenerNombreCompleto());
        dto.setEdad(a.calcularEdad());
        dto.setFechaNacimiento(a.getFechaNacimiento());
        dto.setMenorDeEdad(a.esMenorDeEdad());
        dto.setTelefono(a.getTelefono());
        dto.setEmail(a.getEmail());
        dto.setParentesco(a.getParentesco());

        if (a.getFotografia() != null) {
            dto.setRutaFoto(a.getFotografia().getRutaArchivo());
        }

        if (a.getGrupoFamiliar() != null) {
            dto.setGrupoFamiliarId(a.getGrupoFamiliar().getId());
            dto.setGrupoFamiliarCodigo(a.getGrupoFamiliar().getCodigo());
            if (a.getGrupoFamiliar().getTitular() != null) {
                dto.setTitularNombre(a.getGrupoFamiliar().getTitular().obtenerNombreCompleto());
            }
        }
        return dto;
    }
}
