package com.clubdeportivo.mapper;

import com.clubdeportivo.dto.request.SocioRequestDTO;
import com.clubdeportivo.dto.response.SocioResponseDTO;
import com.clubdeportivo.entity.Fotografia;
import com.clubdeportivo.entity.Socio;
import org.springframework.stereotype.Component;

/**
 * =========================================================================================
 * MAPPER: SocioMapper
 * =========================================================================================
 * Desacopla la entidad JPA Socio de sus DTOs de entrada y salida:
 * - Evita fugas de abstracción de Hibernate hacia la capa web.
 * - Mapea de forma bidireccional atributos de Persona y Socio.
 * - Gestiona la asignación de Fotografia biométrica.
 */
@Component
public class SocioMapper {

    public Socio toEntity(SocioRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Socio socio = new Socio(
            dto.getDni(),
            dto.getNombre(),
            dto.getApellido(),
            dto.getFechaNacimiento(),
            dto.getTelefono(),
            dto.getEmail(),
            dto.getNumeroSocio(),
            dto.getCategoria()
        );
        socio.setId(dto.getId());

        if (dto.getFotoData() != null && !dto.getFotoData().trim().isEmpty()) {
            Fotografia foto = new Fotografia(dto.getFotoData(), "image/jpeg", (long) dto.getFotoData().length());
            socio.setFotografia(foto);
        }
        return socio;
    }

    public void updateEntityFromDTO(SocioRequestDTO dto, Socio socio) {
        if (dto == null || socio == null) {
            return;
        }
        socio.setDni(dto.getDni());
        socio.setNombre(dto.getNombre());
        socio.setApellido(dto.getApellido());
        socio.setFechaNacimiento(dto.getFechaNacimiento());
        socio.setTelefono(dto.getTelefono());
        socio.setEmail(dto.getEmail());
        socio.setNumeroSocio(dto.getNumeroSocio());
        socio.setCategoria(dto.getCategoria());

        if (dto.getFotoData() != null && !dto.getFotoData().trim().isEmpty()) {
            if (socio.getFotografia() == null) {
                socio.setFotografia(new Fotografia(dto.getFotoData(), "image/jpeg", (long) dto.getFotoData().length()));
            } else {
                socio.getFotografia().setRutaArchivo(dto.getFotoData());
            }
        }
    }

    public SocioResponseDTO toResponseDTO(Socio socio) {
        if (socio == null) {
            return null;
        }
        SocioResponseDTO dto = new SocioResponseDTO();
        dto.setId(socio.getId());
        dto.setDni(socio.getDni());
        dto.setNombre(socio.getNombre());
        dto.setApellido(socio.getApellido());
        dto.setNombreCompleto(socio.obtenerNombreCompleto());
        dto.setEdad(socio.calcularEdad());
        dto.setFechaNacimiento(socio.getFechaNacimiento());
        dto.setTelefono(socio.getTelefono());
        dto.setEmail(socio.getEmail());
        dto.setActivo(socio.getActivo());
        dto.setNumeroSocio(socio.getNumeroSocio());
        dto.setCategoria(socio.getCategoria());
        dto.setFechaAlta(socio.getFechaAlta());
        dto.setAlDia(socio.estaAlDia());

        if (socio.getFotografia() != null) {
            dto.setRutaFoto(socio.getFotografia().getRutaArchivo());
        }

        if (socio.getGrupoFamiliar() != null) {
            dto.setCodigoGrupoFamiliar(socio.getGrupoFamiliar().getCodigo());
            dto.setGrupoFamiliarId(socio.getGrupoFamiliar().getId());
            dto.setCantidadAdherentes(
                socio.getGrupoFamiliar().getIntegrantes() != null ?
                socio.getGrupoFamiliar().getIntegrantes().size() : 0
            );
        }
        return dto;
    }

    public SocioRequestDTO toRequestDTO(Socio socio) {
        if (socio == null) {
            return null;
        }
        SocioRequestDTO dto = new SocioRequestDTO();
        dto.setId(socio.getId());
        dto.setDni(socio.getDni());
        dto.setNombre(socio.getNombre());
        dto.setApellido(socio.getApellido());
        dto.setFechaNacimiento(socio.getFechaNacimiento());
        dto.setTelefono(socio.getTelefono());
        dto.setEmail(socio.getEmail());
        dto.setNumeroSocio(socio.getNumeroSocio());
        dto.setCategoria(socio.getCategoria());
        if (socio.getFotografia() != null) {
            dto.setFotoData(socio.getFotografia().getRutaArchivo());
        }
        return dto;
    }
}

