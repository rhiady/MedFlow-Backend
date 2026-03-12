package com.domains;

import com.domains.enums.CategoriaMedicamento;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "medicamento")
@SequenceGenerator(
        name = "seq_medicamento",
        sequenceName = "seq_medicamento",
        allocationSize = 1
)
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_medicamento")
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 120)
    private String nome;

    @Column(length = 255)
    private String descricao;

    @NotBlank
    @Column(nullable = false, length = 120)
    private String laboratorio;

    @NotBlank
    @Column(nullable = false, length = 80)
    private String apresentacao;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @NotNull
    @Column(nullable = false)
    private BigDecimal quantidadeEstoque;

    @Convert(converter = com.infra.CategoriaMedicamentoConverter.class)
    @Column(name = "categoria_medicamento", nullable = false)
    private CategoriaMedicamento categoriaMedicamento;

    @NotNull
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fornecedor_id", nullable = false)
    private Fornecedor fornecedor;

    @JsonManagedReference
    @OneToMany(
            mappedBy = "medicamento",
            fetch = FetchType.LAZY
    )
    private List<ItemVenda> itensVenda = new ArrayList<>();

    public Medicamento() {
        this.quantidadeEstoque= BigDecimal.ZERO;
        this.preco = BigDecimal.ZERO;
    }

    public Medicamento(Long id, String nome, String descricao, String laboratorio, String apresentacao, BigDecimal preco, BigDecimal quantidadeEstoque, CategoriaMedicamento categoriaMedicamento, Fornecedor fornecedor, List<ItemVenda> itensVenda) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.laboratorio = laboratorio;
        this.apresentacao = apresentacao;
        this.preco = preco != null ? preco : BigDecimal.ZERO;;
        this.quantidadeEstoque = quantidadeEstoque != null ? quantidadeEstoque : BigDecimal.ZERO;
        this.categoriaMedicamento = categoriaMedicamento;
        this.fornecedor = fornecedor;
        this.itensVenda = itensVenda;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public String getApresentacao() {
        return apresentacao;
    }

    public void setApresentacao(String apresentacao) {
        this.apresentacao = apresentacao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public BigDecimal getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(BigDecimal quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public CategoriaMedicamento getCategoriaMedicamento() {
        return categoriaMedicamento;
    }

    public void setCategoriaMedicamento(CategoriaMedicamento categoriaMedicamento) {
        this.categoriaMedicamento = categoriaMedicamento;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public List<ItemVenda> getItensVenda() {
        return itensVenda;
    }

    public void setItensVenda(List<ItemVenda> itensVenda) {
        this.itensVenda = itensVenda;
    }

    public void addItemVenda(ItemVenda item) {
        if (item == null) return;
        itensVenda.add(item);
        item.setMedicamento(this);
    }

    public void removeItemVenda(ItemVenda item) {
        if (item == null) return;
        itensVenda.remove(item);
        if (item.getMedicamento() == this) item.setMedicamento(null);
    }


}
