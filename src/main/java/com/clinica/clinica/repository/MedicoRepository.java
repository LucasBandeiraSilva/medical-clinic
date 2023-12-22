package com.clinica.clinica.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinica.clinica.entities.Medico;

public interface MedicoRepository extends JpaRepository<Medico,Long> {

    boolean existsByCpfOrEmail(String cpf, String email);
    boolean existsByCelular(Long celular);

    
}
