package com.github.s0phs.ms.produto.repositories;

import com.github.s0phs.ms.produto.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
