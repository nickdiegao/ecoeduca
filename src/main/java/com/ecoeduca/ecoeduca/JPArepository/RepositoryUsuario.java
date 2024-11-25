package com.ecoeduca.ecoeduca.JPArepository;

import com.ecoeduca.ecoeduca.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepositoryUsuario extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String email);

    // Usando Spring Data JPA com Sort
    List<Usuario> findAllByOrderByPontuacaoDesc();
    
    // Ou, usando uma consulta JPQL personalizada
    @Query("SELECT u FROM Usuario u ORDER BY u.pontuacao DESC")
    List<Usuario> findUsuariosOrdenadosPorPontuacao();
}

