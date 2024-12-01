package com.ecoeduca.ecoeduca.Services;

import com.ecoeduca.ecoeduca.model.Usuario;

public class LoginService {

    public void login(String nome, String senha) {
        // Cria uma instância de ServiceUser
        ServiceUser serviceUser = new ServiceUser();

        // Chama o método authenticate na instância criada
        Usuario usuario = serviceUser.authenticate(nome, senha);

        if (usuario != null) {
            // Login bem-sucedido, mostra os dados do usuário
            System.out.println("Usuário autenticado com sucesso!");
            System.out.println("Nome: " + usuario.getNome());
            System.out.println("Idade: " + usuario.getIdade());
            // Mostre as demais informações
        } else {
            // Login falhou
            System.out.println("Falha na autenticação. Nome ou senha incorretos.");
        }
    }
}

