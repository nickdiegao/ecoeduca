package com.ecoeduca.ecoeduca.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @Column(nullable = true)
    private String nomeDoResponsavel;

    @Column(nullable = true)
    private String nomeDoResponsavel2;

    public Usuario() {}

    public Usuario(String nome, String email, Integer idade, String nomeDoResponsavel, String senha, String nomeDoResponsavel2) {
        this.nome = nome;
        this.email = email;
        this.idade = idade;
        this.nomeDoResponsavel = nomeDoResponsavel;
        this.senha = senha;
        this.nomeDoResponsavel2 = nomeDoResponsavel2;
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

    public String getNomeDoResponsavel() {
        return nomeDoResponsavel;
    }

    public void setNomeDoResponsavel(String nomeDoResponsavel) {
        this.nomeDoResponsavel = nomeDoResponsavel;
    }

    public String getNomeDoResponsavel2() {
        return nomeDoResponsavel;
    }

    public void setNomeDoResponsavel2(String nomeDoResponsavel2) {
        this.nomeDoResponsavel2 = nomeDoResponsavel2;
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
} 

