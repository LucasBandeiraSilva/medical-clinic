package com.clinica.clinica.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinica.clinica.entities.Estoque;

public interface EstoqueRepository extends JpaRepository<Estoque,Long>{

    List<Estoque> findAllByOrderByNomeAsc();
    
}
