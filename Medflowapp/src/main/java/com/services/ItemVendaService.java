package com.services;

import com.domains.ItemVenda;
import com.domains.Medicamento;
import com.domains.dtos.ItemVendaDTO;
import com.mappers.ItemVendaMapper;
import com.repositories.ItemVendaRepository;
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
public class ItemVendaService {

    private static final int MAX_PAGE_SIZE = 200;

    private final ItemVendaRepository itemVendaRepo;
    private final MedicamentoRepository medicamentoRepo;

    public ItemVendaService(ItemVendaRepository itemVendaRepo,
                            MedicamentoRepository medicamentoRepo) {
        this.itemVendaRepo = itemVendaRepo;
        this.medicamentoRepo = medicamentoRepo;
    }


    @Transactional
    public List<ItemVendaDTO> findAll() {
        return ItemVendaMapper.toDtoList(itemVendaRepo.findAll());
    }

    @Transactional
    public Page<ItemVendaDTO> findAll(Pageable pageable) {

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

        Page<ItemVenda> page = itemVendaRepo.findAll(effective);
        return ItemVendaMapper.toDtoPage(page);
    }

    @Transactional
    public ItemVendaDTO findById(Long id) {

        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id do item de venda é obrigatório");
        }

        return itemVendaRepo.findById(id)
                .map(ItemVendaMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("ItemVenda não encontrado: id=" + id));
    }


    @Transactional
    public ItemVendaDTO create(ItemVendaDTO dto) {

        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do item de venda são obrigatórios");
        }

        dto.setId(null);

        Medicamento medicamento = medicamentoRepo.findById(dto.getMedicamentoId())
                .orElseThrow(() ->
                        new ObjectNotFoundException("Medicamento não encontrado: id=" + dto.getMedicamentoId()));

        ItemVenda itemVenda;

        try {
            itemVenda = ItemVendaMapper.toEntity(dto, medicamento);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        return ItemVendaMapper.toDto(itemVendaRepo.save(itemVenda));
    }


    @Transactional
    public ItemVendaDTO update(Long id, ItemVendaDTO dto) {

        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do item de venda são obrigatórios");
        }

        itemVendaRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("ItemVenda não encontrado: id=" + id));

        Medicamento medicamento = medicamentoRepo.findById(dto.getMedicamentoId())
                .orElseThrow(() ->
                        new ObjectNotFoundException("Medicamento não encontrado: id=" + dto.getMedicamentoId()));

        dto.setId(id);

        ItemVenda itemVenda;

        try {
            itemVenda = ItemVendaMapper.toEntity(dto, medicamento);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        return ItemVendaMapper.toDto(itemVendaRepo.save(itemVenda));
    }


    @Transactional
    public void delete(Long id) {

        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        ItemVenda itemVenda = itemVendaRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("ItemVenda não encontrado: id=" + id));

        itemVendaRepo.delete(itemVenda);
    }
}
