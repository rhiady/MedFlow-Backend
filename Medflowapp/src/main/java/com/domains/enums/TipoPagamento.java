package com.domains.enums;

public enum TipoPagamento {

    DINHEIRO(0, "DINHEIRO"),
    CARTAO(1, "CARTAO"),
    PIX(2, "PIX");

    private Integer id;
    private String descricao;

    TipoPagamento(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public static TipoPagamento toEnum(Integer id) {
        if (id == null) return null;
        for (TipoPagamento tipoPagamento : TipoPagamento.values()) {
            if (id.equals(tipoPagamento.getId())) {
                return tipoPagamento;
            }
        }
        throw new IllegalArgumentException("Tipo de pagamento inválido!");
    }
}
