package com.ecoeduca.ecoeduca.Services;

import com.ecoeduca.ecoeduca.model.Usuario;
import com.ecoeduca.ecoeduca.JPArepository.RepositoryUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceUser {

    @Autowired
    private RepositoryUsuario usuarioRepository;

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario salvarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Usuario authenticate(String email, String senha) {
        // Verifica se o usuário com o e-mail existe
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario != null) {
            System.out.println("Usuário encontrado: " + usuario.getNome());
            // System.out.println("Senha fornecida: " + senha);
            // System.out.println("Senha do banco: " + usuario.getSenha());
    
            if (usuario.getSenha().equals(senha)) {
                return usuario;  // Senha correta
            } else {
                System.out.println("Senha incorreta");
                return null;  // Senha incorreta
            }
        }
        System.out.println("Usuário não encontrado");
        return null;  // Usuário não encontrado
    }
}
