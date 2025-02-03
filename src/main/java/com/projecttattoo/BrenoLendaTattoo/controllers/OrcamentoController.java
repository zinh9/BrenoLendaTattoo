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

    @GetMapping("/novo_orcamento")
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
    
    @GetMapping("/admin-orcamentos")
    public String listarOrcamentosAdmin(Model model) {
        ResponseEntity<List<ResponseOrcamentoDto>> response = orcamentoService.getAll();
        if (response.getStatusCode().is2xxSuccessful()) {
            model.addAttribute("orcamentos", response.getBody());
        }
        return "admin_orcamentos";
    }

    @GetMapping("/{id}/editar")
    public String exibirFormularioEdicao(@PathVariable Integer id, Model model) {
        ResponseEntity<ResponseOrcamentoDto> response = orcamentoService.getById(id);
        if (response.getStatusCode().is2xxSuccessful()) {
            model.addAttribute("orcamento", response.getBody());
            return "atualizar_orcamento"; // Retorna o template de edição
        }
        return "redirect:/orcamentos/meus-orcamentos";
    }

    @PostMapping("/{id}/editar-orcamento")
    public String atualizarOrcamento(
        @PathVariable Integer id,
        @ModelAttribute RequestOrcamentoDto requestOrcamentoDto, // Substitui @RequestParam por @ModelAttribute
        Model model
    ) {
    	System.out.println(requestOrcamentoDto.altura());
    	System.out.println(requestOrcamentoDto.largura());
    	System.out.println(requestOrcamentoDto.imagem());
        ResponseEntity<ResponseOrcamentoDto> response = orcamentoService.update(id, requestOrcamentoDto);

        if (response.getStatusCode().is2xxSuccessful()) {
            model.addAttribute("message", "Orçamento atualizado com sucesso!");
        } else {
            model.addAttribute("error", "Erro ao atualizar o orçamento.");
        }
        return "redirect:/orcamentos/meus-orcamentos";
    }
    
    // @PreAuthorize("hasRole('ADMIN')")
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
    
    @GetMapping("/historico")
    public String historicoTattoo(Model model) {
    	ResponseEntity<List<ResponseOrcamentoDto>> response = orcamentoService.getAll();
    	if(response.getStatusCode().is2xxSuccessful()) {
    		model.addAttribute("orcamentos", response.getBody());
    	}
    	return "historico";
    }
    
    // Precisa mudar algumas coisas como: quando o admin agenda um orçamento, ele tem que ser redirecionado para a tela de "admin_orcamentos"
}