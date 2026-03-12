package com.domains;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fornecedor")
@SequenceGenerator(
        name = "seq_fornecedor",
        sequenceName = "seq_fornecedor",
        allocationSize = 1
)
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_fornecedor")
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 120)
    private String nome;

    @NotBlank
    @Column(nullable = false, length = 18, unique = true)
    private String cnpj;

    @Column(length = 20)
    private String telefone;

    @Email
    @Column(length = 120)
    private String email;

    @JsonManagedReference
    @OneToMany(
            mappedBy = "fornecedor",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = false,
            fetch = FetchType.LAZY
    )
    @OrderBy("nome ASC")
    private List<Medicamento> medicamentos = new ArrayList<>();

    public Fornecedor() {
    }

    public Fornecedor(Long id, String nome, String cnpj, String telefone, String email, List<Medicamento> medicamentos) {
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
        this.telefone = telefone;
        this.email = email;
        this.medicamentos = medicamentos;
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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
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

    public List<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(List<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
    }
    public void addMedicamento(Medicamento m) {
        if (m == null) return;
        medicamentos.add(m);
        m.setFornecedor(this);
    }

    public void removeMedicamento(Medicamento m) {
        if (m == null) return;
        medicamentos.remove(m);
        if (m.getFornecedor() == this) m.setFornecedor(null);
    }
}
