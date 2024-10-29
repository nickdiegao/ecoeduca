package com.ecoeduca.ecoeduca.JPArepository;

import com.ecoeduca.ecoeduca.model.Responsaveis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryResponsaveis extends JpaRepository<Responsaveis, Long>{
    
}
