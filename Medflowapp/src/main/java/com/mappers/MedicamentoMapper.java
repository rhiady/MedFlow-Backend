package com.mappers;

import com.domains.Fornecedor;
import com.domains.Medicamento;
import com.domains.dtos.MedicamentoDTO;
import com.domains.enums.CategoriaMedicamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MedicamentoMapper {

    private MedicamentoMapper() {}

    public static MedicamentoDTO toDto(Medicamento e) {
        if (e == null) return null;

        Integer categoria = (e.getCategoriaMedicamento() == null)
                ? null
                : e.getCategoriaMedicamento().getId();

        Long fornecedorId = (e.getFornecedor() == null)
                ? null
                : e.getFornecedor().getId();

        return new MedicamentoDTO(
                e.getId(),
                e.getNome(),
                e.getDescricao(),
                e.getLaboratorio(),
                e.getApresentacao(),
                e.getPreco(),
                e.getQuantidadeEstoque(),
                categoria,
                fornecedorId
        );
    }

    public static List<MedicamentoDTO> toDtoList(Collection<Medicamento> entities) {
        if (entities == null) return List.of();
        return entities.stream()
                .filter(Objects::nonNull)
                .map(MedicamentoMapper::toDto)
                .collect(Collectors.toList());
    }

    public static Page<MedicamentoDTO> toDtoPage(Page<Medicamento> page) {
        List<MedicamentoDTO> content = toDtoList(page.getContent());
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }

    public static Medicamento toEntity(MedicamentoDTO dto, Fornecedor fornecedor) {
        if (dto == null) return null;

        Medicamento e = new Medicamento();
        e.setId(dto.getId());
        e.setNome(trim(dto.getNome()));
        e.setDescricao(trim(dto.getDescricao()));
        e.setLaboratorio(trim(dto.getLaboratorio()));
        e.setApresentacao(trim(dto.getApresentacao()));
        e.setPreco(dto.getPreco());
        e.setQuantidadeEstoque(dto.getQuantidadeEstoque());
        e.setCategoriaMedicamento(
                dto.getCategoriaMedicamento() == null
                        ? null
                        : CategoriaMedicamento.toEnum(dto.getCategoriaMedicamento())
        );
        e.setFornecedor(fornecedor);

        return e;
    }

    public static Medicamento toEntity(MedicamentoDTO dto, Function<Long, Fornecedor> fornecedorResolver) {
        if (dto == null) return null;
        Fornecedor fornecedor = (dto.getFornecedorId() == null) ? null : fornecedorResolver.apply(dto.getFornecedorId());
        return toEntity(dto, fornecedor);
    }

    public static void copyToEntity(MedicamentoDTO dto, Medicamento target, Fornecedor fornecedor) {
        if (dto == null || target == null) return;

        target.setNome(trim(dto.getNome()));
        target.setDescricao(trim(dto.getDescricao()));
        target.setLaboratorio(trim(dto.getLaboratorio()));
        target.setApresentacao(trim(dto.getApresentacao()));
        target.setPreco(dto.getPreco());
        target.setQuantidadeEstoque(dto.getQuantidadeEstoque());
        target.setCategoriaMedicamento(
                dto.getCategoriaMedicamento() == null
                        ? null
                        : CategoriaMedicamento.toEnum(dto.getCategoriaMedicamento())
        );
        target.setFornecedor(fornecedor);
    }

    public static void copyToEntity(MedicamentoDTO dto, Medicamento target, Function<Long, Fornecedor> fornecedorResolver) {
        if (dto == null || target == null) return;
        Fornecedor fornecedor = (dto.getFornecedorId() == null) ? null : fornecedorResolver.apply(dto.getFornecedorId());
        copyToEntity(dto, target, fornecedor);
    }


    private static String trim(String s) {
        return (s == null) ? null : s.trim();
    }
}
