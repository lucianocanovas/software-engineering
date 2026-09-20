package com.techstore.controller;

import com.techstore.service.OrdenDeCompraService;
import com.techstore.service.ProductoService;
import com.techstore.service.ProveedorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * =============================================================================
 * CAPA CONTROLADOR: DashboardController
 * =============================================================================
 * Controla el panel de control principal de la empresa tecnológica.
 * Provee indicadores clave (KPIs), métricas de stock y accesos rápidos
 * para administradores, compradores y vendedores.
 */
@Controller
public class DashboardController {

    private final ProductoService productoService;
    private final OrdenDeCompraService ordenDeCompraService;
    private final ProveedorService proveedorService;

    public DashboardController(ProductoService productoService,
                               OrdenDeCompraService ordenDeCompraService,
                               ProveedorService proveedorService) {
        this.productoService = productoService;
        this.ordenDeCompraService = ordenDeCompraService;
        this.proveedorService = proveedorService;
    }

    @GetMapping({"/", "/dashboard"})
    public String index(Model model) {
        model.addAttribute("totalProductos", productoService.contarProductosActivos());
        model.addAttribute("productosBajoStock", productoService.listarBajoStock());
        model.addAttribute("ordenesPendientes", ordenDeCompraService.contarPendientes());
        model.addAttribute("totalProveedores", proveedorService.contarProveedores());
        model.addAttribute("ultimasOrdenes", ordenDeCompraService.listarTodas());

        return "dashboard/index";
    }
}

