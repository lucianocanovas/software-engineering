package com.colegio.repository;

import com.colegio.model.Colegio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ColegioRepository extends JpaRepository<Colegio, UUID> {
}

