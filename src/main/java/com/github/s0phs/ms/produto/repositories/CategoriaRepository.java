package com.github.s0phs.ms.produto.repositories;

import com.github.s0phs.ms.produto.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
