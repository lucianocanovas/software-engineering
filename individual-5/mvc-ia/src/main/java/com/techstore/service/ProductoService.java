package com.techstore.service;

import com.techstore.dto.ProductoDTO;
import com.techstore.mapper.ProductoMapper;
import com.techstore.model.Categoria;
import com.techstore.model.Producto;
import com.techstore.model.ProductoDigital;
import com.techstore.model.ProductoFisico;
import com.techstore.repository.CategoriaRepository;
import com.techstore.repository.ProductoDigitalRepository;
import com.techstore.repository.ProductoFisicoRepository;
import com.techstore.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * =============================================================================
 * CAPA SERVICIO: ProductoService
 * =============================================================================
 * Coordina la lógica de negocio del ABM (Alta, Baja, Modificación) de productos
 * de tecnología y la gestión de precios polimórficos e inventario.
 *
 * CONCEPTOS IMPLEMENTADOS:
 * - DTO: Recibe y envía ProductoDTO hacia la capa web / controladores.
 * - HERENCIA: Procesa polimórficamente ProductoFisico y ProductoDigital.
 * - AGREGACIÓN: Asocia el producto con su correspondiente Categoria.
 */
@Service
@Transactional
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoFisicoRepository productoFisicoRepository;
    private final ProductoDigitalRepository productoDigitalRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProductoMapper productoMapper;

    public ProductoService(ProductoRepository productoRepository,
                           ProductoFisicoRepository productoFisicoRepository,
                           ProductoDigitalRepository productoDigitalRepository,
                           CategoriaRepository categoriaRepository,
                           ProductoMapper productoMapper) {
        this.productoRepository = productoRepository;
        this.productoFisicoRepository = productoFisicoRepository;
        this.productoDigitalRepository = productoDigitalRepository;
        this.categoriaRepository = categoriaRepository;
        this.productoMapper = productoMapper;
    }

    @Transactional(readOnly = true)
    public List<ProductoDTO> listarTodos() {
        return productoRepository.findByActivoTrue().stream()
                .map(productoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductoDTO> buscarPorFiltro(String filtro) {
        if (filtro == null || filtro.trim().isEmpty()) {
            return listarTodos();
        }
        return productoRepository.buscarPorNombreOCodigo(filtro.trim()).stream()
                .map(productoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductoDTO> filtrarPorCategoria(Long categoriaId) {
        return productoRepository.findByCategoriaId(categoriaId).stream()
                .map(productoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<ProductoDTO> buscarPorId(Long id) {
        return productoRepository.findById(id).map(productoMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public List<ProductoDTO> listarBajoStock() {
        return productoFisicoRepository.findProductosConBajoStock().stream()
                .map(productoMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Guarda o actualiza un producto tecnológico a partir de un DTO recibido
     * desde el formulario MVC. Resuelve la Agregación con la Categoría y la
     * Herencia concreta según el tipo de producto.
     */
    public ProductoDTO guardar(ProductoDTO dto) {
        Categoria categoria = null;
        if (dto.getCategoriaId() != null) {
            categoria = categoriaRepository.findById(dto.getCategoriaId())
                    .orElse(null);
        }

        // Si es una actualización de un producto existente, preservamos campos clave si aplica
        Producto entity;
        if (dto.getId() != null) {
            Optional<Producto> existenteOpt = productoRepository.findById(dto.getId());
            if (existenteOpt.isPresent()) {
                Producto existente = existenteOpt.get();
                if (existente instanceof ProductoFisico && "FISICO".equalsIgnoreCase(dto.getTipoProducto())) {
                    ProductoFisico pf = (ProductoFisico) existente;
                    pf.setNombre(dto.getNombre());
                    pf.setCodigo(dto.getCodigo());
                    pf.setDescripcion(dto.getDescripcion());
                    pf.setPrecioVenta(dto.getPrecioVenta());
                    pf.setCategoria(categoria);
                    pf.setStock(dto.getStock());
                    pf.setStockMinimo(dto.getStockMinimo());
                    pf.setPeso(dto.getPeso());
                    pf.setUbicacionDeposito(dto.getUbicacionDeposito());
                    entity = pf;
                } else if (existente instanceof ProductoDigital && "DIGITAL".equalsIgnoreCase(dto.getTipoProducto())) {
                    ProductoDigital pd = (ProductoDigital) existente;
                    pd.setNombre(dto.getNombre());
                    pd.setCodigo(dto.getCodigo());
                    pd.setDescripcion(dto.getDescripcion());
                    pd.setPrecioVenta(dto.getPrecioVenta());
                    pd.setCategoria(categoria);
                    if (dto.getClaveLicencia() != null && !dto.getClaveLicencia().isBlank()) {
                        pd.setClaveLicencia(dto.getClaveLicencia());
                    }
                    pd.setUrlDescarga(dto.getUrlDescarga());
                    pd.setVigenciaDias(dto.getVigenciaDias());
                    entity = pd;
                } else {
                    entity = productoMapper.toEntity(dto, categoria);
                }
            } else {
                entity = productoMapper.toEntity(dto, categoria);
            }
        } else {
            entity = productoMapper.toEntity(dto, categoria);
        }

        Producto guardado = productoRepository.save(entity);
        return productoMapper.toDTO(guardado);
    }

    /**
     * Baja lógica de un producto: mantiene la integridad histórica de órdenes pasadas.
     */
    public void darDeBaja(Long id) {
        productoRepository.findById(id).ifPresent(p -> {
            p.setActivo(false);
            productoRepository.save(p);
        });
    }

    @Transactional(readOnly = true)
    public boolean existeCodigo(String codigo) {
        return productoRepository.existsByCodigo(codigo);
    }

    @Transactional(readOnly = true)
    public long contarProductosActivos() {
        return productoRepository.findByActivoTrue().size();
    }
}

