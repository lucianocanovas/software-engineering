package com.techstore.service;

import com.techstore.dto.DetalleOrdenCompraDTO;
import com.techstore.dto.OrdenDeCompraDTO;
import com.techstore.mapper.OrdenDeCompraMapper;
import com.techstore.model.*;
import com.techstore.repository.OrdenDeCompraRepository;
import com.techstore.repository.ProductoRepository;
import com.techstore.repository.ProveedorRepository;
import com.techstore.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * =============================================================================
 * CAPA SERVICIO: OrdenDeCompraService
 * =============================================================================
 * Coordina el flujo de compras mayoristas y actualización de stock:
 * "Trabajamos en el desarrollo del sistema para una empresa que realiza venta de
 * productos de tecnología, estos se actualizan en el stock de la empresa por
 * medio de compras a sus proveedores mayorista mediante el uso de órdenes de compra
 * que registran el detalle de la misma."
 *
 * CONCEPTOS IMPLEMENTADOS:
 * - COMPOSICIÓN: La orden administra el ciclo de vida de los DetalleOrdenCompra.
 * - ASOCIACIÓN: Conexión con Proveedor y con Producto en cada detalle.
 * - TRANSACCIONALIDAD: La confirmación de entrega actualiza el estado de la orden
 *   y los stocks en una única transacción atómica en SQLite.
 */
@Service
@Transactional
public class OrdenDeCompraService {

    private final OrdenDeCompraRepository ordenDeCompraRepository;
    private final ProveedorRepository proveedorRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;
    private final OrdenDeCompraMapper ordenDeCompraMapper;

    public OrdenDeCompraService(OrdenDeCompraRepository ordenDeCompraRepository,
                                ProveedorRepository proveedorRepository,
                                ProductoRepository productoRepository,
                                UsuarioRepository usuarioRepository,
                                OrdenDeCompraMapper ordenDeCompraMapper) {
        this.ordenDeCompraRepository = ordenDeCompraRepository;
        this.proveedorRepository = proveedorRepository;
        this.productoRepository = productoRepository;
        this.usuarioRepository = usuarioRepository;
        this.ordenDeCompraMapper = ordenDeCompraMapper;
    }

    @Transactional(readOnly = true)
    public List<OrdenDeCompraDTO> listarTodas() {
        return ordenDeCompraRepository.findAllByOrderByFechaEmisionDesc().stream()
                .map(ordenDeCompraMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<OrdenDeCompraDTO> buscarPorId(Long id) {
        return ordenDeCompraRepository.findById(id).map(ordenDeCompraMapper::toDTO);
    }

    /**
     * Registra una nueva Orden de Compra con sus detalles correspondientes.
     */
    public OrdenDeCompraDTO crearOrden(OrdenDeCompraDTO dto, String nombreUsuarioOperador) {
        Proveedor proveedor = proveedorRepository.findById(dto.getProveedorId())
                .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado con ID: " + dto.getProveedorId()));

        Usuario operador = usuarioRepository.findByNombreUsuario(nombreUsuarioOperador)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + nombreUsuarioOperador));

        OrdenDeCompra orden = new OrdenDeCompra();
        orden.setNumero(dto.getNumero());
        orden.setFechaEmision(dto.getFechaEmision() != null ? dto.getFechaEmision() : LocalDate.now());
        orden.setFechaEntregaEstimada(dto.getFechaEntregaEstimada());
        orden.setProveedor(proveedor);
        orden.setEncargadoCompras(operador);
        orden.setEstado(EstadoOrden.PENDIENTE);

        // Relación de Composición: creación y vinculación de los detalles
        if (dto.getDetalles() != null) {
            for (DetalleOrdenCompraDTO detDTO : dto.getDetalles()) {
                if (detDTO.getProductoId() != null && detDTO.getCantidad() != null && detDTO.getCantidad() > 0) {
                    Producto producto = productoRepository.findById(detDTO.getProductoId())
                            .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

                    DetalleOrdenCompra detalle = new DetalleOrdenCompra();
                    detalle.setProducto(producto);
                    detalle.setCantidad(detDTO.getCantidad());
                    detalle.setPrecioUnitario(detDTO.getPrecioUnitario());
                    detalle.calcularSubtotal();

                    orden.agregarDetalle(detalle);
                }
            }
        }

        orden.calcularTotal();
        OrdenDeCompra guardada = ordenDeCompraRepository.save(orden);
        return ordenDeCompraMapper.toDTO(guardada);
    }

    /**
     * Confirma la recepción de mercadería física en depósito.
     * CUMPLE CON EL REQUERIMIENTO PRINCIPAL DE NEGOCIO:
     * - Cambia el estado a RECIBIDA.
     * - Incrementa el stock de los productos físicos comprados en la base SQLite.
     */
    public boolean confirmarRecepcion(Long ordenId) {
        Optional<OrdenDeCompra> ordenOpt = ordenDeCompraRepository.findById(ordenId);
        if (ordenOpt.isEmpty()) {
            return false;
        }

        OrdenDeCompra orden = ordenOpt.get();
        if (orden.getEstado() != EstadoOrden.PENDIENTE) {
            return false;
        }

        // Ejecuta el método de dominio de la entidad OrdenDeCompra
        boolean exito = orden.confirmarRecepcion();
        if (exito) {
            // Guardamos la orden y los productos actualizados
            ordenDeCompraRepository.save(orden);
            for (DetalleOrdenCompra d : orden.getDetalles()) {
                if (d.getProducto() instanceof ProductoFisico) {
                    productoRepository.save(d.getProducto());
                }
            }
        }
        return exito;
    }

    /**
     * Cancela una orden de compra pendiente.
     */
    public boolean cancelarOrden(Long ordenId) {
        Optional<OrdenDeCompra> ordenOpt = ordenDeCompraRepository.findById(ordenId);
        if (ordenOpt.isPresent()) {
            OrdenDeCompra orden = ordenOpt.get();
            if (orden.getEstado() == EstadoOrden.PENDIENTE) {
                orden.setEstado(EstadoOrden.CANCELADA);
                ordenDeCompraRepository.save(orden);
                return true;
            }
        }
        return false;
    }

    @Transactional(readOnly = true)
    public long contarPendientes() {
        return ordenDeCompraRepository.countOrdenesPendientes();
    }
}

