package com.clinica.clinica.enumTypes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Especialidades {
    Cardiologia("Cardiología"), 
    Neurologia("Neurología"), 
    Oncologia("Oncología"),
    Dentista("Denitsta");
    
    private  String nome;
}
