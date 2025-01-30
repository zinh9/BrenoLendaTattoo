package com.projecttattoo.BrenoLendaTattoo.controllers;

import java.util.List;
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
import com.projecttattoo.BrenoLendaTattoo.services.OrcamentoService;

@Controller
@RequestMapping("/orcamentos")
public class OrcamentoController {

    @Autowired
    private OrcamentoService orcamentoService;

    @GetMapping("/novo")
    public String exibirFormularioNovoOrcamento(Model model) {
        model.addAttribute("orcamento", new RequestOrcamentoDto(null, null, null, null, null));
        return "novo_orcamento";
    }

    @PostMapping("/novo")
    public String criarOrcamento(
        @RequestParam("imagem") MultipartFile imagem,
        @RequestParam("largura") Double largura,
        @RequestParam("altura") Double altura,
        @RequestParam("parteCorpo") String parteCorpo,
        @RequestParam("descricao") String descricao,
        Model model
    ) {
        RequestOrcamentoDto requestOrcamentoDto = new RequestOrcamentoDto(imagem, largura, altura, parteCorpo, descricao);
        ResponseEntity<ResponseOrcamentoDto> response = orcamentoService.register(requestOrcamentoDto);

        if (response.getStatusCode().is2xxSuccessful()) {
            model.addAttribute("message", "Orçamento salvo com sucesso!");
        } else {
            model.addAttribute("error", "Erro ao salvar o orçamento.");
        }
        return "redirect:/orcamentos/meus-orcamentos";
    }

    @GetMapping("/meus-orcamentos")
    public String listarOrcamentos(Model model) {
        ResponseEntity<List<ResponseOrcamentoDto>> response = orcamentoService.getAll();
        if (response.getStatusCode().is2xxSuccessful()) {
            model.addAttribute("orcamentos", response.getBody());
        }
        return "meus_orcamentos";
    }

    @GetMapping("/{id}/editar")
    public String exibirFormularioEdicao(@PathVariable Integer id, Model model) {
        ResponseEntity<ResponseOrcamentoDto> response = orcamentoService.getById(id);
        if (response.getStatusCode().is2xxSuccessful()) {
            model.addAttribute("orcamento", response.getBody());
            return "editar_orcamento";
        }
        return "redirect:/orcamentos/meus-orcamentos";
    }

    @PostMapping("/{id}/editar")
    public String atualizarOrcamento(@PathVariable Integer id, @ModelAttribute RequestOrcamentoDto orcamento, Model model) {
        ResponseEntity<ResponseOrcamentoDto> response = orcamentoService.update(id, orcamento);
        if (response.getStatusCode().is2xxSuccessful()) {
            model.addAttribute("message", "Orçamento atualizado com sucesso!");
        } else {
            model.addAttribute("error", "Erro ao atualizar o orçamento.");
        }
        return "redirect:/orcamentos/meus-orcamentos";
    }

    @PostMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public String atualizarStatus(@PathVariable Integer id, @RequestParam String newStatus, Model model) {
        ResponseEntity<ResponseOrcamentoDto> response = orcamentoService.updateStatus(id, newStatus);
        if (response.getStatusCode().is2xxSuccessful()) {
            model.addAttribute("message", "Status do orçamento atualizado com sucesso!");
        } else {
            model.addAttribute("error", "Erro ao atualizar o status do orçamento.");
        }
        return "redirect:/orcamentos/meus-orcamentos";
    }
}