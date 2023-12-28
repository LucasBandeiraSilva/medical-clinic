package com.clinica.clinica.entities;

import java.time.LocalDate;

import com.clinica.clinica.enumTypes.CidadesEnum;
import com.clinica.clinica.enumTypes.PermissoesEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tb_admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nome;
    private String sobrenome;
    private String senha;
    @Column(unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    private PermissoesEnum tipoPermissao;
}
