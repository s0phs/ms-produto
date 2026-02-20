package com.github.s0phs.ms.produto.service;

import com.github.s0phs.ms.produto.dto.ProdutoDTO;
import com.github.s0phs.ms.produto.entities.Produto;
import com.github.s0phs.ms.produto.repositories.ProdutoRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired //para não ter que criar uma variavel final e colocar no construtor
    private ProdutoRepository produtoRepository;

    @Transactional(readOnly = true)//para abrir uma transação apenas de leitura no banco de dados
    public List<ProdutoDTO> findAllProdutos(){

        List<Produto> produtos = produtoRepository.findAll();

        return produtos.stream().map(ProdutoDTO::new).toList();
    }

    public ProdutoDTO findProdutoById(Long id){

        Produto produto = produtoRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Recurso não encontrado. ID: " + id)
        );

        return new ProdutoDTO(produto);
    }

}
