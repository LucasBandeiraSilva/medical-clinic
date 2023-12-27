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

import com.clinica.clinica.dto.EstoqueDto;
import com.clinica.clinica.enumTypes.TipoServico;
import com.clinica.clinica.services.EstoqueService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/estoque")
public class EstoqueController {

    @Autowired
    private EstoqueService estoqueService;

    @GetMapping("/cadastro")
    public ModelAndView cadastrarProdutModelAndView() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("estoque", new EstoqueDto());
        mv.addObject("tipoServico", TipoServico.values());
        mv.setViewName("estoque/cadastroEstoque");
        return mv;
    }

    @PostMapping("/salvar-produto")
    public ModelAndView salvarProduto(@Valid @ModelAttribute("estoque") EstoqueDto estoqueDto,
            @RequestParam("fileProduto") MultipartFile file, BindingResult result) {
        return estoqueService.salvarProduto(estoqueDto, file, result);

    }

    @GetMapping("/foto-produto/{id}")
    @ResponseBody
    public byte[] fotoMedico(@PathVariable Long id) {
        return estoqueService.fotoMedico(id);
    }

    @GetMapping("/lista")
    public ModelAndView listaDeEstoques() {
        return estoqueService.listaDeEstoques();
    }
    @GetMapping("/{servicos}")
    public ModelAndView catalogo(@PathVariable String servicos){
        return estoqueService.catalogo(servicos);
    }
}