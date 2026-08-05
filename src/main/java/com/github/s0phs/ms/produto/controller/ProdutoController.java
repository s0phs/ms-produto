package com.github.s0phs.ms.produto.controller;


import com.github.s0phs.ms.produto.dto.ProdutoRequestDTO;
import com.github.s0phs.ms.produto.dto.ProdutoResponseDTO;
import com.github.s0phs.ms.produto.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    //forçando erro 500 para testes
//    @Profile("test")
//    @GetMapping("/--demo/500")
//    public String force500(){
//        throw new RuntimeException("Erro 500 forçado para demonstração");
//    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> getAllProdutos(){

        List<ProdutoResponseDTO> list = produtoService.findAllProdutos();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> getProdutoById(@PathVariable Long id){

        ProdutoResponseDTO produtoDTO = produtoService.findProdutoById(id);

        return ResponseEntity.ok(produtoDTO);
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> createProduto(@RequestBody @Valid ProdutoRequestDTO inputDTO){//@Valid para fazer as validações do DTO

        ProdutoResponseDTO produtoDTO = produtoService.saveProduto(inputDTO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(produtoDTO.getId())
                .toUri();

        //.created = 201
        return ResponseEntity.created(uri).body(produtoDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> updateProduto(@PathVariable Long id, @RequestBody @Valid ProdutoRequestDTO inputDTO){

        ProdutoResponseDTO produtoDTO = produtoService.updateProduto(id, inputDTO);

        //.ok = 200
        return ResponseEntity.ok(produtoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id){

        produtoService.deleteProdutoById(id);

        //.noContent = 204
        return ResponseEntity.noContent().build();//.build para construir a requisicao
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




