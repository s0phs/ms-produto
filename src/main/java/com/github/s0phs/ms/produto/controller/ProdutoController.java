package com.github.s0phs.ms.produto.controller;


import com.github.s0phs.ms.produto.dto.ProdutoDTO;
import com.github.s0phs.ms.produto.dto.ProdutoInputDTO;
import com.github.s0phs.ms.produto.dto.ProdutoResponseDTO;
import com.github.s0phs.ms.produto.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> getAllProdutos(){

        List<ProdutoDTO> list = produtoService.findAllProdutos();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> getProdutoById(@PathVariable Long id){

        ProdutoDTO produtoDTO = produtoService.findProdutoById(id);

        return ResponseEntity.ok(produtoDTO);
    }
}

/*
@GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> getProduto(){

        List<ProdutoResponseDTO> dto = ProdutoResponseDTO.createMock();
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> createProduto(@RequestBody ProdutoInputDTO inputDTO) {

            ProdutoResponseDTO dto = new ProdutoResponseDTO(1L, inputDTO.getNome(), inputDTO.getDescricao(), inputDTO.getValor());
            return ResponseEntity.created(null).body(dto);
    }
*/




