package com.techstore.repository;

import com.techstore.model.ProductoDigital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repositorio especializado para productos digitales y licencias de software.
 */
@Repository
public interface ProductoDigitalRepository extends JpaRepository<ProductoDigital, Long> {

    List<ProductoDigital> findByActivoTrue();
}

