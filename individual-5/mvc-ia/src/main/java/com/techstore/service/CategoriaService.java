package com.techstore.service;

import com.techstore.dto.CategoriaDTO;
import com.techstore.mapper.CategoriaMapper;
import com.techstore.model.Categoria;
import com.techstore.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * =============================================================================
 * CAPA SERVICIO: CategoriaService
 * =============================================================================
 * @Service: Indica que la clase contiene la lógica de negocio del dominio.
 * Actúa como intermediaria entre los Controladores (MVC) y la Persistencia (JPA).
 *
 * @Transactional: Delimita las fronteras transaccionales. Si ocurre un error no
 * controlado durante una operación de escritura, Hibernate realiza un Rollback.
 *
 * Todos los métodos reciben y retornan DTOs, asegurando el desacoplamiento.
 */
@Service
@Transactional
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    public CategoriaService(CategoriaRepository categoriaRepository, CategoriaMapper categoriaMapper) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaMapper = categoriaMapper;
    }

    @Transactional(readOnly = true)
    public List<CategoriaDTO> listarTodas() {
        return categoriaRepository.findAllByOrderByNombreAsc().stream()
                .map(categoriaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<CategoriaDTO> buscarPorId(Long id) {
        return categoriaRepository.findById(id).map(categoriaMapper::toDTO);
    }

    public CategoriaDTO guardar(CategoriaDTO dto) {
        Categoria entity = categoriaMapper.toEntity(dto);
        Categoria guardada = categoriaRepository.save(entity);
        return categoriaMapper.toDTO(guardada);
    }

    public void eliminar(Long id) {
        categoriaRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean existeCodigo(String codigo) {
        return categoriaRepository.existsByCodigo(codigo);
    }
}

