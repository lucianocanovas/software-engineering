package com.techstore.controller;

import com.techstore.config.AuthInterceptor;
import com.techstore.dto.DetalleOrdenCompraDTO;
import com.techstore.dto.OrdenDeCompraDTO;
import com.techstore.dto.UsuarioDTO;
import com.techstore.service.OrdenDeCompraService;
import com.techstore.service.ProductoService;
import com.techstore.service.ProveedorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * =============================================================================
 * CAPA CONTROLADOR: OrdenDeCompraController
 * =============================================================================
 * Maneja el flujo transaccional de órdenes de compra con proveedores mayoristas
 * y la actualización del inventario de productos de tecnología.
 *
 * CUMPLE CON EL REQUERIMIENTO DEL ENUNCIADO:
 * "...estos se actualizan en el stock de la empresa por medio de compras a sus
 * proveedores mayorista mediante el uso de órdenes de compra que registran el
 * detalle de la misma."
 */
@Controller
@RequestMapping("/ordenes")
public class OrdenDeCompraController {

    private final OrdenDeCompraService ordenDeCompraService;
    private final ProveedorService proveedorService;
    private final ProductoService productoService;

    public OrdenDeCompraController(OrdenDeCompraService ordenDeCompraService,
                                   ProveedorService proveedorService,
                                   ProductoService productoService) {
        this.ordenDeCompraService = ordenDeCompraService;
        this.proveedorService = proveedorService;
        this.productoService = productoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("ordenes", ordenDeCompraService.listarTodas());
        return "ordenes/list";
    }

    @GetMapping("/nueva")
    public String nueva(Model model) {
        OrdenDeCompraDTO dto = new OrdenDeCompraDTO();
        dto.setNumero("OC-" + System.currentTimeMillis() % 1000000);
        dto.setFechaEmision(LocalDate.now());
        dto.setFechaEntregaEstimada(LocalDate.now().plusDays(7));

        // Inicializamos 3 renglones de detalles vacíos para el formulario
        List<DetalleOrdenCompraDTO> detalles = new ArrayList<>();
        detalles.add(new DetalleOrdenCompraDTO());
        detalles.add(new DetalleOrdenCompraDTO());
        detalles.add(new DetalleOrdenCompraDTO());
        dto.setDetalles(detalles);

        model.addAttribute("orden", dto);
        model.addAttribute("proveedores", proveedorService.listarTodos());
        model.addAttribute("productos", productoService.listarTodos());
        return "ordenes/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("orden") OrdenDeCompraDTO dto,
                          HttpSession session,
                          RedirectAttributes redirectAttributes) {

        UsuarioDTO usuarioSesion = (UsuarioDTO) session.getAttribute(AuthInterceptor.SESSION_USER_KEY);
        String username = usuarioSesion != null ? usuarioSesion.getNombreUsuario() : "admin";

        try {
            // Filtrar detalles que tengan producto y cantidad válida
            if (dto.getDetalles() != null) {
                dto.getDetalles().removeIf(d -> d.getProductoId() == null || d.getCantidad() == null || d.getCantidad() <= 0);
            }

            if (dto.getDetalles() == null || dto.getDetalles().isEmpty()) {
                redirectAttributes.addFlashAttribute("mensajeError", "Debe agregar al menos un producto a la orden de compra.");
                return "redirect:/ordenes/nueva";
            }

            ordenDeCompraService.crearOrden(dto, username);
            redirectAttributes.addFlashAttribute("mensajeExito", "Orden de Compra " + dto.getNumero() + " generada exitosamente.");
            return "redirect:/ordenes";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error al registrar la orden: " + e.getMessage());
            return "redirect:/ordenes/nueva";
        }
    }

    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<OrdenDeCompraDTO> opt = ordenDeCompraService.buscarPorId(id);
        if (opt.isEmpty()) {
            redirectAttributes.addFlashAttribute("mensajeError", "Orden de compra no encontrada.");
            return "redirect:/ordenes";
        }

        model.addAttribute("orden", opt.get());
        return "ordenes/detail";
    }

    /**
     * ACCIÓN CLAVE: Confirmar Recepción de Mercadería
     * Cambia el estado de la Orden a RECIBIDA y actualiza de inmediato
     * el stock de los productos físicos en la base de datos SQLite.
     */
    @PostMapping("/confirmar/{id}")
    public String confirmarRecepcion(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        boolean exito = ordenDeCompraService.confirmarRecepcion(id);
        if (exito) {
            redirectAttributes.addFlashAttribute("mensajeExito",
                    "¡Mercadería recibida con éxito! El stock de los productos tecnológicos ha sido actualizado en el inventario.");
        } else {
            redirectAttributes.addFlashAttribute("mensajeError",
                    "No se pudo confirmar la recepción. La orden ya podría haber sido recibida o cancelada previamente.");
        }
        return "redirect:/ordenes/detalle/" + id;
    }

    @PostMapping("/cancelar/{id}")
    public String cancelar(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        boolean exito = ordenDeCompraService.cancelarOrden(id);
        if (exito) {
            redirectAttributes.addFlashAttribute("mensajeExito", "Orden de compra cancelada.");
        } else {
            redirectAttributes.addFlashAttribute("mensajeError", "No se puede cancelar una orden ya recibida o cancelada.");
        }
        return "redirect:/ordenes";
    }
}

