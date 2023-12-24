package com.clinica.clinica.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.clinica.clinica.dto.EstoqueDto;
import com.clinica.clinica.entities.Estoque;
import com.clinica.clinica.enumTypes.TipoServico;
import com.clinica.clinica.repository.EstoqueRepository;

import jakarta.validation.Valid;

@Service
public class EstoqueService {
    @Autowired
    private EstoqueRepository estoqueRepository;

    public ModelAndView salvarProduto(@Valid @ModelAttribute("estoque") EstoqueDto estoqueDto,
            @RequestParam("fileEstoque") MultipartFile file, BindingResult result) {
        ModelAndView mv = new ModelAndView();
        if (result.hasErrors()) {
            mv.addObject("tipoServico", TipoServico.values());
            return new ModelAndView("estoque/cadastroEstoque");

        }
        Estoque estoque = estoqueDto.toEstoque();
        try {
            estoque.setFoto(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        estoqueRepository.save(estoque);
        mv.setViewName("redirect:/backoffice");
        return mv;

    }
     public byte[] fotoMedico(@PathVariable Long id) {
        Estoque estoque = this.estoqueRepository.getReferenceById(id);
        return estoque.getFoto();
    }
    public ModelAndView listaDeEstoques() {
        List<Estoque> listaEstoques = this.estoqueRepository.findAllByOrderByNomeAsc();
        ModelAndView mv = new ModelAndView("estoque/listagemEstoque");
        mv.addObject("estoque", listaEstoques);
        return mv;
    }
}
