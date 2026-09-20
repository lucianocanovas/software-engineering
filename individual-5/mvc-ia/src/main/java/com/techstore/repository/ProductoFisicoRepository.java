package com.techstore.repository;

import com.techstore.model.ProductoFisico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repositorio especializado para productos físicos e inventario en bodega.
 */
@Repository
public interface ProductoFisicoRepository extends JpaRepository<ProductoFisico, Long> {

    @Query("SELECT pf FROM ProductoFisico pf WHERE pf.activo = true AND pf.stock <= pf.stockMinimo")
    List<ProductoFisico> findProductosConBajoStock();

    List<ProductoFisico> findByActivoTrue();
}

