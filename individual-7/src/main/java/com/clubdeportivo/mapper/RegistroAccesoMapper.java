package com.clubdeportivo.mapper;

import com.clubdeportivo.dto.response.RegistroAccesoResponseDTO;
import com.clubdeportivo.entity.FamiliarAdherente;
import com.clubdeportivo.entity.Persona;
import com.clubdeportivo.entity.Profesor;
import com.clubdeportivo.entity.RegistroAcceso;
import com.clubdeportivo.entity.Socio;
import org.springframework.stereotype.Component;

/**
 * =========================================================================================
 * MAPPER: RegistroAccesoMapper
 * =========================================================================================
 * Transforma eventos de paso físico a DTOs informativos con verificación de estado y foto.
 */
@Component
public class RegistroAccesoMapper {

    public RegistroAccesoResponseDTO toResponseDTO(RegistroAcceso r) {
        if (r == null) {
            return null;
        }
        RegistroAccesoResponseDTO dto = new RegistroAccesoResponseDTO();
        dto.setId(r.getId());
        dto.setFechaHoraEntrada(r.getFechaHoraEntrada());
        dto.setFechaHoraSalida(r.getFechaHoraSalida());
        dto.setTipoMovimiento(r.getTipoMovimiento());
        dto.setObservacion(r.getObservacion());
        dto.setPermanenciaMinutos(r.calcularPermanenciaMinutos());

        if (r.getPuntoDeAcceso() != null) {
            dto.setPuntoDeAccesoId(r.getPuntoDeAcceso().getId());
            dto.setPuntoDeAccesoNombre(r.getPuntoDeAcceso().getNombre());
            dto.setPuntoDeAccesoUbicacion(r.getPuntoDeAcceso().getUbicacion());
        }

        if (r.getPersona() != null) {
            Persona p = r.getPersona();
            dto.setPersonaId(p.getId());
            dto.setPersonaNombreCompleto(p.obtenerNombreCompleto());
            dto.setPersonaDni(p.getDni());

            if (p.getFotografia() != null) {
                dto.setRutaFoto(p.getFotografia().getRutaArchivo());
            }

            if (p instanceof Socio socio) {
                dto.setTipoPersona("Socio Titular (#" + socio.getNumeroSocio() + ")");
                dto.setHabilitado(socio.estaAlDia());
                if (!socio.estaAlDia()) {
                    dto.setMotivoBloqueo("Cuota impaga / Socio moroso");
                }
            } else if (p instanceof FamiliarAdherente adherente) {
                dto.setTipoPersona("Familiar Adherente (" + adherente.getParentesco().getDescripcion() + ")");
                boolean titularAlDia = adherente.getGrupoFamiliar() != null &&
                                       adherente.getGrupoFamiliar().getTitular() != null &&
                                       adherente.getGrupoFamiliar().getTitular().estaAlDia();
                dto.setHabilitado(titularAlDia);
                if (!titularAlDia) {
                    dto.setMotivoBloqueo("Cuota del grupo familiar impaga");
                }
            } else if (p instanceof Profesor prof) {
                dto.setTipoPersona("Profesor (" + prof.getEspecialidad() + ")");
                dto.setHabilitado(Boolean.TRUE.equals(prof.getActivo()));
            } else {
                dto.setTipoPersona("Empleado de Planta");
                dto.setHabilitado(Boolean.TRUE.equals(p.getActivo()));
            }
        }
        return dto;
    }
}

