package com.techstore.repository;

import com.techstore.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * =============================================================================
 * CAPA REPOSITORIO: ProductoRepository
 * =============================================================================
 * Maneja consultas polimórficas sobre la jerarquía de Producto (Físicos y Digitales).
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    Optional<Producto> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);

    List<Producto> findByActivoTrue();

    List<Producto> findByCategoriaId(Long categoriaId);

    /**
     * Búsqueda por término de texto en código o nombre.
     */
    @Query("SELECT p FROM Producto p WHERE p.activo = true AND (LOWER(p.nombre) LIKE LOWER(CONCAT('%', :filtro, '%')) OR LOWER(p.codigo) LIKE LOWER(CONCAT('%', :filtro, '%')))")
    List<Producto> buscarPorNombreOCodigo(String filtro);
}

