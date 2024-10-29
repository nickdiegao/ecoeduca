package com.ecoeduca.ecoeduca.Services;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecoeduca.ecoeduca.JPArepository.RepositoryResponsaveis;
import com.ecoeduca.ecoeduca.model.Responsaveis;

import java.util.List;
import java.util.Optional;


@Service
public class ServiceResponsaveis {
    
    private final RepositoryResponsaveis responsavelRepository;

    // @Autowired
    public ServiceResponsaveis(RepositoryResponsaveis responsavelRepository) {
        this.responsavelRepository = responsavelRepository;
    }

    // Método para salvar um novo responsável
    public Responsaveis salvarResponsavel(Responsaveis responsavel) {
        return responsavelRepository.save(responsavel);
    }

    // Método para buscar todos os responsáveis
    public List<Responsaveis> listarResponsaveis() {
        return responsavelRepository.findAll();
    }

    // Método para buscar um responsável pelo ID
    public Optional<Responsaveis> buscarPorId(Long id) {
        return responsavelRepository.findById(id);
    }

    // Método para atualizar um responsável
    public Responsaveis atualizarResponsavel(Long id, Responsaveis responsavelAtualizado) {
        return responsavelRepository.findById(id)
                .map(responsavel -> {
                    responsavel.setNome(responsavelAtualizado.getNome());
                    responsavel.setEmail(responsavelAtualizado.getEmail());
                    responsavel.setTelefone(responsavelAtualizado.getTelefone());
                    return responsavelRepository.save(responsavel);
                })
                .orElseThrow(() -> new RuntimeException("Responsável não encontrado"));
    }

    // Método para deletar um responsável
    public void deletarResponsavel(Long id) {
        responsavelRepository.deleteById(id);
    }
}
