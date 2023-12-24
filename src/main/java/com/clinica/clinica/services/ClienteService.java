package com.clinica.clinica.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.clinica.clinica.dto.PacienteDto;
import com.clinica.clinica.entities.Paciente;
import com.clinica.clinica.enumTypes.CidadesEnum;
import com.clinica.clinica.repository.ClienteRepository;

import jakarta.validation.Valid;

@Service
public class ClienteService {

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    @Autowired
    private ClienteRepository clienteRepository;

    public ModelAndView salvarCadastroCliente(@Valid @ModelAttribute("cliente") PacienteDto clienteDto,
            BindingResult result, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView();
        Paciente cliente = clienteDto.toCliente();
        if (result.hasErrors()) {
            System.out.println("tem erros");
            System.out.println(result.getAllErrors());
            mv.setViewName("cliente/form-cadastro");
            mv.addObject("estados", CidadesEnum.values());
            mv.addObject("estados", CidadesEnum.values());

            return mv;
        } else {
            String senhaCriptografada = encoder.encode(cliente.getSenha());
            cliente.setSenha(senhaCriptografada);
            clienteRepository.save(cliente);
            System.out.println("cliente salvo");
            redirectAttributes.addFlashAttribute("mensagem", cliente.getNome() + " Sua conta criada com sucesso!!");
            return new ModelAndView("redirect:/catalogo");
        }

    }
}
