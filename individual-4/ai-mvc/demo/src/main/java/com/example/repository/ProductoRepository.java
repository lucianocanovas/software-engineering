package com.example.repository;

import com.example.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Capa de acceso a datos (Repository/DAO).
 * Spring Data genera en tiempo de ejecucion la implementacion de CRUD a partir
 * de {@link JpaRepository}; la capa de servicio no necesita SQL manual.
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
}