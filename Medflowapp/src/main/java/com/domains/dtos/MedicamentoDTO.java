package com.domains.dtos;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class MedicamentoDTO {

    public interface Create {}
    public interface Update {}

    @Null(groups = Create.class, message = "Id deve ser omitido na criação")
    @NotNull(groups = Update.class, message = "Id é obrigatório na atualização")
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 120, message = "Nome deve ter no máximo 120 caracteres")
    private String nome;

    @Size(max = 255, message = "Descrição deve ter no máximo 255 caracteres")
    private String descricao;

    @NotBlank(message = "Laboratório é obrigatório")
    @Size(max = 120, message = "Laboratório deve ter no máximo 120 caracteres")
    private String laboratorio;

    @NotBlank(message = "Apresentação é obrigatória")
    @Size(max = 80, message = "Apresentação deve ter no máximo 80 caracteres")
    private String apresentacao;

    @NotNull(message = "Preço é obrigatório")
    @Digits(integer = 8, fraction = 2, message = "Preço deve ter no máximo 8 inteiros e 2 decimais")
    @Positive(message = "Preço deve ser maior que zero")
    private BigDecimal preco;

    @NotNull(message = "Quantidade em estoque é obrigatória")
    @Digits(integer = 12, fraction = 3, message = "Quantidade em estoque deve ter no máximo 12 inteiros e 3 decimais")
    @PositiveOrZero(message = "Quantidade em estoque não pode ser negativa")
    private BigDecimal quantidadeEstoque;

    @NotNull(message = "Categoria do medicamento é obrigatória")
    private Integer categoriaMedicamento;

    @NotNull(message = "Fornecedor é obrigatório")
    private Long fornecedorId;

    public MedicamentoDTO() {
    }

    public MedicamentoDTO(
            Long id,
            String nome,
            String descricao,
            String laboratorio,
            String apresentacao,
            BigDecimal preco,
            BigDecimal quantidadeEstoque,
            Integer categoriaMedicamento,
            Long fornecedorId
    ) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.laboratorio = laboratorio;
        this.apresentacao = apresentacao;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
        this.categoriaMedicamento = categoriaMedicamento;
        this.fornecedorId = fornecedorId;
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
        this.nome = (nome == null ? null : nome.trim());
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = (descricao == null ? null : descricao.trim());
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = (laboratorio == null ? null : laboratorio.trim());
    }

    public String getApresentacao() {
        return apresentacao;
    }

    public void setApresentacao(String apresentacao) {
        this.apresentacao = (apresentacao == null ? null : apresentacao.trim());
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

    public Integer getCategoriaMedicamento() {
        return categoriaMedicamento;
    }

    public void setCategoriaMedicamento(Integer categoriaMedicamento) {
        this.categoriaMedicamento = categoriaMedicamento;
    }

    public Long getFornecedorId() {
        return fornecedorId;
    }

    public void setFornecedorId(Long fornecedorId) {
        this.fornecedorId = fornecedorId;
    }

}
