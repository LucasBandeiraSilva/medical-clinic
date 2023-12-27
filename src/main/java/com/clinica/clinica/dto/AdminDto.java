package com.clinica.clinica.dto;

import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;

import com.clinica.clinica.entities.Admin;
import com.clinica.clinica.enumTypes.CidadesEnum;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminDto {
   
    @NotBlank(message = "O nome é obrigatório.")
    private String nome;
    @NotBlank(message = "O sobrenome é obrigatório.")
    private String sobrenome;
    @NotBlank(message = "A senha é obrigatória")
    private String senha;
    @Email
    @NotBlank(message = "Informe um e-mail válido.")
    private String email;
    // @NotNull(message = "O número de celular é obrigatório")
    // private Long celular;
    
    @NotBlank(message = "O campo CPF é obrigatório")
    @CPF(message = "CPF invalido")
    private String cpf;
   

    public Admin toAdmin(){
        Admin admin = new Admin();
        admin.setNome(this.nome);
        admin.setSobrenome(this.sobrenome);
        admin.setSenha(this.senha);
        admin.setEmail(this.email);
        // admin.setCelular(this.celular);
        admin.setCpf(this.cpf);
        return admin;
    }
}
