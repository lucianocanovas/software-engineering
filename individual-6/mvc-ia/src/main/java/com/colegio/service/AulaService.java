package com.colegio.service;

import com.colegio.dto.AulaDTO;
import com.colegio.dto.GradoDTO;
import com.colegio.mapper.AulaMapper;
import com.colegio.mapper.GradoMapper;
import com.colegio.repository.AulaRepository;
import com.colegio.repository.GradoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AulaService {

    private final AulaRepository aulaRepository;
    private final GradoRepository gradoRepository;
    private final AulaMapper aulaMapper;
    private final GradoMapper gradoMapper;

    public AulaService(AulaRepository aulaRepository,
                       GradoRepository gradoRepository,
                       AulaMapper aulaMapper,
                       GradoMapper gradoMapper) {
        this.aulaRepository = aulaRepository;
        this.gradoRepository = gradoRepository;
        this.aulaMapper = aulaMapper;
        this.gradoMapper = gradoMapper;
    }

    @Transactional(readOnly = true)
    public List<AulaDTO> listarTodas() {
        return aulaRepository.findAll().stream()
                .map(aulaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GradoDTO> listarGrados() {
        return gradoRepository.findAll().stream()
                .map(gradoMapper::toDTO)
                .collect(Collectors.toList());
    }
}

