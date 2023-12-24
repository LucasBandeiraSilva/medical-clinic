package com.clinica.clinica.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.clinica.clinica.enumTypes.CidadesEnum;
import com.clinica.clinica.enumTypes.Especialidades;
import com.clinica.clinica.services.BackOfficeService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/backoffice")
public class BackOfficeController {

    @Autowired
    private BackOfficeService backOfficeService;

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
        return backOfficeService.salvaAdimin(adminDto, result);
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
            @RequestParam("fileFoto") MultipartFile file) {
        return backOfficeService.salvarCadastro(medicoDto, result, file);
    }

    @GetMapping("/foto-medico/{id}")
    @ResponseBody
    public byte[] fotoMedico(@PathVariable Long id) {
        return backOfficeService.fotoMedico(id);
    }

    @GetMapping("/listaMedico")
    public ModelAndView listarMedicos() {
        return backOfficeService.listarMedicos();
    }

}
