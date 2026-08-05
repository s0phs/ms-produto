package com.github.s0phs.ms.produto.service;

import com.github.s0phs.ms.produto.dto.ProdutoRequestDTO;
import com.github.s0phs.ms.produto.dto.ProdutoResponseDTO;
import com.github.s0phs.ms.produto.entities.Categoria;
import com.github.s0phs.ms.produto.entities.Produto;
import com.github.s0phs.ms.produto.exceptions.DatabaseException;
import com.github.s0phs.ms.produto.exceptions.ResourceNotFoundException;
import com.github.s0phs.ms.produto.repositories.CategoriaRepository;
import com.github.s0phs.ms.produto.repositories.ProdutoRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired //para não ter que criar uma variavel final e colocar no construtor
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)//para abrir uma transação apenas de leitura no banco de dados
    public List<ProdutoResponseDTO> findAllProdutos(){

        List<Produto> produtos = produtoRepository.findAll();

        return produtos.stream().map(ProdutoResponseDTO::new).toList();
    }

    public ProdutoResponseDTO findProdutoById(Long id){

        Produto produto = produtoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado. ID: " + id)
        );

        return new ProdutoResponseDTO(produto);
    }

    @Transactional
    public ProdutoResponseDTO saveProduto (ProdutoRequestDTO requestDTO){

            Produto produto = new Produto();
            //metodo auxiliar para converter DTO para Entidade Produto
            copyDtoToProduto(requestDTO, produto);
            produto = produtoRepository.save(produto);
            return new ProdutoResponseDTO(produto);

    }

    private void copyDtoToProduto(ProdutoRequestDTO requestDTO, Produto produto){

        produto.setNome(requestDTO.getNome());
        produto.setDescricao(requestDTO.getDescricao());
        produto.setValor(requestDTO.getValor());

        //Objeto completo gerenciado
        //Categoria categoria = categoriaRepository.getReferenceById(requestDTO.getCategoria().getId());

        Categoria categoria = categoriaRepository.findById(requestDTO.getCategoriaId()).orElseThrow(
                () -> new DatabaseException("Não foi possivel salvar Produto. Categoria inexistente" +
                        "(ID: " + requestDTO.getCategoriaId() + ")")
        );

        produto.setCategoria(categoria);
    }

    @Transactional
    public ProdutoResponseDTO updateProduto(Long id, ProdutoRequestDTO requestDTO){

        try {
            Produto produto = produtoRepository.getReferenceById(id);//pega a referencia do produto pelo id
            copyDtoToProduto(requestDTO,produto);//faz as setagens dos novos valores
            produto = produtoRepository.save(produto);//salva
            return new ProdutoResponseDTO(produto);//retorna o produto modificado
        }catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado. ID: " + id);//caso não exista o produto
        }catch (DataIntegrityViolationException e){
            throw new DatabaseException("Não foi possivel salvar Produto. Categoria inexistente " + "(ID " + requestDTO.getCategoriaId() + ")");
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
