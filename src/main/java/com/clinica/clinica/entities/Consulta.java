package com.clinica.clinica.entities;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "consultas")
@Getter
@Setter
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tipoConsulta;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataConsulta;
    @ManyToOne
    private Medico medicos;
    @ManyToOne
    private Paciente pacientes;
}
