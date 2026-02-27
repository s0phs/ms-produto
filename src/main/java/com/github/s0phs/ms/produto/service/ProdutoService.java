package com.github.s0phs.ms.produto.service;

import com.github.s0phs.ms.produto.dto.ProdutoDTO;
import com.github.s0phs.ms.produto.entities.Produto;
import com.github.s0phs.ms.produto.exceptions.ResourceNotFoundException;
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
                () -> new ResourceNotFoundException("Recurso não encontrado. ID: " + id)
        );

        return new ProdutoDTO(produto);
    }

    @Transactional
    public ProdutoDTO saveProduto (ProdutoDTO produtoDTO){

        Produto produto = new Produto();

        //metodo auxiliar para converter DTO para Entidade Produto
        copyDtoToProduto(produtoDTO, produto);
        produto = produtoRepository.save(produto);
        return new ProdutoDTO(produto);
    }

    private void copyDtoToProduto(ProdutoDTO produtoDTO, Produto produto){

        produto.setNome(produtoDTO.getNome());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setValor(produtoDTO.getValor());
    }

    @Transactional
    public ProdutoDTO updateProduto(Long id, ProdutoDTO produtoDTO){

        try {
            Produto produto = produtoRepository.getReferenceById(id);//pega a referencia do produto pelo id
            copyDtoToProduto(produtoDTO,produto);//faz as setagens dos novos valores
            produto = produtoRepository.save(produto);//salva
            return new ProdutoDTO(produto);//retorna o produto modificado
        }catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado. ID: " + id);//caso não exista o produto
        }
    }

    @Transactional
    public void deleteProdutoById(Long id){

        if(!produtoRepository.existsById(id)){
            throw new ResourceNotFoundException("Recurso não encontrado. ID: " + id);
        }

        produtoRepository.deleteById(id);
    }

}
