package com.example.service;

import com.example.model.Producto;
import com.example.repository.ProductoRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Capa de negocio: centraliza reglas y coordina la persistencia.
 * Los controladores no acceden directamente al repositorio, respetando la
 * separacion MVC y dejando una unidad testeable para futuras reglas.
 */
@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Transactional
    public List<Producto> listar() {
        return productoRepository.findAll();
    }

    public Producto buscar(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    @Transactional
    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    @Transactional
    public Producto actualizar(Long id, Producto datos) {
        Producto producto = buscar(id);
        if (producto == null) {
            return null;
        }
        producto.setNombre(datos.getNombre());
        producto.setMarca(datos.getMarca());
        producto.setCategoria(datos.getCategoria());
        producto.setPrecio(datos.getPrecio());
        producto.setStock(datos.getStock());
        producto.setDescripcion(datos.getDescripcion());
        return productoRepository.save(producto);
    }

    @Transactional
    public boolean eliminar(Long id) {
        if (!productoRepository.existsById(id)) {
            return false;
        }
        productoRepository.deleteById(id);
        return true;
    }
}