package com.colegio.service;

import com.colegio.dto.MateriaDTO;
import com.colegio.mapper.MateriaMapper;
import com.colegio.model.Grado;
import com.colegio.model.Materia;
import com.colegio.repository.GradoRepository;
import com.colegio.repository.MateriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Servicio para gestión de Materias curriculares.
 */
@Service
public class MateriaService {

    private final MateriaRepository materiaRepository;
    private final GradoRepository gradoRepository;
    private final MateriaMapper materiaMapper;

    public MateriaService(MateriaRepository materiaRepository,
                          GradoRepository gradoRepository,
                          MateriaMapper materiaMapper) {
        this.materiaRepository = materiaRepository;
        this.gradoRepository = gradoRepository;
        this.materiaMapper = materiaMapper;
    }

    @Transactional
    public MateriaDTO registrarMateria(MateriaDTO dto) {
        Grado grado = null;
        if (dto.getGradoId() != null) {
            grado = gradoRepository.findById(dto.getGradoId())
                    .orElseThrow(() -> new IllegalArgumentException("Grado no encontrado"));
        }
        Materia materia = materiaMapper.toEntity(dto, grado);
        Materia guardada = materiaRepository.save(materia);
        return materiaMapper.toDTO(guardada);
    }

    @Transactional(readOnly = true)
    public List<MateriaDTO> listarTodas() {
        return materiaRepository.findAll().stream()
                .map(materiaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MateriaDTO buscarPorId(UUID id) {
        return materiaRepository.findById(id)
                .map(materiaMapper::toDTO)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public long contarMaterias() {
        return materiaRepository.count();
    }
}

