package com.github.s0phs.ms.produto.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")

@Entity
@Table(name = "tb_categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    //relacionamento
    @OneToMany(mappedBy = "categoria")//essa string precisa ser igual a Categoria na entidade Produto
    private List<Produto> produtos = new ArrayList<>();
}
