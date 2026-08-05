package com.github.s0phs.ms.produto.dto;

import com.github.s0phs.ms.produto.entities.Produto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//lombok
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProdutoResponseDTO {
    private Long id;

    private String nome;

    private String descricao;

    private Double valor;//precisa ser Double com letra maiúscula

    //Diferente do REQUEST, aqui precisa ser o objeto e não só o id
    private CategoriaResponseDTO categoria;

    public ProdutoResponseDTO(Produto produto) {
        id = produto.getId();
        nome = produto.getNome();
        descricao = produto.getDescricao();
        valor = produto.getValor();
        categoria = new CategoriaResponseDTO(produto.getCategoria());
    }
}
