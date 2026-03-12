package com.mappers;

import com.domains.Fornecedor;
import com.domains.dtos.FornecedorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FornecedorMapper {

    private FornecedorMapper() {}

    public static FornecedorDTO toDto(Fornecedor e) {
        if (e == null) return null;

        return new FornecedorDTO(
                e.getId(),
                e.getNome(),
                e.getCnpj(),
                e.getTelefone(),
                e.getEmail()
        );
    }

    public static List<FornecedorDTO> toDtoList(Collection<Fornecedor> entities) {
        if (entities == null) return List.of();
        return entities.stream()
                .filter(Objects::nonNull)
                .map(FornecedorMapper::toDto)
                .collect(Collectors.toList());
    }

    public static Page<FornecedorDTO> toDtoPage(Page<Fornecedor> page) {
        List<FornecedorDTO> content = toDtoList(page.getContent());
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }

    public static Fornecedor toEntity(FornecedorDTO dto) {
        if (dto == null) return null;

        Fornecedor e = new Fornecedor();
        e.setId(dto.getId());
        e.setNome(trim(dto.getNome()));
        e.setCnpj(trim(dto.getCnpj()));
        e.setTelefone(trim(dto.getTelefone()));
        e.setEmail(trim(dto.getEmail()));

        return e;
    }

    public static void copyToEntity(FornecedorDTO dto, Fornecedor target) {
        if (dto == null || target == null) return;

        target.setNome(trim(dto.getNome()));
        target.setCnpj(trim(dto.getCnpj()));
        target.setTelefone(trim(dto.getTelefone()));
        target.setEmail(trim(dto.getEmail()));
    }


    private static String trim(String s) {
        return (s == null) ? null : s.trim();
    }
}
