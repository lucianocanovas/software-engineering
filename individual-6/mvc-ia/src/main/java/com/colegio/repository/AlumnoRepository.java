package com.colegio.repository;

import com.colegio.model.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio Spring Data JPA para la entidad Alumno.
 */
@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, UUID> {

    Optional<Alumno> findByCodigo(Integer codigo);

    Optional<Alumno> findByEmail(String email);

    boolean existsByCodigo(Integer codigo);

    List<Alumno> findByAulaId(UUID aulaId);
}

