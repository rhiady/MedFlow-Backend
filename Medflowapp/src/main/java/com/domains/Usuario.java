package com.domains;

import com.domains.enums.Funcao;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuario")
@SequenceGenerator(
        name = "seq_usuario",
        sequenceName = "seq_usuario",
        allocationSize = 1
)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuario")
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 120)
    private String nome;

    @NotBlank
    @Column(nullable = false, length = 80, unique = true)
    private String login;

    @NotBlank
    @Column(nullable = false, length = 120)
    private String senha;

    @Convert(converter = com.infra.FuncaoConverter.class)
    @Column(name = "funcao", nullable = false)
    private Funcao funcao;

    @JsonManagedReference
    @OneToMany(
            mappedBy = "usuario",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = false,
            fetch = FetchType.LAZY
    )
    private List<Venda> vendas = new ArrayList<>();

    public Usuario() {
    }

    public Usuario(Long id, String nome, String login, String senha, Funcao funcao, List<Venda> vendas) {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.funcao = funcao;
        this.vendas = vendas;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Funcao getFuncao() {
        return funcao;
    }

    public void setFuncao(Funcao funcao) {
        this.funcao = funcao;
    }

    public List<Venda> getVendas() {
        return vendas;
    }

    public void setVendas(List<Venda> vendas) {
        this.vendas = vendas;
    }
    public void addVenda(Venda v) {
        if (v == null) return;
        vendas.add(v);
        v.setUsuario(this);
    }

    public void removeVenda(Venda v) {
        if (v == null) return;
        vendas.remove(v);
        if (v.getUsuario() == this) v.setUsuario(null);
    }
}
