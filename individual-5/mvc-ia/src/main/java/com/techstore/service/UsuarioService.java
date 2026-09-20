package com.techstore.service;

import com.techstore.dto.LoginDTO;
import com.techstore.dto.UsuarioDTO;
import com.techstore.mapper.UsuarioMapper;
import com.techstore.model.Usuario;
import com.techstore.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * =============================================================================
 * CAPA SERVICIO: UsuarioService
 * =============================================================================
 * Gestiona la autenticación, roles y usuarios del sistema tecnológico.
 */
@Service
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    /**
     * Valida las credenciales ingresadas en la pantalla de inicio de sesión.
     * Retorna el UsuarioDTO autenticado si las credenciales son válidas.
     */
    @Transactional(readOnly = true)
    public Optional<UsuarioDTO> autenticar(LoginDTO loginDTO) {
        if (loginDTO == null || loginDTO.getNombreUsuario() == null || loginDTO.getPassword() == null) {
            return Optional.empty();
        }

        return usuarioRepository.findByNombreUsuarioAndActivoTrue(loginDTO.getNombreUsuario().trim())
                .filter(u -> u.login(loginDTO.getPassword().trim()))
                .map(usuarioMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public Optional<UsuarioDTO> buscarPorNombreUsuario(String nombreUsuario) {
        return usuarioRepository.findByNombreUsuario(nombreUsuario).map(usuarioMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UsuarioDTO guardar(UsuarioDTO dto) {
        Usuario entity = usuarioMapper.toEntity(dto);
        Usuario guardado = usuarioRepository.save(entity);
        return usuarioMapper.toDTO(guardado);
    }

    @Transactional(readOnly = true)
    public boolean existeNombreUsuario(String nombreUsuario) {
        return usuarioRepository.existsByNombreUsuario(nombreUsuario);
    }
}

