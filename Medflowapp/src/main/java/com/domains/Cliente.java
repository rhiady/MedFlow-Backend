package com.domains;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cliente")
@SequenceGenerator(
        name = "seq_cliente",
        sequenceName = "seq_cliente",
        allocationSize = 1
)
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cliente")
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 120)
    private String nome;

    @NotBlank
    @Column(nullable = false, length = 14, unique = true)
    private String cpf;

    @Column(length = 20)
    private String telefone;

    @Email
    @Column(length = 120)
    private String email;

    @JsonManagedReference
    @OneToMany(
            mappedBy = "cliente",
            fetch = FetchType.LAZY
    )
    private List<Venda> vendas = new ArrayList<>();

    public Cliente() {
    }

    public Cliente(Long id, String nome, String cpf, String telefone, String email, List<Venda> vendas) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        v.setCliente(this);
    }

    public void removeVenda(Venda v) {
        if (v == null) return;
        vendas.remove(v);
        if (v.getCliente() == this) v.setCliente(null);
    }
}
