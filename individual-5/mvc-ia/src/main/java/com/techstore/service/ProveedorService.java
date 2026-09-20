package com.techstore.service;

import com.techstore.dto.ProveedorDTO;
import com.techstore.mapper.ProveedorMapper;
import com.techstore.model.Proveedor;
import com.techstore.repository.ProveedorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * =============================================================================
 * CAPA SERVICIO: ProveedorService
 * =============================================================================
 * Lógica de negocio para administración de proveedores mayoristas asociados
 * a las órdenes de reposición de mercadería.
 */
@Service
@Transactional
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;
    private final ProveedorMapper proveedorMapper;

    public ProveedorService(ProveedorRepository proveedorRepository, ProveedorMapper proveedorMapper) {
        this.proveedorRepository = proveedorRepository;
        this.proveedorMapper = proveedorMapper;
    }

    @Transactional(readOnly = true)
    public List<ProveedorDTO> listarTodos() {
        return proveedorRepository.findByActivoTrueOrderByRazonSocialAsc().stream()
                .map(proveedorMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<ProveedorDTO> buscarPorId(Long id) {
        return proveedorRepository.findById(id).map(proveedorMapper::toDTO);
    }

    public ProveedorDTO guardar(ProveedorDTO dto) {
        Proveedor entity = proveedorMapper.toEntity(dto);
        Proveedor guardado = proveedorRepository.save(entity);
        return proveedorMapper.toDTO(guardado);
    }

    public void darDeBaja(Long id) {
        proveedorRepository.findById(id).ifPresent(p -> {
            p.setActivo(false);
            proveedorRepository.save(p);
        });
    }

    @Transactional(readOnly = true)
    public boolean existeCodigo(String codigo) {
        return proveedorRepository.existsByCodigo(codigo);
    }

    @Transactional(readOnly = true)
    public boolean existeCuit(String cuit) {
        return proveedorRepository.existsByCuit(cuit);
    }

    @Transactional(readOnly = true)
    public long contarProveedores() {
        return proveedorRepository.count();
    }
}

