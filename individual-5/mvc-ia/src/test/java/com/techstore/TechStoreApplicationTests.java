package com.techstore;

import com.techstore.dto.DetalleOrdenCompraDTO;
import com.techstore.dto.LoginDTO;
import com.techstore.dto.OrdenDeCompraDTO;
import com.techstore.dto.ProductoDTO;
import com.techstore.dto.UsuarioDTO;
import com.techstore.model.*;
import com.techstore.repository.OrdenDeCompraRepository;
import com.techstore.repository.ProductoRepository;
import com.techstore.repository.ProveedorRepository;
import com.techstore.repository.UsuarioRepository;
import com.techstore.service.OrdenDeCompraService;
import com.techstore.service.ProductoService;
import com.techstore.service.UsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * =============================================================================
 * SUITE DE PRUEBAS DE INTEGRACIÓN: TechStoreApplicationTests
 * =============================================================================
 * Valida los requisitos funcionales del Ejercicio 'a':
 * 1. Autenticación con usuario y contraseña (LoginDTO / UsuarioDTO).
 * 2. Cálculo polimórfico de precios en Herencia (ProductoFisico vs ProductoDigital).
 * 3. Relación de Agregación (Categoria agrupa Productos).
 * 4. Relación de Composición (OrdenDeCompra administra DetalleOrdenCompra).
 * 5. Actualización automática de stock de productos físicos al confirmar recepción.
 */
@SpringBootTest
class TechStoreApplicationTests {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private OrdenDeCompraService ordenDeCompraService;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private OrdenDeCompraRepository ordenDeCompraRepository;

    @Test
    @DisplayName("Test 1: Autenticación exitosa con credenciales de Administrador")
    void testAutenticacionExitosa() {
        LoginDTO loginDTO = new LoginDTO("admin", "admin123");
        Optional<UsuarioDTO> usuarioOpt = usuarioService.autenticar(loginDTO);

        assertTrue(usuarioOpt.isPresent(), "El usuario 'admin' debe autenticarse correctamente");
        assertEquals("ADMIN", usuarioOpt.get().getRol());
        assertEquals("Luciano Canovas", usuarioOpt.get().getNombreCompleto());
    }

    @Test
    @DisplayName("Test 2: Autenticación rechazada ante contraseña incorrecta")
    void testAutenticacionFallida() {
        LoginDTO loginDTO = new LoginDTO("admin", "clave_erronea_999");
        Optional<UsuarioDTO> usuarioOpt = usuarioService.autenticar(loginDTO);

        assertTrue(usuarioOpt.isEmpty(), "Las credenciales inválidas deben ser rechazadas");
    }

    @Test
    @DisplayName("Test 3: Cálculo polimórfico de precios en Herencia UML (Físico vs Digital)")
    void testCalculoPolimorficoPrecios() {
        // Producto Físico: suma recargo del 3% por embalaje
        ProductoFisico fisico = new ProductoFisico("TEST-HW", "Gabinete Gamer", "Desc", 100.0, null, 10, 2, 8.0, "A-1");
        Double precioFinalFisico = fisico.calcularPrecioFinal();
        assertEquals(103.0, precioFinalFisico, "El precio final del producto físico debe incluir el 3% de embalaje");

        // Producto Digital: aplica 5% de descuento digital
        ProductoDigital digital = new ProductoDigital("TEST-SW", "Licencia IDE", "Desc", 100.0, null, "KEY-123", "http://dl", 365);
        Double precioFinalDigital = digital.calcularPrecioFinal();
        assertEquals(95.0, precioFinalDigital, "El precio final del producto digital debe incluir el 5% de descuento");
    }

    @Test
    @DisplayName("Test 4: Flujo Principal de Negocio - Actualización de Stock al Confirmar Recepción de Compra")
    void testActualizacionStockAlConfirmarRecepcion() {
        // 1. Obtener un producto físico existente en SQLite
        Producto producto = productoRepository.findByCodigo("PROD-003")
                .orElseThrow(() -> new IllegalStateException("Producto PROD-003 no encontrado"));
        assertTrue(producto instanceof ProductoFisico, "Debe ser un producto físico");
        ProductoFisico fisico = (ProductoFisico) producto;
        int stockInicial = fisico.getStock();

        // 2. Crear una orden de compra para este producto
        Proveedor proveedor = proveedorRepository.findAll().get(0);
        Usuario encargado = usuarioRepository.findByNombreUsuario("compras").orElseThrow();

        OrdenDeCompraDTO nuevaOrdenDTO = new OrdenDeCompraDTO();
        nuevaOrdenDTO.setNumero("OC-TEST-" + System.currentTimeMillis());
        nuevaOrdenDTO.setFechaEmision(LocalDate.now());
        nuevaOrdenDTO.setFechaEntregaEstimada(LocalDate.now().plusDays(5));
        nuevaOrdenDTO.setProveedorId(proveedor.getId());

        int cantidadComprada = 20;
        DetalleOrdenCompraDTO detalleDTO = new DetalleOrdenCompraDTO();
        detalleDTO.setProductoId(fisico.getId());
        detalleDTO.setCantidad(cantidadComprada);
        detalleDTO.setPrecioUnitario(70.0);

        List<DetalleOrdenCompraDTO> detalles = new ArrayList<>();
        detalles.add(detalleDTO);
        nuevaOrdenDTO.setDetalles(detalles);

        // Guardar la orden de compra (Composición)
        OrdenDeCompraDTO ordenCreada = ordenDeCompraService.crearOrden(nuevaOrdenDTO, encargado.getNombreUsuario());
        assertNotNull(ordenCreada.getId());
        assertTrue(ordenCreada.isPendiente());

        // 3. Confirmar la recepción física de la orden
        boolean recepcionExitosa = ordenDeCompraService.confirmarRecepcion(ordenCreada.getId());
        assertTrue(recepcionExitosa, "La recepción de mercadería debe completarse con éxito");

        // 4. Verificar que el stock en SQLite aumentó exactamente en la cantidad adquirida
        Producto productoActualizado = productoRepository.findById(fisico.getId()).orElseThrow();
        ProductoFisico fisicoActualizado = (ProductoFisico) productoActualizado;

        assertEquals(stockInicial + cantidadComprada, fisicoActualizado.getStock(),
                "El stock físico en SQLite debe haberse incrementado en exactamente " + cantidadComprada + " unidades");
    }
}

