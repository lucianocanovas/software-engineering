package com.techstore.repository;

import com.techstore.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * =============================================================================
 * CAPA REPOSITORIO: CategoriaRepository
 * =============================================================================
 * Gestiona el acceso a datos para las Categorías que agrupan productos tecnológicos.
 */
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    Optional<Categoria> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);

    List<Categoria> findAllByOrderByNombreAsc();
}

