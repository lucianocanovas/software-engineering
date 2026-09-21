package com.clubdeportivo.repository;

import com.clubdeportivo.entity.Socio;
import com.clubdeportivo.entity.enums.CategoriaSocio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * =========================================================================================
 * REPOSITORIO: SocioRepository
 * =========================================================================================
 * Operaciones de acceso a datos para Socios Titulares del club deportivo.
 */
@Repository
public interface SocioRepository extends JpaRepository<Socio, Long> {

    Optional<Socio> findByNumeroSocio(String numeroSocio);

    Optional<Socio> findByDni(String dni);

    boolean existsByNumeroSocio(String numeroSocio);

    boolean existsByDni(String dni);

    List<Socio> findByActivoTrue();

    List<Socio> findByCategoria(CategoriaSocio categoria);

    @Query("SELECT s FROM Socio s WHERE LOWER(s.nombre) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR LOWER(s.apellido) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR s.dni LIKE CONCAT('%', :query, '%') " +
           "OR s.numeroSocio LIKE CONCAT('%', :query, '%')")
    List<Socio> buscarPorTermino(@Param("query") String query);
}

