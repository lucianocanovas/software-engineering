package com.techstore.mapper;

import com.techstore.dto.UsuarioDTO;
import com.techstore.model.Administrador;
import com.techstore.model.EncargadoCompras;
import com.techstore.model.Usuario;
import com.techstore.model.Vendedor;
import org.springframework.stereotype.Component;

/**
 * =============================================================================
 * CAPA MAPPER: UsuarioMapper
 * =============================================================================
 * Conversor polimórfico entre la jerarquía de herencia Usuario y UsuarioDTO.
 */
@Component
public class UsuarioMapper {

    public UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null) return null;

        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNombreUsuario(usuario.getNombreUsuario());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setEmail(usuario.getEmail());
        dto.setRol(usuario.getRol());
        dto.setActivo(usuario.getActivo());
        return dto;
    }

    public Usuario toEntity(UsuarioDTO dto) {
        if (dto == null) return null;

        Usuario usuario;
        String rol = dto.getRol() != null ? dto.getRol().toUpperCase() : "VENDEDOR";

        switch (rol) {
            case "ADMIN":
                usuario = new Administrador();
                break;
            case "COMPRAS":
                usuario = new EncargadoCompras();
                break;
            case "VENDEDOR":
            default:
                usuario = new Vendedor();
                break;
        }

        usuario.setId(dto.getId());
        usuario.setNombreUsuario(dto.getNombreUsuario());
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            usuario.setPassword(dto.getPassword());
        }
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setEmail(dto.getEmail());
        usuario.setActivo(dto.getActivo() != null ? dto.getActivo() : true);
        return usuario;
    }
}

