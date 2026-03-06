package com.github.s0phs.ms.produto.dto;

import com.github.s0phs.ms.produto.entities.Categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CategoriaDTO {
    private Long id;

    @NotBlank(message = "Campo nome não pode ser vazio, nulo ou em branco")
    @Size(min = 1, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    public CategoriaDTO(Categoria categoria) {
       id = categoria.getId();
       nome = categoria.getNome();
    }
}
