package com.ecoeduca.ecoeduca.JPArepository;

import com.ecoeduca.ecoeduca.model.Postagens;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryPostagens extends JpaRepository<Postagens, Long> {
    List<Postagens> findAllByOrderByDataCriacaoDesc();
}
