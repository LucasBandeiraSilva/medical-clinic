package com.clinica.clinica.dto;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import com.clinica.clinica.entities.Estoque;
import com.clinica.clinica.enumTypes.TipoServico;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstoqueDto {
    @NotBlank(message = "Nome não pode ficar vazio")
    @Length(min = 3,max = 100,message = "Nome deve ser maior do que 3 e menor do que 100 caracteres")
    private String nome;
    @NotBlank(message = "Você precisa fornecer uma descrição")
    private String Descricao;
    @NotNull(message = "você deve fornecer um valor")
    private BigDecimal valor;
    @Lob
    @Column(length = 5242880)
    private byte[] foto;
    @Enumerated(EnumType.STRING)
    private TipoServico tipoServico;

    public Estoque toEstoque(){
        Estoque estoque = new Estoque();
        estoque.setNome(this.nome);
        estoque.setDescricao(this.Descricao);
        estoque.setValor(this.valor);
        estoque.setFoto(this.foto);
        estoque.setTipoServico(this.tipoServico);
        return estoque;
    }
}
