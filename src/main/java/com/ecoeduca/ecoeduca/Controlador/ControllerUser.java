package com.ecoeduca.ecoeduca.Controlador;

import com.ecoeduca.ecoeduca.model.Responsaveis;
import com.ecoeduca.ecoeduca.model.Usuario;
import com.ecoeduca.ecoeduca.JPArepository.RepositoryResponsaveis;
import com.ecoeduca.ecoeduca.JPArepository.RepositoryUsuario;
import com.ecoeduca.ecoeduca.Services.ServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/alunos")
@CrossOrigin(origins = "http://localhost:4200") //endpoint frontend
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

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Usuario loginRequest) {
        Usuario usuario = usuarioRepository.findByEmail(loginRequest.getEmail());
        System.out.println("Recebendo login de: " + loginRequest.getEmail());
    
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não encontrado");
        }
    
        // Verificar se a senha está correta
        if (!usuario.getSenha().equals(loginRequest.getSenha())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha incorreta");
        }
    
        // Login bem-sucedido
        return ResponseEntity.ok("Login bem-sucedido, bem-vindo " + usuario.getNome());
    } 
    
    //A parte do ranking vai estar presente aqui
    @GetMapping("/ranking")
    public List<Usuario> listarRanking() {
        // Pode usar um dos métodos definidos no repositório
        return usuarioRepository.findAllByOrderByPontuacaoDesc();
    }
    
}
