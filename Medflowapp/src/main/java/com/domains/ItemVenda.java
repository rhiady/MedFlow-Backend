package com.domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "itemvenda")
@SequenceGenerator(
        name = "seq_itemvenda",
        sequenceName = "seq_itemvenda",
        allocationSize = 1
)
public class ItemVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_itemvenda")
    private Long id;


    @Column(nullable = false)
    private BigDecimal quantidade;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precoUnitario;

    @NotNull
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicamento_id", nullable = false)
    private Medicamento medicamento;

    @NotNull
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venda_id", nullable = false)
    private Venda venda;

    public ItemVenda() {
        this.quantidade = BigDecimal.ZERO;
        this.precoUnitario = BigDecimal.ZERO;
        this.subtotal = BigDecimal.ZERO;
    }


    public ItemVenda(Long id, Venda venda, Medicamento medicamento, BigDecimal quantidade, BigDecimal precoUnitario) {

        this.id = id;
        this.venda = venda;
        this.medicamento = medicamento;
        this.quantidade = quantidade != null ? quantidade : BigDecimal.ZERO;
        this.precoUnitario = precoUnitario != null ? precoUnitario : BigDecimal.ZERO;

        this.subtotal = this.precoUnitario
                .multiply(this.quantidade)
                .setScale(2, RoundingMode.HALF_UP);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    @PrePersist @PreUpdate
    private void recalcSubtotal() {
        BigDecimal qtd   = (quantidade != null ? quantidade : BigDecimal.ZERO);
        BigDecimal preco = (precoUnitario != null ? precoUnitario : BigDecimal.ZERO);

        this.subtotal = qtd.multiply(preco)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
