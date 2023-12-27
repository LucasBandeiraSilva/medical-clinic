package com.clinica.clinica.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinica.clinica.entities.Admin;

public interface AdminRepository extends JpaRepository<Admin,Long> {


    boolean existsByEmail(String email);
    Admin findByEmail(String email);
    boolean existsBySenha(String senha);
    
}
