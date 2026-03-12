package com.mappers;

import com.domains.Cliente;
import com.domains.dtos.ClienteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ClienteMapper {

    private ClienteMapper() {}

    public static ClienteDTO toDto(Cliente e) {
        if (e == null) return null;

        return new ClienteDTO(
                e.getId(),
                e.getNome(),
                e.getCpf(),
                e.getTelefone(),
                e.getEmail()
        );
    }

    public static List<ClienteDTO> toDtoList(Collection<Cliente> entities) {
        if (entities == null) return List.of();
        return entities.stream()
                .filter(Objects::nonNull)
                .map(ClienteMapper::toDto)
                .collect(Collectors.toList());
    }

    public static Page<ClienteDTO> toDtoPage(Page<Cliente> page) {
        List<ClienteDTO> content = toDtoList(page.getContent());
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }


    public static Cliente toEntity(ClienteDTO dto) {
        if (dto == null) return null;

        Cliente e = new Cliente();
        e.setId(dto.getId());
        e.setNome(trim(dto.getNome()));
        e.setCpf(trim(dto.getCpf()));
        e.setTelefone(trim(dto.getTelefone()));
        e.setEmail(trim(dto.getEmail()));

        return e;
    }

    public static void copyToEntity(ClienteDTO dto, Cliente target) {
        if (dto == null || target == null) return;

        target.setNome(trim(dto.getNome()));
        target.setCpf(trim(dto.getCpf()));
        target.setTelefone(trim(dto.getTelefone()));
        target.setEmail(trim(dto.getEmail()));
    }


    private static String trim(String s) {
        return (s == null) ? null : s.trim();
    }
}
