package com.clinica.clinica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.clinica.clinica.dto.PacienteDto;
import com.clinica.clinica.enumTypes.CidadesEnum;
import com.clinica.clinica.enumTypes.TipoServico;
import com.clinica.clinica.services.ClienteService;

import jakarta.validation.Valid;

@Controller
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/")
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
    public ModelAndView catalogo() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("servicos", TipoServico.values());
        mv.setViewName("cliente/catalogo-consulta");
        return mv;
    }

}
