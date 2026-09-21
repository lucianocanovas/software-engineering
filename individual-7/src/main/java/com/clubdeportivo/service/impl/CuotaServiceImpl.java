package com.clubdeportivo.service.impl;

import com.clubdeportivo.dto.request.CuotaEmisionRequestDTO;
import com.clubdeportivo.dto.response.CuotaResponseDTO;
import com.clubdeportivo.entity.Cuota;
import com.clubdeportivo.entity.GrupoFamiliar;
import com.clubdeportivo.entity.Socio;
import com.clubdeportivo.entity.enums.EstadoCuota;
import com.clubdeportivo.mapper.CuotaMapper;
import com.clubdeportivo.repository.CuotaRepository;
import com.clubdeportivo.repository.SocioRepository;
import com.clubdeportivo.service.CuotaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * =========================================================================================
 * SERVICIO IMPL: CuotaServiceImpl
 * =========================================================================================
 * Emisión y supervisión de cuotas sociales para familias y socios titulares.
 */
@Service
@Transactional
public class CuotaServiceImpl implements CuotaService {

    private final CuotaRepository cuotaRepository;
    private final SocioRepository socioRepository;
    private final CuotaMapper cuotaMapper;

    public CuotaServiceImpl(CuotaRepository cuotaRepository,
                            SocioRepository socioRepository,
                            CuotaMapper cuotaMapper) {
        this.cuotaRepository = cuotaRepository;
        this.socioRepository = socioRepository;
        this.cuotaMapper = cuotaMapper;
    }

    @Override
    public CuotaResponseDTO emitirCuota(CuotaEmisionRequestDTO dto) {
        Socio socio = socioRepository.findById(dto.getSocioTitularId())
                .orElseThrow(() -> new IllegalArgumentException("Socio titular no encontrado con ID: " + dto.getSocioTitularId()));

        if (cuotaRepository.findBySocioTitularIdAndPeriodo(socio.getId(), dto.getPeriodo()).isPresent()) {
            throw new IllegalArgumentException("Ya existe una cuota emitida para el socio " + socio.obtenerNombreCompleto() + " en el periodo " + dto.getPeriodo());
        }

        GrupoFamiliar grupo = socio.getGrupoFamiliar();
        Cuota cuota = new Cuota(
                dto.getPeriodo(),
                dto.getMontoBase(),
                dto.getMontoRecargo(),
                dto.getFechaVencimiento(),
                socio,
                grupo
        );

        Cuota guardada = cuotaRepository.save(cuota);
        return cuotaMapper.toResponseDTO(guardada);
    }

    @Override
    @Transactional(readOnly = true)
    public CuotaResponseDTO obtenerCuotaPorId(Long id) {
        return cuotaRepository.findById(id)
                .map(cuotaMapper::toResponseDTO)
                .orElseThrow(() -> new IllegalArgumentException("Cuota no encontrada con ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CuotaResponseDTO> obtenerCuotasPorSocio(Long socioId) {
        return cuotaRepository.findBySocioTitularIdOrderByFechaVencimientoDesc(socioId).stream()
                .map(cuotaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CuotaResponseDTO> obtenerCuotasPorGrupoFamiliar(Long grupoId) {
        return cuotaRepository.findByGrupoFamiliarIdOrderByFechaVencimientoDesc(grupoId).stream()
                .map(cuotaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CuotaResponseDTO> obtenerTodasLasCuotas() {
        return cuotaRepository.findAll().stream()
                .map(cuotaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CuotaResponseDTO> obtenerCuotasImpagas(Long socioId) {
        List<EstadoCuota> impagos = List.of(EstadoCuota.PENDIENTE, EstadoCuota.VENCIDA);
        return cuotaRepository.findCuotasImpagas(socioId, impagos).stream()
                .map(cuotaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void actualizarEstadosVencimiento() {
        List<Cuota> pendientes = cuotaRepository.findByEstado(EstadoCuota.PENDIENTE);
        for (Cuota c : pendientes) {
            if (c.estaVencida()) {
                c.setEstado(EstadoCuota.VENCIDA);
                cuotaRepository.save(c);
            }
        }
    }
}

