package com.mappers;

import com.domains.Cliente;
import com.domains.ItemVenda;
import com.domains.Usuario;
import com.domains.Venda;
import com.domains.dtos.ItemVendaDTO;
import com.domains.dtos.VendaDTO;
import com.domains.enums.TipoPagamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class VendaMapper {

    private VendaMapper() {}


    public static VendaDTO toDto(Venda e) {
        if (e == null) return null;

        Integer tipoPagamento = (e.getTipoPagamento() == null) ? null : e.getTipoPagamento().getId();
        Long clienteId = (e.getCliente() == null) ? null : e.getCliente().getId();
        Long usuarioId = (e.getUsuario() == null) ? null : e.getUsuario().getId();

        List<ItemVendaDTO> itensDto = ItemVendaMapper.toDtoList(e.getItens());

        return new VendaDTO(
                e.getId(),
                e.getDataVenda(),
                e.getValorTotal(),
                tipoPagamento,
                clienteId,
                usuarioId,
                itensDto
        );
    }

    public static List<VendaDTO> toDtoList(Collection<Venda> entities) {
        if (entities == null) return List.of();
        return entities.stream()
                .filter(Objects::nonNull)
                .map(VendaMapper::toDto)
                .collect(Collectors.toList());
    }

    public static Page<VendaDTO> toDtoPage(Page<Venda> page) {
        List<VendaDTO> content = toDtoList(page.getContent());
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }


    public static Venda toEntity(VendaDTO dto, Cliente cliente, Usuario usuario, List<ItemVenda> itens) {
        if (dto == null) return null;

        Venda e = new Venda();
        e.setId(dto.getId());
        e.setDataVenda(dto.getDataVenda());
        e.setValorTotal(dto.getValorTotal());
        e.setTipoPagamento(dto.getTipoPagamento() == null ? null : TipoPagamento.toEnum(dto.getTipoPagamento()));
        e.setCliente(cliente);
        e.setUsuario(usuario);
        e.setItens(itens != null ? itens : new ArrayList<>());

        if (e.getItens() != null) {
            for (ItemVenda item : e.getItens()) {
                if (item != null) {
                    item.setVenda(e);
                }
            }
        }

        return e;
    }

    public static void copyToEntity(VendaDTO dto, Venda target, Cliente cliente, Usuario usuario, List<ItemVenda> itens) {
        if (dto == null || target == null) return;

        target.setDataVenda(dto.getDataVenda());
        target.setValorTotal(dto.getValorTotal());
        target.setTipoPagamento(dto.getTipoPagamento() == null ? null : TipoPagamento.toEnum(dto.getTipoPagamento()));
        target.setCliente(cliente);
        target.setUsuario(usuario);

        target.getItens().clear();
        if (itens != null) {
            for (ItemVenda item : itens) {
                if (item != null) {
                    item.setVenda(target);
                    target.getItens().add(item);
                }
            }
        }
    }
}
