package com.clinica.clinica.entities;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.clinica.clinica.enumTypes.CidadesEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pacientes")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String sobrenome;
    private String senha;
    private String cpf;
    private String email;
    private int celular;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;
    @Enumerated(EnumType.STRING)
    @Column(name = "cidade_nascimento")
    private CidadesEnum cidadesEnum;
    @ManyToOne
    private Medico medico;
}
