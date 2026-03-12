package com.domains.enums;

public enum Funcao {
    ADMIN(0, "ADMIN"),
    FARMACEUTICO(1, "FARMACEUTICO"),
    ATENDENTE(2, "ATENDENTE"),
    CAIXA(3, "CAIXA");

    private Integer id;
    private String descricao;

    Funcao(Integer id, String descricao) {
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

    public static Funcao toEnum(Integer id) {
        if (id == null) return null;
        for (Funcao funcao : Funcao.values()) {
            if (id.equals(funcao.getId())) {
                return funcao;
            }
        }
        throw new IllegalArgumentException("Função inválida!");
    }
}
