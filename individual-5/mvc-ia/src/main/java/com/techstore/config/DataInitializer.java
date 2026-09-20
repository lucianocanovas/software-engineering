package com.techstore.config;

import com.techstore.model.*;
import com.techstore.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

/**
 * =============================================================================
 * COMPONENTE DE INICIALIZACIÓN: DataInitializer
 * =============================================================================
 * Implementa CommandLineRunner para sembrar datos iniciales en la base de datos
 * SQLite al arrancar la aplicación por primera vez.
 *
 * Pre-carga:
 * 1. Usuarios con roles especificados en el diagrama de clases (Admin, Compras, Vendedor).
 * 2. Categorías que agrupan productos (Agregación UML).
 * 3. Proveedores mayoristas (Asociación UML).
 * 4. Productos Físicos y Digitales (Herencia UML).
 * 5. Órdenes de compra con detalles de renglones (Composición UML) listas para probar
 *    el incremento de stock en tiempo real.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProveedorRepository proveedorRepository;
    private final ProductoRepository productoRepository;
    private final OrdenDeCompraRepository ordenDeCompraRepository;

    public DataInitializer(UsuarioRepository usuarioRepository,
                           CategoriaRepository categoriaRepository,
                           ProveedorRepository proveedorRepository,
                           ProductoRepository productoRepository,
                           OrdenDeCompraRepository ordenDeCompraRepository) {
        this.usuarioRepository = usuarioRepository;
        this.categoriaRepository = categoriaRepository;
        this.proveedorRepository = proveedorRepository;
        this.productoRepository = productoRepository;
        this.ordenDeCompraRepository = ordenDeCompraRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.count() > 0) {
            // Ya existen datos en SQLite
            return;
        }

        System.out.println(">>> [INICIALIZACIÓN] Sembrando datos de demostración en SQLite...");

        // 1. Usuarios con Herencia UML
        Administrador admin = new Administrador("admin", "admin123", "Luciano", "Canovas", "admin@techstore.com");
        EncargadoCompras compras = new EncargadoCompras("compras", "compras123", "Martín", "Gómez", "compras@techstore.com");
        Vendedor vendedor = new Vendedor("vendedor", "vendedor123", "Sofía", "Martínez", "vendedor@techstore.com");

        usuarioRepository.saveAll(List.of(admin, compras, vendedor));

        // 2. Categorías (Relación de Agregación con Productos)
        Categoria catLaptops = new Categoria("CAT-LAP", "Notebooks y Equipos", "Computadoras portátiles, ultrabooks y estaciones de trabajo");
        Categoria catPerif = new Categoria("CAT-PER", "Periféricos y Accesorios", "Teclados, ratones, auriculares y monitores");
        Categoria catComp = new Categoria("CAT-ALM", "Componentes y Almacenamiento", "Discos SSD, memorias RAM, procesadores y placas de video");
        Categoria catSoft = new Categoria("CAT-SOF", "Software y Licencias", "Sistemas operativos, suites de ofimática y soluciones en la nube");

        categoriaRepository.saveAll(List.of(catLaptops, catPerif, catComp, catSoft));

        // 3. Proveedores Mayoristas (Relación de Asociación con Órdenes)
        Proveedor provAsus = new Proveedor("PRV-001", "ASUSTek Computer Inc. Mayorista", "30-71234567-8", "+54 11 4555-1000", "ventas@asus-dist.com", "Av. Del Libertador 4500, CABA");
        Proveedor provKingston = new Proveedor("PRV-002", "Kingston Technology Cono Sur", "30-78912345-1", "+54 11 4777-2000", "mayoristas@kingston.lat", "Parque Industrial Norte 120, Tigre");
        Proveedor provLogitech = new Proveedor("PRV-003", "Logitech International S.A.", "30-54321987-3", "+54 11 4888-3000", "contacto@logitech-b2b.com", "Av. Corrientes 1250, CABA");
        Proveedor provMS = new Proveedor("PRV-004", "Microsoft Distribution Argentina", "30-65432198-4", "+54 11 5000-4000", "licencias@msdistribution.com", "Bouchard 547, CABA");

        proveedorRepository.saveAll(List.of(provAsus, provKingston, provLogitech, provMS));

        // 4. Productos Físicos y Digitales (Relación de Herencia UML)
        ProductoFisico notebook = new ProductoFisico(
                "PROD-001",
                "Notebook ASUS ROG Strix G16 i7 16GB RTX4060",
                "Laptop gamer de alto rendimiento con procesador Intel Core i7 13va gen y pantalla 165Hz.",
                1890.00,
                catLaptops,
                7, // Stock actual
                3, // Stock mínimo
                2.50, // Peso kg
                "Depósito Central - Pasillo A-02"
        );

        ProductoFisico monitor = new ProductoFisico(
                "PROD-002",
                "Monitor Gamer LG UltraGear 27 IPS 144Hz",
                "Monitor plano FHD 1ms respuesta con soporte HDR10 y FreeSync Premium.",
                320.00,
                catPerif,
                10,
                4,
                5.20,
                "Depósito Central - Pasillo B-05"
        );

        ProductoFisico ssd = new ProductoFisico(
                "PROD-003",
                "SSD Kingston NV2 1TB M.2 NVMe PCIe 4.0",
                "Unidad de estado sólido con velocidades de lectura de hasta 3500 MB/s.",
                85.00,
                catComp,
                2, // ¡Stock bajo para disparar alerta visual! (stock <= stockMinimo)
                5,
                0.08,
                "Depósito Central - Gaveta C-14"
        );

        ProductoFisico teclado = new ProductoFisico(
                "PROD-004",
                "Teclado Mecánico Logitech G Pro X RGB",
                "Teclado gamer compacto con switches mecánicos GX Blue intercambiables.",
                135.00,
                catPerif,
                12,
                4,
                0.98,
                "Depósito Central - Pasillo B-01"
        );

        ProductoDigital licenciaWindows = new ProductoDigital(
                "PROD-005",
                "Licencia Microsoft Windows 11 Pro 64-bit",
                "Clave de activación original para 1 PC con soporte para virtualización y cifrado BitLocker.",
                48.00,
                catSoft,
                "TECH-W11P-7492-991A",
                "https://www.microsoft.com/software-download/windows11",
                365
        );

        ProductoDigital licenciaOffice = new ProductoDigital(
                "PROD-006",
                "Suscripción Microsoft 365 Business Standard 1 Año",
                "Suite completa con Word, Excel, Teams, Outlook y 1TB de almacenamiento en OneDrive.",
                125.00,
                catSoft,
                "TECH-O365-5511-BB44",
                "https://admin.microsoft.com/setup",
                365
        );

        productoRepository.saveAll(List.of(notebook, monitor, ssd, teclado, licenciaWindows, licenciaOffice));

        // 5. Órdenes de Compra (Relación de Composición con DetalleOrdenCompra)
        // Orden 1: Ya recibida históricamente
        OrdenDeCompra ordenHistorica = new OrdenDeCompra("OC-2026-0001", LocalDate.now().minusDays(10), provAsus, compras);
        ordenHistorica.setFechaEmision(LocalDate.now().minusDays(15));
        ordenHistorica.setEstado(EstadoOrden.RECIBIDA);

        DetalleOrdenCompra detH1 = new DetalleOrdenCompra(notebook, 5, 1500.00);
        ordenHistorica.agregarDetalle(detH1);
        ordenDeCompraRepository.save(ordenHistorica);

        // Orden 2: PENDIENTE lista para que el usuario pruebe "Confirmar Recepción" e incremente el stock del SSD
        OrdenDeCompra ordenPendiente = new OrdenDeCompra("OC-2026-0002", LocalDate.now().plusDays(3), provKingston, compras);
        ordenPendiente.setFechaEmision(LocalDate.now());

        DetalleOrdenCompra detP1 = new DetalleOrdenCompra(ssd, 15, 65.00); // 15 unidades de SSD
        DetalleOrdenCompra detP2 = new DetalleOrdenCompra(teclado, 10, 95.00); // 10 unidades de Teclado
        ordenPendiente.agregarDetalle(detP1);
        ordenPendiente.agregarDetalle(detP2);
        ordenDeCompraRepository.save(ordenPendiente);

        System.out.println(">>> [INICIALIZACIÓN] Base de datos SQLite inicializada exitosamente.");
    }
}

