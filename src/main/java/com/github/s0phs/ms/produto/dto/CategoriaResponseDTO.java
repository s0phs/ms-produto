package com.github.s0phs.ms.produto.dto;

import com.github.s0phs.ms.produto.entities.Categoria;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//Response não tem validação
public class CategoriaResponseDTO {
    private Long id;

    private String nome;

    public CategoriaResponseDTO(Categoria categoria) {
       id = categoria.getId();
       nome = categoria.getNome();
    }
}
