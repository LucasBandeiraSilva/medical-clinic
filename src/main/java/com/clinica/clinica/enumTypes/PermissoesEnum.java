package com.clinica.clinica.enumTypes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PermissoesEnum {
    ADMINISTRADOR("Administrador"),
    MEDICO("Medico");
    private String nome;

}
