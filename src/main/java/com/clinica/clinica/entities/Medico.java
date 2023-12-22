package com.clinica.clinica.entities;

import java.time.LocalDate;
import java.util.List;

import com.clinica.clinica.enumTypes.CidadesEnum;
import com.clinica.clinica.enumTypes.Especialidades;
import com.clinica.clinica.enumTypes.PermissoesEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name = "Medicos")
@ToString
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigo;
    private String nome;
    private String sobrenome;
    private String senha;
    private String email;
    private Long celular;
    private int idade;
    private LocalDate dataNascimento;
    private String cpf;
    @Enumerated(EnumType.STRING)
    @Column(name = "cidade_nascimento")
    private CidadesEnum cidadesEnum;
     @Enumerated(EnumType.STRING)
    @Column(name = "especialidade")
    private Especialidades especialidades;
    @Lob
    @Column(length = 5242880)
    private byte[] foto;
    @OneToMany
    private List <Paciente> paciente;
    @Enumerated(EnumType.STRING)
    private PermissoesEnum permissao;
   
}
