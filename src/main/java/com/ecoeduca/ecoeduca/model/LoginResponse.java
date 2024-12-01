package com.ecoeduca.ecoeduca.model;

public class LoginResponse {
    private Long idAluno;

    public LoginResponse() {}

    public LoginResponse(Long idAluno) {
        this.idAluno = idAluno;
    }

    // Getters e Setters
    public Long getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Long idaluno) {
        this.idAluno = idaluno;
    }
}