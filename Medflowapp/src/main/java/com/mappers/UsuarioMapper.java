package com.mappers;

import com.domains.Usuario;
import com.domains.dtos.UsuarioDTO;
import com.domains.enums.Funcao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UsuarioMapper {

    private UsuarioMapper() {}

    public static UsuarioDTO toDto(Usuario e) {
        if (e == null) return null;

        Integer funcao = (e.getFuncao() == null) ? null : e.getFuncao().getId();

        return new UsuarioDTO(
                e.getId(),
                e.getNome(),
                e.getLogin(),
                e.getSenha(),
                funcao
        );
    }

    public static List<UsuarioDTO> toDtoList(Collection<Usuario> entities) {
        if (entities == null) return List.of();
        return entities.stream()
                .filter(Objects::nonNull)
                .map(UsuarioMapper::toDto)
                .collect(Collectors.toList());
    }

    public static Page<UsuarioDTO> toDtoPage(Page<Usuario> page) {
        List<UsuarioDTO> content = toDtoList(page.getContent());
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }


    public static Usuario toEntity(UsuarioDTO dto) {
        if (dto == null) return null;

        Usuario e = new Usuario();
        e.setId(dto.getId());
        e.setNome(trim(dto.getNome()));
        e.setLogin(trim(dto.getLogin()));
        e.setSenha(trim(dto.getSenha()));
        e.setFuncao(dto.getFuncao() == null ? null : Funcao.toEnum(dto.getFuncao()));

        return e;
    }

    public static void copyToEntity(UsuarioDTO dto, Usuario target) {
        if (dto == null || target == null) return;

        target.setNome(trim(dto.getNome()));
        target.setLogin(trim(dto.getLogin()));
        target.setSenha(trim(dto.getSenha()));
        target.setFuncao(dto.getFuncao() == null ? null : Funcao.toEnum(dto.getFuncao()));
    }


    private static String trim(String s) {
        return (s == null) ? null : s.trim();
    }
}
