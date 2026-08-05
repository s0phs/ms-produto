package com.github.s0phs.ms.produto.service;

import com.github.s0phs.ms.produto.dto.CategoriaRequestDTO;
import com.github.s0phs.ms.produto.dto.CategoriaResponseDTO;
import com.github.s0phs.ms.produto.entities.Categoria;
import com.github.s0phs.ms.produto.exceptions.DatabaseException;
import com.github.s0phs.ms.produto.exceptions.ResourceNotFoundException;
import com.github.s0phs.ms.produto.repositories.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> findAllCategorias(){

        return categoriaRepository.findAll().stream().map(CategoriaResponseDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public CategoriaResponseDTO findCategoriaById(Long id){

        Categoria categoria = categoriaRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado. ID: " + id)
        );

        return new CategoriaResponseDTO(categoria);
    }

    @Transactional
    //ATENÇÃO -- passa o REQUEST como parametro
    public CategoriaResponseDTO saveCategoria(CategoriaRequestDTO inputDTO){
        Categoria categoria = new Categoria();
        copyDtoToCategoria(inputDTO, categoria);
        categoria = categoriaRepository.save(categoria);

        return new CategoriaResponseDTO(categoria);
    }

    //ATENÇÃO -- passa o REQUEST como parametro
    private void copyDtoToCategoria(CategoriaRequestDTO inputDTO, Categoria categoria) {
        categoria.setNome(inputDTO.getNome());
    }

    @Transactional
    //ATENÇÃO -- passa o REQUEST como parametro
    public CategoriaResponseDTO updateCategoria(Long id, CategoriaRequestDTO inputDTO){

        try{
            Categoria categoria = categoriaRepository.getReferenceById(id);
            copyDtoToCategoria(inputDTO, categoria);
            categoria = categoriaRepository.save(categoria);

            return new CategoriaResponseDTO(categoria);
        }catch(EntityNotFoundException ex){
            throw new ResourceNotFoundException("Recurso não encontrado. ID: " + id);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteCategoriaById(Long id){
        if(!categoriaRepository.existsById(id)){
            throw new ResourceNotFoundException("Recurso não encontrado. ID: " + id);
        }

        try {
            categoriaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Não foi possivel excluir a categoria. " + "Existem produtos associados a ela.");
        }
    }

}
