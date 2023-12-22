package com.clinica.clinica.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinica.clinica.entities.Admin;

public interface AdminRepository extends JpaRepository<Admin,Long> {
    
}
