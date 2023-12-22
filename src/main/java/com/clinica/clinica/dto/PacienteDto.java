package com.clinica.clinica.dto;

import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;

import com.clinica.clinica.entities.Paciente;
import com.clinica.clinica.enumTypes.CidadesEnum;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PacienteDto {
    @NotBlank(message = "O nome não pode ser vazio")
    private String nome;
    @NotBlank(message = "O sobrenome não pode ser vazio")
    private String sobrenome;
    private String senha;
    @CPF
    private String cpf;
    @Email
    private String email;
    @NotNull
    private int celular;

    @NotNull(message = "a Data não pode ser vazia")
    @Past(message = "Informe a data certa de seu nascimento")
    private LocalDate dataNascimento;
    private CidadesEnum cidadesEnum;

    public Paciente toCliente() {
        Paciente paciente = new Paciente();
        paciente.setNome(this.nome);
        paciente.setSobrenome(this.sobrenome);
        paciente.setSenha(this.senha);
        paciente.setCpf(this.cpf);
        paciente.setEmail(this.email);
        paciente.setCelular(this.celular);
        paciente.setCidadesEnum(this.cidadesEnum);
        paciente.setDataNascimento(this.dataNascimento);
        return paciente;
    }
}
