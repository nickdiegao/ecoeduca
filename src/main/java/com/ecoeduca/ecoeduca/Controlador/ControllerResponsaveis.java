package com.ecoeduca.ecoeduca.Controlador;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecoeduca.ecoeduca.Services.ServiceResponsaveis;
import com.ecoeduca.ecoeduca.model.Responsaveis;

import java.util.List;

@RestController
@RequestMapping("/responsaveis")
public class ControllerResponsaveis {
    
    private final ServiceResponsaveis serviceResponsaveis;

    // @Autowired
    public ControllerResponsaveis(ServiceResponsaveis serviceResponsaveis) {
        this.serviceResponsaveis = serviceResponsaveis;
    }

    @PostMapping
    public ResponseEntity<Responsaveis> criarResponsavel(@RequestBody Responsaveis responsavel) {
        Responsaveis novoResponsavel = serviceResponsaveis.salvarResponsavel(responsavel);
        return ResponseEntity.ok(novoResponsavel);
    }

    @GetMapping
    public ResponseEntity<List<Responsaveis>> listarResponsaveis() {
        return ResponseEntity.ok(serviceResponsaveis.listarResponsaveis());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Responsaveis> buscarPorId(@PathVariable Long id) {
        return serviceResponsaveis.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Responsaveis> atualizarResponsavel(@PathVariable Long id, @RequestBody Responsaveis responsavel) {
        return ResponseEntity.ok(serviceResponsaveis.atualizarResponsavel(id, responsavel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarResponsavel(@PathVariable Long id) {
        serviceResponsaveis.deletarResponsavel(id);
        return ResponseEntity.noContent().build();
    }
}
