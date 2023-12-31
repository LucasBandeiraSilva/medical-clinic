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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.clinica.clinica.dto.AdminDto;
import com.clinica.clinica.dto.MedicoDto;
import com.clinica.clinica.entities.Admin;
import com.clinica.clinica.enumTypes.CidadesEnum;
import com.clinica.clinica.enumTypes.Especialidades;
import com.clinica.clinica.services.BackOfficeService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/backoffice")
public class BackOfficeController {

    @Autowired
    private BackOfficeService backOfficeService;

    @GetMapping("/")
    public String home(HttpSession session) {
        Admin admin = (Admin)  session.getAttribute("usuarioAdmin");
        if(admin == null){
            return "backoffice/login";
        }
        return "backoffice/home";
    }

    @GetMapping("/login")
    public String login() {
        return "backoffice/login";
    }
    @PostMapping("/login/admin")
    public ModelAndView loginAdmin(@RequestParam("email") String email, @RequestParam("senha") String senha,RedirectAttributes redirectAttributes,HttpSession session){
        return backOfficeService.loginAdmin(email, senha, redirectAttributes,session);
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
    @GetMapping("/editar-medico/{id}")
    public ModelAndView editarMedico(@PathVariable Long id, RedirectAttributes redirectAttributes){
        return  backOfficeService.editarMedico(id, redirectAttributes);
    }
    @PostMapping("/Salvar-edicao/{id}")
    public ModelAndView salvarEdicao(@Valid @PathVariable Long id, MedicoDto medicoDto, BindingResult result){
        return backOfficeService.salvarEdicao(id,medicoDto,result);
    }
    @GetMapping("/excluir-medico/{id}")
    public ModelAndView deletarPorID(@PathVariable Long id,RedirectAttributes redirectAttributes){
        return backOfficeService.deletarPorID(id,redirectAttributes);
    }

}
