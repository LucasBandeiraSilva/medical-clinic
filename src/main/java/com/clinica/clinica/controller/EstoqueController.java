package com.clinica.clinica.controller;

import java.io.IOException;
import java.util.List;

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

import com.clinica.clinica.dto.EstoqueDto;
import com.clinica.clinica.entities.Estoque;
import com.clinica.clinica.repository.EstoqueRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/estoque")
public class EstoqueController {

    @Autowired
    private EstoqueRepository estoqueRepository;

    @GetMapping("/cadastro")
    public ModelAndView cadastrarProdutModelAndView() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("estoque", new EstoqueDto());
        mv.setViewName("estoque/cadastroEstoque");
        return mv;
    }

    @PostMapping("/salvar-produto")
    public ModelAndView salvarProduto(@Valid @ModelAttribute("estoque") EstoqueDto estoqueDto,
            @RequestParam("fileEstoque") MultipartFile file, BindingResult result) {
                ModelAndView mv = new ModelAndView();
        if (result.hasErrors()) {
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

    @GetMapping("/foto-produto/{id}")
    @ResponseBody
    public byte[] fotoMedico(@PathVariable Long id) {
        Estoque estoque = this.estoqueRepository.getReferenceById(id);
        return estoque.getFoto();
    }
    @GetMapping("/lista")
    public ModelAndView listaDeEstoques(){
        List<Estoque> listaEstoques = this.estoqueRepository.findAllByOrderByNomeAsc();
        ModelAndView mv = new ModelAndView("estoque/listagemEstoque");
        mv.addObject("estoque",listaEstoques );
        return mv;

}
}