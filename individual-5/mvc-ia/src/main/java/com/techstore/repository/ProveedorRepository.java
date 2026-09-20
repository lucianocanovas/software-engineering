package com.techstore.repository;

import com.techstore.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * =============================================================================
 * CAPA REPOSITORIO: ProveedorRepository
 * =============================================================================
 * Persistencia de proveedores mayoristas en SQLite.
 */
@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    Optional<Proveedor> findByCuit(String cuit);

    Optional<Proveedor> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);

    boolean existsByCuit(String cuit);

    List<Proveedor> findByActivoTrueOrderByRazonSocialAsc();
}

