package com.ecoeduca.ecoeduca.Controlador;

import com.ecoeduca.ecoeduca.model.Responsaveis;
import com.ecoeduca.ecoeduca.model.Usuario;
import com.ecoeduca.ecoeduca.JPArepository.RepositoryResponsaveis;
import com.ecoeduca.ecoeduca.JPArepository.RepositoryUsuario;
import com.ecoeduca.ecoeduca.Services.ServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alunos")
public class ControllerUser {

    @Autowired
    private ServiceUser usuarioService;

    @Autowired
    private RepositoryUsuario usuarioRepository; // Injetando o repositório de usuários

    @Autowired
    private RepositoryResponsaveis responsaveisRepository; // Injetando o repositório de responsáveis

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Long id) {
        return usuarioService.buscarUsuarioPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Usuario criarUsuario(@RequestBody Usuario usuario) {
        // Aqui buscamos o responsável baseado no ID fornecido
        if (usuario.getResponsavel() != null) {
            Responsaveis responsavel = responsaveisRepository.findById(usuario.getResponsavel().getId())
                .orElseThrow(() -> new RuntimeException("Responsável não encontrado"));
            usuario.setResponsavel(responsavel);
        }
        return usuarioRepository.save(usuario); // Chamar o método save na instância do repositório
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
