package com.clinica.clinica.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.clinica.clinica.dto.AdminDto;
import com.clinica.clinica.dto.MedicoDto;
import com.clinica.clinica.entities.Admin;
import com.clinica.clinica.entities.Medico;
import com.clinica.clinica.enumTypes.CidadesEnum;
import com.clinica.clinica.enumTypes.Especialidades;
import com.clinica.clinica.enumTypes.PermissoesEnum;
import com.clinica.clinica.repository.AdminRepository;
import com.clinica.clinica.repository.MedicoRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class BackOfficeService {

    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private AdminRepository adminRepository;

    private BCryptPasswordEncoder enconder = new BCryptPasswordEncoder();

    public ModelAndView salvaAdimin(AdminDto adminDto, BindingResult result) {
        ModelAndView mv = new ModelAndView();
        if (result.hasErrors()) {
            System.out.println(result.getFieldError().toString());
            mv.setViewName("backoffice/cadastro-admin");
            return mv;
        }
        Admin admin = adminDto.toAdmin();
        String senhaSegura = enconder.encode(admin.getSenha());
        admin.setSenha(senhaSegura);
        adminRepository.save(admin);
        mv.setViewName("backoffice/login");
        return mv;

    }

    public ModelAndView loginAdmin(String email, String senha,
            RedirectAttributes redirectAttributes, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        Admin admin = adminRepository.findByEmail(email);
        if (admin != null && enconder.matches(senha, admin.getSenha())) {
            session.setAttribute("usuarioAdmin", admin);
            mv.setViewName("redirect:/backoffice/");
        } else {
            mv.setViewName("backoffice/login");
            redirectAttributes.addFlashAttribute("mensagemErro", "Usuário ou senha inválidos!");

        }
        return mv;
    }

    // métodos para Médicos

    public ModelAndView salvarCadastro(MedicoDto medicoDto, BindingResult result,
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

    public byte[] fotoMedico(Long id) {
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

    public ModelAndView editarMedico(Long id, RedirectAttributes attributes) {
        Optional<Medico> optionalMedico = medicoRepository.findById(id);
        ModelAndView mv = new ModelAndView();
        if (optionalMedico.isPresent()) {
            mv.addObject("medico", optionalMedico.get());
            mv.addObject("Especialidades", Especialidades.values());
            mv.addObject("estados", CidadesEnum.values());
            mv.setViewName("backoffice/edicaoMedico");
            return mv;
        }
        attributes.addFlashAttribute("mensagemErro", "Este médico não existe.");
        return new ModelAndView("redirect:/backoffice/listaMedico");
    }

    public ModelAndView salvarEdicao(Long id, MedicoDto medicoDto, BindingResult result) {
        Optional<Medico> optionalMedico = medicoRepository.findById(id);
        if (optionalMedico.isPresent()) {
            Medico medico = optionalMedico.get();
            medicoDto.fromMedico(medico);
            medicoRepository.save(medico);
            return new ModelAndView("redirect:/backoffice/listaMedico");
        }
        return new ModelAndView("redirect:/backoffice/listaMedico");

    }

    public ModelAndView deletarPorID(Long id, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView();
        Optional<Medico> medicoOptional = this.medicoRepository.findById(id);
        if (medicoOptional.isPresent()) {
            this.medicoRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("excluido", "Médico excluído com sucesso!");
            mv.setViewName("redirect:/backoffice/listaMedico");
            return mv;
        }
        redirectAttributes.addFlashAttribute("naoExcluido", "Não foi possível encontrar o médico para exclusão.");
        mv.setViewName("redirect:/backoffice/listaMedico");
        return mv;
    }
}
