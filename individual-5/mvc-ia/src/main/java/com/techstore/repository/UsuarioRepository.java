package com.techstore.repository;

import com.techstore.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * =============================================================================
 * CAPA REPOSITORIO: UsuarioRepository
 * =============================================================================
 * @Repository: Marca el componente como Bean de persistencia de Spring.
 * Extiende JpaRepository para heredar operaciones CRUD completas y paginación
 * sobre la jerarquía de la entidad Usuario en SQLite.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Búsqueda por nombre de usuario para el proceso de autenticación / login.
     * Spring Data JPA genera automáticamente la sentencia SQL:
     * "SELECT * FROM usuarios WHERE nombre_usuario = ? AND activo = 1"
     */
    Optional<Usuario> findByNombreUsuarioAndActivoTrue(String nombreUsuario);

    Optional<Usuario> findByNombreUsuario(String nombreUsuario);

    boolean existsByNombreUsuario(String nombreUsuario);
}

