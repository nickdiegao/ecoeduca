package com.ecoeduca.ecoeduca.Controlador;

import com.ecoeduca.ecoeduca.model.LoginRequest;
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
import java.util.Optional;


@RestController
@RequestMapping("/alunos")
@CrossOrigin(origins = "https://ecoeduca-ten.vercel.app") //endpoint frontend
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
                .map(usuario -> {
                    // Aqui, você pode modificar o objeto antes de retorná-lo
                    return ResponseEntity.ok(usuario); // Retorna o objeto completo com o nome do responsável
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Usuario criarUsuario(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario); // Chamar o método save na instância do repositório
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    //A parte do ranking vai estar presente aqui
    @GetMapping("/ranking")
    public List<Usuario> listarRanking() {
        // Pode usar um dos métodos definidos no repositório
        return usuarioRepository.findAllByOrderByPontuacaoDesc();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        // Buscar o usuário pelo email
        Usuario usuario = usuarioRepository.findByEmail(loginRequest.getEmail()); // Busca pelo email
        
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

    @PutMapping("/{id}/nome")
    public ResponseEntity<Usuario> atualizarNome(@PathVariable Long id, @RequestBody String novoNome) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setNome(novoNome);  // Atualiza apenas o nome
            usuarioRepository.save(usuario);  // Salva as alterações
            return ResponseEntity.ok(usuario);  // Retorna o usuário atualizado
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // Endpoint para atualizar apenas a senha do usuário
    @PutMapping("/{id}/senha")
    public ResponseEntity<Usuario> atualizarSenha(@PathVariable Long id, @RequestBody String novaSenha) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setSenha(novaSenha);  // Atualiza apenas a senha
            usuarioRepository.save(usuario);  // Salva as alterações
            return ResponseEntity.ok(usuario);  // Retorna o usuário atualizado
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
