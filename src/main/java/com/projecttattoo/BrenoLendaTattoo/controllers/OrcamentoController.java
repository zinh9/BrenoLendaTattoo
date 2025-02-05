package com.projecttattoo.BrenoLendaTattoo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.projecttattoo.BrenoLendaTattoo.dto.orcamento.RequestOrcamentoDto;
import com.projecttattoo.BrenoLendaTattoo.dto.orcamento.ResponseOrcamentoDto;
import com.projecttattoo.BrenoLendaTattoo.models.Orcamento;
import com.projecttattoo.BrenoLendaTattoo.models.Produto;
import com.projecttattoo.BrenoLendaTattoo.repositories.ProdutoRepository;
import com.projecttattoo.BrenoLendaTattoo.services.OrcamentoService;


@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/orcamentos")
public class OrcamentoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private OrcamentoService orcamentoService;

    // Endpoint para exibir o formulário de orçamento (com ou sem produto associado)
    @GetMapping("/novo-orcamento")
    public String exibirFormularioNovoOrcamento(
        @RequestParam(value = "produtoId", required = false) Integer produtoId,
        Model model
    ) {
        Orcamento orcamento = new Orcamento();

        if (produtoId != null) {
            // Busca o produto pelo ID
            Optional<Produto> produtoOpt = produtoRepository.findById(produtoId);
            if (produtoOpt.isPresent()) {
                Produto produto = produtoOpt.get();
                // Pré-preenche os dados do orçamento com base no produto
                orcamento.setImagem(produto.getImagem());
                orcamento.setAltura(produto.getAltura());
                orcamento.setLargura(produto.getLargura());
                orcamento.setDescricao(produto.getDescricao());
                orcamento.setValor(produto.getValor());
                model.addAttribute("orcamento", orcamento); // Passa o produto para o template
            }
        }

        model.addAttribute("orcamento", orcamento);
        return "novo_orcamento"; // Nome do template do formulário de orçamento
    }

    // Endpoint para criar um novo orçamento
    @PostMapping("/novo")
    public String criarOrcamento(
        @RequestParam("imagem") MultipartFile imagem,
        @RequestParam("largura") Double largura,
        @RequestParam("altura") Double altura,
        @RequestParam("parteCorpo") String parteCorpo,
        @RequestParam("descricao") String descricao,
        @RequestParam(value = "produtoId", required = false) Integer produtoId,
        Model model
    ) {
        RequestOrcamentoDto requestOrcamentoDto = new RequestOrcamentoDto(imagem, largura, altura, parteCorpo, descricao, produtoId);
        ResponseEntity<ResponseOrcamentoDto> response;

        if (produtoId != null) {
            // Cria o orçamento com base no produto
            response = orcamentoService.regiterByProduto(requestOrcamentoDto, produtoId);
        } else {
            // Cria o orçamento sem produto associado
            response = orcamentoService.register(requestOrcamentoDto);
        }

        if (response.getStatusCode().is2xxSuccessful()) {
            model.addAttribute("message", "Orçamento salvo com sucesso!");
        } else {
            model.addAttribute("error", "Erro ao salvar o orçamento.");
        }

        return "redirect:/orcamentos/meus-orcamentos";
    }

    // Endpoint para listar os orçamentos do usuário
    @GetMapping("/meus-orcamentos")
    public String listarOrcamentos(Model model) {
        ResponseEntity<List<ResponseOrcamentoDto>> response = orcamentoService.getAll();
        if (response.getStatusCode().is2xxSuccessful()) {
            model.addAttribute("orcamentos", response.getBody());
        }
        return "meus_orcamentos";
    }

    // Endpoint para listar os orçamentos (admin)
    @GetMapping("/admin-orcamentos")
    public String listarOrcamentosAdmin(Model model) {
        ResponseEntity<List<ResponseOrcamentoDto>> response = orcamentoService.getAll();
        if (response.getStatusCode().is2xxSuccessful()) {
            model.addAttribute("orcamentos", response.getBody());
        }
        return "admin_orcamentos";
    }

    // Endpoint para exibir o formulário de edição de orçamento
    @GetMapping("/{id}/editar")
    public String exibirFormularioEdicao(@PathVariable Integer id, Model model) {
        ResponseEntity<ResponseOrcamentoDto> response = orcamentoService.getById(id);
        if (response.getStatusCode().is2xxSuccessful()) {
            model.addAttribute("orcamento", response.getBody());
            return "atualizar_orcamento"; // Retorna o template de edição
        }
        return "redirect:/orcamentos/meus-orcamentos";
    }

    // Endpoint para atualizar um orçamento
    @PostMapping("/{id}/editar-orcamento")
    public String atualizarOrcamento(
        @PathVariable Integer id,
        @ModelAttribute RequestOrcamentoDto requestOrcamentoDto,
        Model model
    ) {
        ResponseEntity<ResponseOrcamentoDto> response = orcamentoService.update(id, requestOrcamentoDto);

        if (response.getStatusCode().is2xxSuccessful()) {
            model.addAttribute("message", "Orçamento atualizado com sucesso!");
        } else {
            model.addAttribute("error", "Erro ao atualizar o orçamento.");
        }
        return "redirect:/orcamentos/meus-orcamentos";
    }

    // Endpoint para concluir um orçamento
    @PostMapping("/{id}/concluir")
    public String concluirOrcamento(@PathVariable Integer id, Model model) {
        ResponseEntity<ResponseOrcamentoDto> response = orcamentoService.updateStatus(id, "Concluído");
        if (response.getStatusCode().is2xxSuccessful()) {
            model.addAttribute("message", "Orçamento concluído com sucesso!");
        } else {
            model.addAttribute("error", "Erro ao concluir o orçamento.");
        }
        return "redirect:/orcamentos/meus-orcamentos";
    }

    // Endpoint para deletar um orçamento
    @PostMapping("/{id}/deletar")
    public String deletarOrcamento(@PathVariable Integer id, Model model) {
        ResponseEntity<String> response = orcamentoService.delete(id);
        if (response.getStatusCode().is2xxSuccessful()) {
            model.addAttribute("message", "Orçamento cancelado com sucesso!");
        } else {
            model.addAttribute("error", "Erro ao cancelar o orçamento.");
        }
        return "redirect:/orcamentos/meus-orcamentos";
    }

    // Endpoint para exibir o histórico de tatuagens
    @GetMapping("/historico")
    public String historicoTattoo(Model model) {
        ResponseEntity<List<ResponseOrcamentoDto>> response = orcamentoService.getAll();
        if (response.getStatusCode().is2xxSuccessful()) {
            model.addAttribute("orcamentos", response.getBody());
        }
        return "historico";
    }
}