package com.services;

import com.domains.Fornecedor;
import com.domains.dtos.FornecedorDTO;
import com.mappers.FornecedorMapper;
import com.repositories.FornecedorRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FornecedorService {

    private static final int MAX_PAGE_SIZE = 200;

    private final FornecedorRepository fornecedorRepo;

    public FornecedorService(FornecedorRepository fornecedorRepo) {
        this.fornecedorRepo = fornecedorRepo;
    }


    @Transactional
    public List<FornecedorDTO> findAll() {
        return FornecedorMapper.toDtoList(fornecedorRepo.findAll());
    }

    @Transactional
    public Page<FornecedorDTO> findAll(Pageable pageable) {

        final Pageable effective;

        if (pageable == null || pageable.isUnpaged()) {
            effective = Pageable.unpaged();
        } else {
            effective = PageRequest.of(
                    Math.max(0, pageable.getPageNumber()),
                    Math.min(pageable.getPageSize(), MAX_PAGE_SIZE),
                    pageable.getSort()
            );
        }

        Page<Fornecedor> page = fornecedorRepo.findAll(effective);
        return FornecedorMapper.toDtoPage(page);
    }

    @Transactional
    public FornecedorDTO findById(Long id) {

        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id do fornecedor é obrigatório");
        }

        return fornecedorRepo.findById(id)
                .map(FornecedorMapper::toDto)
                .orElseThrow(() -> new ObjectNotFoundException("Fornecedor", id));
    }

    @Transactional
    public FornecedorDTO create(FornecedorDTO dto) {

        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do fornecedor são obrigatórios");
        }

        dto.setId(null);

        Fornecedor fornecedor;

        try {
            fornecedor = FornecedorMapper.toEntity(dto);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        fornecedor = fornecedorRepo.save(fornecedor);

        return FornecedorMapper.toDto(fornecedor);
    }


    @Transactional
    public FornecedorDTO update(Long id, FornecedorDTO dto) {

        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do fornecedor são obrigatórios");
        }

        fornecedorRepo.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Fornecedor", id));

        dto.setId(id);

        Fornecedor fornecedor;

        try {
            fornecedor = FornecedorMapper.toEntity(dto);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        fornecedor = fornecedorRepo.save(fornecedor);

        return FornecedorMapper.toDto(fornecedor);
    }


    @Transactional
    public void delete(Long id) {

        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        Fornecedor fornecedor = fornecedorRepo.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Fornecedor", id));

        fornecedorRepo.delete(fornecedor);
    }
}
