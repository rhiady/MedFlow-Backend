package com.domains.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

public class UsuarioDTO {

    public interface Create {}
    public interface Update {}

    @Null(groups = Create.class, message = "Id deve ser omitido na criação")
    @NotNull(groups = Update.class, message = "Id é obrigatório na atualização")
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 120, message = "Nome deve ter no máximo 120 caracteres")
    private String nome;

    @NotBlank(message = "Login é obrigatório")
    @Size(max = 80, message = "Login deve ter no máximo 80 caracteres")
    private String login;

    @NotBlank(groups = Create.class, message = "Senha é obrigatória na criação")
    @Size(min = 4, max = 120, message = "Senha deve ter entre 4 e 120 caracteres")
    private String senha;


    @NotNull(message = "Função é obrigatória")
    private Integer funcao;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Long id, String nome, String login, String senha, Integer funcao) {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.funcao = funcao;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = (login == null ? null : login.trim());
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = (senha == null ? null : senha.trim());
    }

    public Integer getFuncao() {
        return funcao;
    }

    public void setFuncao(Integer funcao) {
        this.funcao = funcao;
    }
}
