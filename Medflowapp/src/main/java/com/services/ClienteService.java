package com.services;

import com.domains.Cliente;
import com.domains.dtos.ClienteDTO;
import com.mappers.ClienteMapper;
import com.repositories.ClienteRepository;
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
public class ClienteService {

    private static final int MAX_PAGE_SIZE = 200;

    private final ClienteRepository clienteRepo;

    public ClienteService(ClienteRepository clienteRepo) {
        this.clienteRepo = clienteRepo;
    }


    @Transactional
    public List<ClienteDTO> findAll() {
        return ClienteMapper.toDtoList(clienteRepo.findAll());
    }

    @Transactional
    public Page<ClienteDTO> findAll(Pageable pageable) {

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

        Page<Cliente> page = clienteRepo.findAll(effective);
        return ClienteMapper.toDtoPage(page);
    }

    @Transactional
    public ClienteDTO findById(Long id) {

        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id do cliente é obrigatório");
        }

        return clienteRepo.findById(id)
                .map(ClienteMapper::toDto)
                .orElseThrow(() -> new ObjectNotFoundException("Cliente", id));
    }


    @Transactional
    public ClienteDTO create(ClienteDTO dto) {

        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do cliente são obrigatórios");
        }

        dto.setId(null);

        Cliente cliente;

        try {
            cliente = ClienteMapper.toEntity(dto);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        cliente = clienteRepo.save(cliente);

        return ClienteMapper.toDto(cliente);
    }

    @Transactional
    public ClienteDTO update(Long id, ClienteDTO dto) {

        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do cliente são obrigatórios");
        }

        clienteRepo.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Cliente", id));

        dto.setId(id);

        Cliente cliente;

        try {
            cliente = ClienteMapper.toEntity(dto);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        cliente = clienteRepo.save(cliente);

        return ClienteMapper.toDto(cliente);
    }


    @Transactional
    public void delete(Long id) {

        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        Cliente cliente = clienteRepo.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Cliente", id));

        clienteRepo.delete(cliente);
    }

}
