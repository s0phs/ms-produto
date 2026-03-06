package com.github.s0phs.ms.produto.dto;

import com.github.s0phs.ms.produto.entities.Produto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

//lombok
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProdutoDTO {
    private Long id;

    //essas validações devem ser feitas no DTO
    @NotBlank(message = "Campo nome é requerido")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotBlank(message = "Campo descrição é requerido")
    @Size(min = 3, max = 100, message = "A descrição deve ter no mínimo 10 caracteres")
    private String descricao;

    @NotNull(message = "O campo valor é requerido")
    @Positive(message = "O campo valor deve ser um número positivo maior que zero")
    private Double valor;//precisa ser Double com letra maiúscula

    @NotNull(message = "Campo categoria é requerido")
    private CategoriaDTO categoria;

    public ProdutoDTO(Produto produto) {
        id = produto.getId();
        nome = produto.getNome();
        descricao = produto.getDescricao();
        valor = produto.getValor();
        categoria = new CategoriaDTO(produto.getCategoria());
    }
}
