package com.domains.enums;

public enum CategoriaMedicamento {
    ANALGESICO(0, "ANALGESICO"),
    ANTIBIOTICO(1, "ANTIBIOTICO"),
    ANTIINFLAMATORIO(2, "ANTIINFLAMATORIO"),
    ANTIALERGICO(3, "ANTIALERGICO"),
    VITAMINA(4, "VITAMINA"),
    CONTROLADO(5, "CONTROLADO"),
    OUTROS(6, "OUTROS");

    private Integer id;
    private String descricao;

    CategoriaMedicamento(Integer id, String descricao) {
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

    public static CategoriaMedicamento toEnum(Integer id) {
        if (id == null) return null;
        for (CategoriaMedicamento categoria : CategoriaMedicamento.values()) {
            if (id.equals(categoria.getId())) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Categoria de medicamento inválida!");
    }
}
