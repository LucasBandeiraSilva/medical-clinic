package com.clinica.clinica.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinica.clinica.entities.Paciente;

public interface ClienteRepository extends JpaRepository<Paciente, Long> {

}
