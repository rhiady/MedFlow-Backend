package com.mappers;

import com.domains.ItemVenda;
import com.domains.Medicamento;
import com.domains.dtos.ItemVendaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ItemVendaMapper {

    private ItemVendaMapper() {}

    public static ItemVendaDTO toDto(ItemVenda e) {
        if (e == null) return null;

        Long medicamentoId = (e.getMedicamento() == null) ? null : e.getMedicamento().getId();

        return new ItemVendaDTO(
                e.getId(),
                medicamentoId,
                e.getQuantidade(),
                e.getPrecoUnitario(),
                e.getSubtotal()
        );
    }

    public static List<ItemVendaDTO> toDtoList(Collection<ItemVenda> entities) {
        if (entities == null) return List.of();
        return entities.stream()
                .filter(Objects::nonNull)
                .map(ItemVendaMapper::toDto)
                .collect(Collectors.toList());
    }

    public static Page<ItemVendaDTO> toDtoPage(Page<ItemVenda> page) {
        List<ItemVendaDTO> content = toDtoList(page.getContent());
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }


    public static ItemVenda toEntity(ItemVendaDTO dto, Medicamento medicamento) {
        if (dto == null) return null;

        ItemVenda e = new ItemVenda();
        e.setId(dto.getId());
        e.setMedicamento(medicamento);
        e.setQuantidade(dto.getQuantidade());
        e.setPrecoUnitario(dto.getPrecoUnitario());
        e.setSubtotal(dto.getSubtotal());

        return e;
    }

    public static ItemVenda toEntity(ItemVendaDTO dto, Function<Long, Medicamento> medicamentoResolver) {
        if (dto == null) return null;
        Medicamento medicamento = (dto.getMedicamentoId() == null) ? null : medicamentoResolver.apply(dto.getMedicamentoId());
        return toEntity(dto, medicamento);
    }

    public static void copyToEntity(ItemVendaDTO dto, ItemVenda target, Medicamento medicamento) {
        if (dto == null || target == null) return;

        target.setMedicamento(medicamento);
        target.setQuantidade(dto.getQuantidade());
        target.setPrecoUnitario(dto.getPrecoUnitario());
        target.setSubtotal(dto.getSubtotal());
    }

    public static void copyToEntity(ItemVendaDTO dto, ItemVenda target, Function<Long, Medicamento> medicamentoResolver) {
        if (dto == null || target == null) return;
        Medicamento medicamento = (dto.getMedicamentoId() == null) ? null : medicamentoResolver.apply(dto.getMedicamentoId());
        copyToEntity(dto, target, medicamento);
    }
    public static List<ItemVenda> toEntityList(
            List<ItemVendaDTO> dtos,
            java.util.function.Function<Long, Medicamento> medicamentoFinder) {

        if (dtos == null) return List.of();

        return dtos.stream()
                .filter(Objects::nonNull)
                .map(dto -> {
                    Medicamento medicamento = medicamentoFinder.apply(dto.getMedicamentoId());
                    return toEntity(dto, medicamento);
                })
                .collect(Collectors.toList());
    }
}
