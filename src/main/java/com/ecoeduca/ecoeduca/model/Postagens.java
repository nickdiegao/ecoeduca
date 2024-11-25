package com.ecoeduca.ecoeduca.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "postagens")
public class Postagens {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private String imagemUrl;

    @ElementCollection
    @CollectionTable(name = "checklist", joinColumns = @JoinColumn(name = "postagem_id"))
    private List<CheckListItem> checkList;

    @Column(nullable = false)
    private Integer pontos = 0;

    @Temporal(TemporalType.TIMESTAMP)  // Define que a data será um timestamp
    private Date dataCriacao;

    // Construtores
    public Postagens() {}

    public Postagens(String nome, String descricao, String imagemUrl, List<CheckListItem> checkList, Integer pontos) {
        this.nome = nome;
        this.descricao = descricao;
        this.imagemUrl = imagemUrl;
        this.checkList = checkList;
        this.pontos = pontos;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public List<CheckListItem> getCheckList() {
        return checkList;
    }

    public void setCheckList(List<CheckListItem> checkList) {
        this.checkList = checkList;
    }

    public Integer getPontos() {
        return pontos;
    }

    public void setPontos(Integer pontos) {
        this.pontos = pontos;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
