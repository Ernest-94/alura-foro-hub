package com.alura.foro.infra.repository;

import com.alura.foro.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    boolean existsByEmail(String email);

    UserDetails findByEmail(String email);

    @Query("SELECT u.nombre FROM Usuario u")
    List<String> getNombres();

    Optional<Usuario> findByNombre(String nombre);
}
