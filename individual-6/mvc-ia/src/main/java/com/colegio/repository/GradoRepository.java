package com.colegio.repository;

import com.colegio.model.Grado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GradoRepository extends JpaRepository<Grado, UUID> {
}

