package com.clinica.clinica.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.clinica.clinica.dto.AdminDto;
import com.clinica.clinica.dto.MedicoDto;
import com.clinica.clinica.entities.Admin;
import com.clinica.clinica.entities.Medico;
import com.clinica.clinica.enumTypes.CidadesEnum;
import com.clinica.clinica.enumTypes.Especialidades;
import com.clinica.clinica.enumTypes.PermissoesEnum;
import com.clinica.clinica.repository.AdminRepository;
import com.clinica.clinica.repository.MedicoRepository;

import jakarta.validation.Valid;

@Service
public class BackOfficeService {

     @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private AdminRepository adminRepository;

    public ModelAndView salvaAdimin(@Valid @ModelAttribute("admin") AdminDto adminDto, BindingResult result) {
        ModelAndView mv = new ModelAndView();
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors().toString());
            mv.setViewName("backoffice/cadastro-admin");
            return mv;
        }
        Admin admin = adminDto.toAdmin();
        adminRepository.save(admin);
        mv.setViewName("backoffice/login");
        return mv;

    }

    private BCryptPasswordEncoder enconder = new BCryptPasswordEncoder();
    
    public ModelAndView salvarCadastro(@Valid @ModelAttribute("medico") MedicoDto medicoDto, BindingResult result,
            @RequestParam("fileProduto") MultipartFile file) {
        ModelAndView mv = new ModelAndView();

        if (medicoRepository.existsByCpfOrEmail(medicoDto.getCpf(), medicoDto.getEmail())) {
            mv.addObject("mensagemErro", "CPF e/ou e-mail já cadastrado!");
        }
        if (medicoRepository.existsByCelular(medicoDto.getCelular())) {
            mv.addObject("celularErro", "Número de celular já existente!");
        }

        if (result.hasErrors()) {
            System.out.println("tem erros");
            System.out.println(result.getAllErrors());

            mv.setViewName("backoffice/cadastro");
            mv.addObject("estados", CidadesEnum.values());
            mv.addObject("especialidades", Especialidades.values());

            return mv;
        }
        Medico medico = medicoDto.toMedico();
        try {
            medico.setFoto(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String senhaHash = medico.getSenha();
        medico.setSenha(enconder.encode(senhaHash));
        medico.setPermissao(PermissoesEnum.MEDICO);
        medicoRepository.save(medico);
        System.out.println("campos: " + medico.toString());

        mv.setViewName("backoffice/home");
        return mv;

    }

    public byte[] fotoMedico(@PathVariable Long id) {
        Medico medico = this.medicoRepository.getReferenceById(id);
        return medico.getFoto();
    }

    public ModelAndView listarMedicos() {
        ModelAndView mv = new ModelAndView();
        List<Medico> medicos = this.medicoRepository.findAll();
        mv.setViewName("backoffice/listagemMedicos");
        mv.addObject("medicos", medicos);
        return mv;
    }
}
