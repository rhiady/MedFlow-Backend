package com.services;

import com.domains.Usuario;
import com.domains.dtos.UsuarioDTO;
import com.mappers.UsuarioMapper;
import com.repositories.UsuarioRepository;
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
public class UsuarioService {

    private static final int MAX_PAGE_SIZE = 200;

    private final UsuarioRepository usuarioRepo;

    public UsuarioService(UsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }


    @Transactional
    public List<UsuarioDTO> findAll() {
        return UsuarioMapper.toDtoList(usuarioRepo.findAll());
    }

    @Transactional
    public Page<UsuarioDTO> findAll(Pageable pageable) {

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

        Page<Usuario> page = usuarioRepo.findAll(effective);
        return UsuarioMapper.toDtoPage(page);
    }

    @Transactional
    public UsuarioDTO findById(Long id) {

        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id do usuário é obrigatório");
        }

        return usuarioRepo.findById(id)
                .map(UsuarioMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Usuário não encontrado: id=" + id));
    }


    @Transactional
    public UsuarioDTO create(UsuarioDTO dto) {

        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do usuário são obrigatórios");
        }

        dto.setId(null);

        Usuario usuario;

        try {
            usuario = UsuarioMapper.toEntity(dto);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        return UsuarioMapper.toDto(usuarioRepo.save(usuario));
    }


    @Transactional
    public UsuarioDTO update(Long id, UsuarioDTO dto) {

        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do usuário são obrigatórios");
        }

        Usuario usuario = usuarioRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Usuário não encontrado: id=" + id));

        dto.setId(id);

        try {
            usuario = UsuarioMapper.toEntity(dto);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        return UsuarioMapper.toDto(usuarioRepo.save(usuario));
    }


    @Transactional
    public void delete(Long id) {

        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        Usuario usuario = usuarioRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Usuário não encontrado: id=" + id));

        usuarioRepo.delete(usuario);
    }
}
