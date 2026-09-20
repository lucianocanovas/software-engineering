package com.colegio.repository;

import com.colegio.model.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotaRepository extends JpaRepository<Nota, UUID> {
    List<Nota> findByAlumnoId(UUID alumnoId);
    List<Nota> findByEvaluacionId(UUID evaluacionId);

    @Query("SELECT AVG(n.puntaje) FROM Nota n")
    Double calcularPromedioGeneralInstitucional();

    @Query("SELECT AVG(n.puntaje) FROM Nota n WHERE n.alumno.id = :alumnoId")
    Double calcularPromedioPorAlumno(@Param("alumnoId") UUID alumnoId);
}

