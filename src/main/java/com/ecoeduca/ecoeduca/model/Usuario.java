package com.ecoeduca.ecoeduca.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="alunos")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private Integer idade;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = true)
    private int pontuacao = 0;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "responsavel_id", nullable = false)
    private Responsaveis responsavel;

    @ManyToOne(fetch = FetchType.EAGER)     
    @JoinColumn(name = "responsaveis_id", nullable = false)
    private Responsaveis responsaveis; //existem dois responsaveis id's no banco de dados (alterar isso depois!!)

    public Usuario() {}

    public Usuario(String nome, String email, Integer idade, Responsaveis responsavel, Responsaveis responsaveis, String senha) {
        this.nome = nome;
        this.email = email;
        this.idade = idade;
        this.responsavel = responsavel;
        this.responsaveis = responsaveis;
        this.senha = senha;
    }

    // Getters e Setters
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public Responsaveis getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Responsaveis id) {
        this.responsavel = id;
    }

    
    public Responsaveis getResponsaveis() {
        return responsaveis;
    }

    public void setResponsaveis(Responsaveis id) {
        this.responsaveis = id;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Integer getPontuacao() {
        return pontuacao;
    }
    
    public void setPontuacao(Integer pontuacao) {
        this.pontuacao = pontuacao;
    }

    public String getResponsavelNome() {
        if (this.responsavel != null) {
            return this.responsavel.getNome();
        }
        return null;
    }
} 

