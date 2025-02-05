package com.projecttattoo.BrenoLendaTattoo.dto.produto;

import org.springframework.web.multipart.MultipartFile;

public record ResponseProdutoDto(Integer id, String imagem, String nome, Double largura, Double altura, String descricao, Double valor) {

}
