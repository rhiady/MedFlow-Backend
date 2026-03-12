package com.domains.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VendaDTO {

    public interface Create {}
    public interface Update {}

    @Null(groups = Create.class, message = "Id deve ser omitido na criação")
    @NotNull(groups = Update.class, message = "Id é obrigatório na atualização")
    private Long id;

    @NotNull(message = "Data da venda é obrigatória")
    private LocalDateTime dataVenda;

    @Digits(integer = 10, fraction = 2, message = "Valor total deve ter no máximo 10 inteiros e 2 decimais")
    @PositiveOrZero(message = "Valor total não pode ser negativo")
    private BigDecimal valorTotal;

    /**
     * TipoPagamento representado por inteiro
     */
    @NotNull(message = "Tipo de pagamento é obrigatório")
    private Integer tipoPagamento;

    @NotNull(message = "Cliente é obrigatório")
    private Long clienteId;

    @NotNull(message = "Usuário é obrigatório")
    private Long usuarioId;

    @Valid
    private List<ItemVendaDTO> itens = new ArrayList<>();

    public VendaDTO() {
    }

    public VendaDTO(
            Long id,
            LocalDateTime dataVenda,
            BigDecimal valorTotal,
            Integer tipoPagamento,
            Long clienteId,
            Long usuarioId,
            List<ItemVendaDTO> itens
    ) {
        this.id = id;
        this.dataVenda = dataVenda;
        this.valorTotal = valorTotal;
        this.tipoPagamento = tipoPagamento;
        this.clienteId = clienteId;
        this.usuarioId = usuarioId;
        this.itens = itens;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDateTime dataVenda) {
        this.dataVenda = dataVenda;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Integer getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(Integer tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public List<ItemVendaDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemVendaDTO> itens) {
        this.itens = itens;
    }
}
