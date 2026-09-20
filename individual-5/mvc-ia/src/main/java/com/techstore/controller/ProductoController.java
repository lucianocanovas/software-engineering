package com.techstore.controller;

import com.techstore.dto.ProductoDTO;
import com.techstore.service.CategoriaService;
import com.techstore.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

/**
 * =============================================================================
 * CAPA CONTROLADOR: ProductoController
 * =============================================================================
 * Gestiona el ciclo completo del ABM (Alta, Baja, Modificación y Consulta)
 * de productos de tecnología en la arquitectura MVC.
 *
 * CONCEPTOS DEMOSTRADOS:
 * - DTO: La vista sólo interactúa con instancias de ProductoDTO.
 * - HERENCIA: Soporta formularios especializados para ProductoFisico y ProductoDigital.
 * - AGREGACIÓN: Permite vincular o reasignar la Categoria del producto.
 */
@Controller
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    public ProductoController(ProductoService productoService, CategoriaService categoriaService) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }

    /**
     * Listado general de productos tecnológicos con filtros dinámicos.
     */
    @GetMapping
    public String listar(@RequestParam(value = "filtro", required = false) String filtro,
                         @RequestParam(value = "categoriaId", required = false) Long categoriaId,
                         Model model) {

        List<ProductoDTO> productos;
        if (categoriaId != null) {
            productos = productoService.filtrarPorCategoria(categoriaId);
        } else if (filtro != null && !filtro.isBlank()) {
            productos = productoService.buscarPorFiltro(filtro);
        } else {
            productos = productoService.listarTodos();
        }

        model.addAttribute("productos", productos);
        model.addAttribute("categorias", categoriaService.listarTodas());
        model.addAttribute("filtroActual", filtro);
        model.addAttribute("categoriaSeleccionada", categoriaId);
        return "productos/list";
    }

    /**
     * Formulario para nuevo Producto Físico (Hardware, Stock y Depósito).
     */
    @GetMapping("/nuevo/fisico")
    public String nuevoFisico(Model model) {
        ProductoDTO dto = new ProductoDTO();
        dto.setTipoProducto("FISICO");
        dto.setStock(0);
        dto.setStockMinimo(5);
        dto.setPeso(1.0);

        model.addAttribute("producto", dto);
        model.addAttribute("categorias", categoriaService.listarTodas());
        model.addAttribute("tituloForm", "Nuevo Producto Físico (Hardware / Depósito)");
        return "productos/form-fisico";
    }

    /**
     * Formulario para nuevo Producto Digital (Software, Licencias y Descarga).
     */
    @GetMapping("/nuevo/digital")
    public String nuevoDigital(Model model) {
        ProductoDTO dto = new ProductoDTO();
        dto.setTipoProducto("DIGITAL");
        dto.setVigenciaDias(365);

        model.addAttribute("producto", dto);
        model.addAttribute("categorias", categoriaService.listarTodas());
        model.addAttribute("tituloForm", "Nuevo Producto Digital (Software / Licencia)");
        return "productos/form-digital";
    }

    /**
     * Formulario de edición: Detecta polimórficamente si es físico o digital
     * y despacha a la vista adecuada.
     */
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<ProductoDTO> opt = productoService.buscarPorId(id);
        if (opt.isEmpty()) {
            redirectAttributes.addFlashAttribute("mensajeError", "El producto no existe.");
            return "redirect:/productos";
        }

        ProductoDTO dto = opt.get();
        model.addAttribute("producto", dto);
        model.addAttribute("categorias", categoriaService.listarTodas());

        if (dto.esDigital()) {
            model.addAttribute("tituloForm", "Editar Producto Digital: " + dto.getNombre());
            return "productos/form-digital";
        } else {
            model.addAttribute("tituloForm", "Editar Producto Físico: " + dto.getNombre());
            return "productos/form-fisico";
        }
    }

    /**
     * Detalle técnico del producto: Muestra atributos específicos y precio final calculado.
     */
    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<ProductoDTO> opt = productoService.buscarPorId(id);
        if (opt.isEmpty()) {
            redirectAttributes.addFlashAttribute("mensajeError", "El producto no existe.");
            return "redirect:/productos";
        }
        model.addAttribute("producto", opt.get());
        return "productos/detail";
    }

    /**
     * Procesa la persistencia de un producto (Alta o Modificación) a través de DTOs.
     */
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("producto") ProductoDTO productoDTO,
                          BindingResult bindingResult,
                          Model model,
                          RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("categorias", categoriaService.listarTodas());
            model.addAttribute("tituloForm", (productoDTO.getId() == null ? "Nuevo " : "Editar ") +
                    ("DIGITAL".equalsIgnoreCase(productoDTO.getTipoProducto()) ? "Producto Digital" : "Producto Físico"));
            return productoDTO.esDigital() ? "productos/form-digital" : "productos/form-fisico";
        }

        // Validación de unicidad de código para nuevos registros
        if (productoDTO.getId() == null && productoService.existeCodigo(productoDTO.getCodigo())) {
            bindingResult.rejectValue("codigo", "duplicate", "El código ya se encuentra registrado.");
            model.addAttribute("categorias", categoriaService.listarTodas());
            model.addAttribute("tituloForm", "Nuevo Producto " + productoDTO.getTipoProducto());
            return productoDTO.esDigital() ? "productos/form-digital" : "productos/form-fisico";
        }

        productoService.guardar(productoDTO);
        redirectAttributes.addFlashAttribute("mensajeExito", "Producto " + productoDTO.getCodigo() + " guardado con éxito.");
        return "redirect:/productos";
    }

    /**
     * Baja lógica de un producto.
     */
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        productoService.darDeBaja(id);
        redirectAttributes.addFlashAttribute("mensajeExito", "Producto dado de baja correctamente.");
        return "redirect:/productos";
    }
}

