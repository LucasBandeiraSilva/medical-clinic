package com.clinica.clinica.dto;

import org.hibernate.validator.constraints.br.CPF;

import com.clinica.clinica.entities.Admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "O campo CPF é obrigatório")
    @CPF(message = "CPF invalido")
    private String cpf;
   

    public Admin toAdmin(){
        Admin admin = new Admin();
        admin.setNome(this.nome);
        admin.setSobrenome(this.sobrenome);
        admin.setSenha(this.senha);
        admin.setEmail(this.email);
        return admin;
    }
}
