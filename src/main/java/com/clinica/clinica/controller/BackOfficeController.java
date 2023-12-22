package com.clinica.clinica.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

@Controller
@RequestMapping("/backoffice")
public class BackOfficeController {

    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private AdminRepository adminRepository;

    private BCryptPasswordEncoder enconder = new BCryptPasswordEncoder();

    @GetMapping()
    public String home() {
        return "backoffice/home";
    }

    @GetMapping("/login")
    public String login() {
        return "backoffice/login";
    }

    @GetMapping("/cadastro-admin")
    public ModelAndView cadastraAdmin() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("admin", new AdminDto());
        mv.setViewName("backoffice/cadastro-admin");
        return mv;
    }

    @PostMapping("/salvar-admin")
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
    /*
     * Metódos para médicos
     */

    @GetMapping("/cadastro")
    public ModelAndView cadastro() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("backoffice/cadastro");
        mv.addObject("medico", new MedicoDto());
        mv.addObject("estados", CidadesEnum.values());
        mv.addObject("especialidades", Especialidades.values());

        return mv;
    }

    @PostMapping("/Salvar-Cadastro")
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

        String senhaHash = medico.getSenha();
        try {
            medico.setFoto(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        medico.setSenha(enconder.encode(senhaHash));
        medico.setPermissao(PermissoesEnum.MEDICO);
        medicoRepository.save(medico);
        System.out.println("campos: " + medico.toString());

        mv.setViewName("backoffice/home");
        return mv;

    }

    @GetMapping("/foto-medico/{id}")
    @ResponseBody
    public byte[] fotoMedico(@PathVariable Long id) {
        Medico medico = this.medicoRepository.getReferenceById(id);
        return medico.getFoto();
    }

    @GetMapping("/listaMedico")
    public ModelAndView listarMedicosAndView() {
        ModelAndView mv = new ModelAndView();
        List<Medico> medicos = this.medicoRepository.findAll();
        mv.setViewName("backoffice/listagemMedicos");
        mv.addObject("medicos", medicos);
        return mv;
    }

}
