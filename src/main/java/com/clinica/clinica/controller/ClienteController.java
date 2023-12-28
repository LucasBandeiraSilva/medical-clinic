package com.clinica.clinica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.clinica.clinica.dto.PacienteDto;
import com.clinica.clinica.entities.Paciente;
import com.clinica.clinica.enumTypes.CidadesEnum;
import com.clinica.clinica.enumTypes.TipoServico;
import com.clinica.clinica.services.ClienteService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/cadastro")
    public ModelAndView formularioCadastro() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("cliente/form-cadastro");
        mv.addObject("cliente", new PacienteDto());
        mv.addObject("estados", CidadesEnum.values());

        return mv;
    }

    @PostMapping("/cadastroCliente")
    public ModelAndView salvarCadastroCliente(@Valid @ModelAttribute("cliente") PacienteDto clienteDto,
            BindingResult result, RedirectAttributes redirectAttributes) {
        return clienteService.salvarCadastroCliente(clienteDto, result, redirectAttributes);

    }

    @GetMapping("/catalogo")
    public ModelAndView catalogo(HttpSession session) {
        Paciente paciente = (Paciente) session.getAttribute("usuarioLogado");
        ModelAndView mv = new ModelAndView();
        if(paciente != null){
            System.out.println("eu to na sessão principal");
            mv.addObject("paciente", paciente);
        }
        mv.addObject("servicos", TipoServico.values());
        mv.setViewName("cliente/catalogo-consulta");
        return mv;
    }
    @GetMapping("/login")
    public String login(){
        return "cliente/login";
    }
    @PostMapping("/login")
    public ModelAndView loginCliente(@RequestParam("email") String nome, @RequestParam("senha")String senha,HttpSession session, RedirectAttributes redirectAttributes){
        return clienteService.loginCliente(nome, senha, session, redirectAttributes);
    }
}
