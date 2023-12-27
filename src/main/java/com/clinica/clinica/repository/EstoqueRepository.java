package com.clinica.clinica.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinica.clinica.entities.Estoque;
import com.clinica.clinica.enumTypes.TipoServico;


public interface EstoqueRepository extends JpaRepository<Estoque,Long>{

    List<Estoque> findAllByOrderByNomeAsc();
    List<Estoque> findByTipoServico(TipoServico tipoServico);
    
}
