package com.ecoeduca.ecoeduca.JPArepository;

import com.ecoeduca.ecoeduca.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryUsuario extends JpaRepository<Usuario, Long> {
}