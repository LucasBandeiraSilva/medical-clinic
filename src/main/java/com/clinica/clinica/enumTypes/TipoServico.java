package com.clinica.clinica.enumTypes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoServico {
    ODONTOLOGIA("Veja nosssos serviços dentários","odontologia"),
    DERMATOLOGIA("Veja nosssos serviços dermatologicos","dermatologia"),
    PEDIATRIA("Veja nossos serviços pediatras","pediatria"),
    GINECOLOGIA("Consultas ginecologicas e obstetricas","ginecologia"),
    NEUROLOGIA("Nossa equipe de neurologistas oferece atendimento especializado em doenças neurologicas","neurologia");
    private String Descricao;
    private String nome;
}
