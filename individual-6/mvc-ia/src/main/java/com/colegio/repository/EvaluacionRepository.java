package com.colegio.repository;

import com.colegio.model.Evaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion, UUID> {
    List<Evaluacion> findByMateriaId(UUID materiaId);
    List<Evaluacion> findByProfesorId(UUID profesorId);
}

