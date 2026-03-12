package com.domains;

import com.domains.enums.TipoPagamento;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "venda")
@SequenceGenerator(
        name = "seq_venda",
        sequenceName = "seq_venda",
        allocationSize = 1
)
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_venda")
    private Long id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotNull
    @Column(nullable = false)
    private LocalDateTime dataVenda;

    @NotNull
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;

    @Convert(converter = com.infra.TipoPagamentoConverter.class)
    @Column(name = "tipo_pagamento", nullable = false)
    private TipoPagamento tipoPagamento;

    @NotNull
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @NotNull
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @JsonManagedReference
    @OneToMany(
            mappedBy = "venda",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<ItemVenda> itens = new ArrayList<>();

    public Venda() {
        this.valorTotal = BigDecimal.ZERO;
    }

    public Venda(Long id, LocalDateTime dataVenda, BigDecimal valorTotal, TipoPagamento tipoPagamento, Cliente cliente, Usuario usuario, List<ItemVenda> itens) {
        this.id = id;
        this.dataVenda = dataVenda;
        this.valorTotal = valorTotal != null ? valorTotal : BigDecimal.ZERO;
        this.tipoPagamento = tipoPagamento;
        this.cliente = cliente;
        this.usuario = usuario;
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

    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<ItemVenda> getItens() {
        return itens;
    }

    public void setItens(List<ItemVenda> itens) {
        this.itens = itens;
    }
    public void addItem(ItemVenda item) {
        if (item == null) return;
        itens.add(item);
        item.setVenda(this);
    }

    public void removeItem(ItemVenda item) {
        if (item == null) return;
        itens.remove(item);
        if (item.getVenda() == this) item.setVenda(null);
    }

    @PrePersist @PreUpdate
    private void recalcValorTotal() {
        BigDecimal total = BigDecimal.ZERO;

        if (itens != null) {
            for (ItemVenda item : itens) {
                BigDecimal sub = item.getSubtotal() != null ? item.getSubtotal() : BigDecimal.ZERO;
                total = total.add(sub);
            }
        }

        this.valorTotal = total.setScale(2, RoundingMode.HALF_UP);
    }
}
