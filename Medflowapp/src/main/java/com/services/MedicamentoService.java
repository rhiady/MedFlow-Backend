package com.services;

import com.domains.Fornecedor;
import com.domains.Medicamento;
import com.domains.dtos.MedicamentoDTO;
import com.mappers.MedicamentoMapper;
import com.repositories.FornecedorRepository;
import com.repositories.MedicamentoRepository;
import com.services.exceptions.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MedicamentoService {

    private static final int MAX_PAGE_SIZE = 200;

    private final MedicamentoRepository medicamentoRepo;
    private final FornecedorRepository fornecedorRepo;

    public MedicamentoService(MedicamentoRepository medicamentoRepo,
                              FornecedorRepository fornecedorRepo) {
        this.medicamentoRepo = medicamentoRepo;
        this.fornecedorRepo = fornecedorRepo;
    }

    @Transactional
    public List<MedicamentoDTO> findAll() {
        return MedicamentoMapper.toDtoList(medicamentoRepo.findAll());
    }

    @Transactional
    public Page<MedicamentoDTO> findAll(Pageable pageable) {

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

        Page<Medicamento> page = medicamentoRepo.findAll(effective);

        return MedicamentoMapper.toDtoPage(page);
    }

    @Transactional
    public MedicamentoDTO findById(Long id) {

        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id do medicamento é obrigatório");
        }

        return medicamentoRepo.findById(id)
                .map(MedicamentoMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Medicamento não encontrado: id=" + id));
    }


    @Transactional
    public MedicamentoDTO create(MedicamentoDTO dto) {

        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do medicamento são obrigatórios");
        }

        dto.setId(null);

        Fornecedor fornecedor = null;

        if (dto.getFornecedorId() != null) {
            fornecedor = fornecedorRepo.findById(dto.getFornecedorId())
                    .orElseThrow(() ->
                            new ObjectNotFoundException("Fornecedor não encontrado: id=" + dto.getFornecedorId()));
        }

        Medicamento medicamento;

        try {
            medicamento = MedicamentoMapper.toEntity(dto, fornecedor);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        return MedicamentoMapper.toDto(medicamentoRepo.save(medicamento));
    }

    @Transactional
    public MedicamentoDTO update(Long id, MedicamentoDTO dto) {

        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do medicamento são obrigatórios");
        }

        Medicamento medicamento = medicamentoRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Medicamento não encontrado: id=" + id));

        Fornecedor fornecedor = null;

        if (dto.getFornecedorId() != null) {
            fornecedor = fornecedorRepo.findById(dto.getFornecedorId())
                    .orElseThrow(() ->
                            new ObjectNotFoundException("Fornecedor não encontrado: id=" + dto.getFornecedorId()));
        }

        dto.setId(id);

        try {
            MedicamentoMapper.copyToEntity(dto, medicamento, fornecedor);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        return MedicamentoMapper.toDto(medicamentoRepo.save(medicamento));
    }


    @Transactional
    public void delete(Long id) {

        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        Medicamento medicamento = medicamentoRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Medicamento não encontrado: id=" + id));

        medicamentoRepo.delete(medicamento);
    }
}
