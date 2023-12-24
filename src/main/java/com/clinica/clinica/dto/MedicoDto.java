package com.clinica.clinica.dto;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.validator.constraints.br.CPF;

import com.clinica.clinica.entities.Medico;
import com.clinica.clinica.entities.Paciente;
import com.clinica.clinica.enumTypes.CidadesEnum;
import com.clinica.clinica.enumTypes.Especialidades;
import com.clinica.clinica.enumTypes.PermissoesEnum;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicoDto {
    @NotBlank(message = "Informe o seu CRO/CRM")
    @Column(unique = true)
    private String codigo;
    @NotBlank(message = "O nome precisa ser informado")
    private String nome;
    @Email(message = "email invalido")
    @NotBlank(message = "Insira um email válido")
    @Column(unique = true)
    private String email;
    @CPF(message = "CPF inválido")
    @Column(unique = true)
    private String cpf;
    @NotBlank(message = "O sobrenome precisa ser informado")
    private String sobrenome;
    @NotBlank(message = "Você precisa informar uma senha")
    private String senha;
    @NotNull(message = "Você precisa informar uma número para contato")
    private Long celular;
    @NotNull(message = "Você precisa informar sua idade")
    @Min(value = 18, message = "Você precisa ter 18 anos de idade para se cadastrar")
    private int idade;
    @Past(message = "Você não pode nascer no futuro")
    private LocalDate dataNascimento;
    @Enumerated(EnumType.STRING)
    private CidadesEnum cidadesEnum;
    @Enumerated(EnumType.STRING)
    private Especialidades especialidades;
    @Lob
    @Column(length = 5242880)
    private byte[] foto;

    private List<Paciente> paciente;
    private List<PermissoesEnum> permissao;
    public Medico toMedico() {
        Medico medico = new Medico();
        medico.setCodigo(this.codigo);
        medico.setNome(this.nome);
        medico.setEmail(this.email);
        medico.setCpf(this.cpf);
        medico.setSobrenome(this.sobrenome);
        medico.setSenha(this.senha);
        medico.setCelular(this.celular);
        medico.setIdade(this.idade);
        medico.setDataNascimento(this.dataNascimento);
        medico.setCidadesEnum(this.cidadesEnum);
        medico.setEspecialidades(this.especialidades);
        medico.setFoto(this.foto);
        return medico;

    }

    public MedicoDto fromMedico(Medico medico) {
        MedicoDto medicoDto = new MedicoDto();
        medicoDto.setCodigo(medico.getCodigo());
        medicoDto.setNome(medico.getNome());
        medicoDto.setEmail(medico.getEmail());
        medicoDto.setCpf(medico.getCpf());
        medicoDto.setSobrenome(medico.getSobrenome());
        medicoDto.setSenha(medico.getSenha());
        medicoDto.setCelular(medico.getCelular());
        medicoDto.setIdade(medico.getIdade());
        medicoDto.setDataNascimento(medico.getDataNascimento());
        medicoDto.setCidadesEnum(medico.getCidadesEnum());
        medicoDto.setEspecialidades(medico.getEspecialidades());
        return medicoDto;
    }

}
