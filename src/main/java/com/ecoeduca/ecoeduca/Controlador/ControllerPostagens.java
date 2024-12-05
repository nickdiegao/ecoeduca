package com.ecoeduca.ecoeduca.Controlador;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.ecoeduca.ecoeduca.JPArepository.RepositoryPostagens;
import com.ecoeduca.ecoeduca.model.CheckListItem;
import com.ecoeduca.ecoeduca.model.Postagens;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;


@RestController
@RequestMapping("/postagens")
@CrossOrigin(origins = "https://ecoeduca-ten.vercel.app") // Ajuste conforme o domínio do frontend
public class ControllerPostagens {

    @Autowired
    private RepositoryPostagens postagemRepository;

    @PostMapping
    public ResponseEntity<Postagens> criarPostagem(@RequestBody Postagens postagem) {
        Postagens novaPostagem = postagemRepository.save(postagem);
        return ResponseEntity.ok(novaPostagem);
    }

    @GetMapping
    public ResponseEntity<List<Postagens>> listarPostagens() {
        List<Postagens> postagens = postagemRepository.findAllByOrderByDataCriacaoDesc();
        return ResponseEntity.ok(postagens);
    }

    @PostMapping("/upload")
    public ResponseEntity<Object> uploadImagem(@RequestParam("imagem") MultipartFile imagem,
                                                @RequestParam("nome") String nome,
                                                @RequestParam("descricao") String descricao,
                                                @RequestParam("pontos") int pontos,
                                                @RequestParam("checkList") String checkListJson) {
        try {
            if (imagem.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Imagem não recebida."));
            }
            
            String nomeArquivo = imagem.getOriginalFilename().replaceAll(" ", "_").replaceAll("\\+", "-");

            // Defina o caminho da imagem
            String filePath = "C:/Users/Ionar/OneDrive/Área de Trabalho/backend ecoduca/ecoeduca/src/main/resources/static/imagens/" + nomeArquivo;

            // Crie o diretório se ele não existir
            File diretório = new File("C:/Users/Ionar/OneDrive/Área de Trabalho/backend ecoduca/ecoeduca/src/main/resources/static/imagens");
            if (!diretório.exists()) {
                diretório.mkdirs();  // Cria o diretório
            }

            // Salve a imagem no diretório
            imagem.transferTo(new File(filePath));

            // Salve os dados da postagem no banco
            Postagens novaPostagem = new Postagens();
            novaPostagem.setNome(nome);
            novaPostagem.setDescricao(descricao);   
            novaPostagem.setImagemUrl("/imagens/" + nomeArquivo);  // Caminho correto no banco de dados
            novaPostagem.setDataCriacao(new Date()); // Setando a data de criação automaticamente
            novaPostagem.setPontos(pontos); // Define os pontos
            novaPostagem.setImagemUrl("/imagens/" + nomeArquivo);  // Define o caminho da imagem       
            
            // Converte o checkList JSON para uma lista de objetos CheckListItem
            ObjectMapper objectMapper = new ObjectMapper();
            List<CheckListItem> checkList = objectMapper.readValue(checkListJson, objectMapper.getTypeFactory().constructCollectionType(List.class, CheckListItem.class));
            novaPostagem.setCheckList(checkList); // Define o checkList na postagem  

            postagemRepository.save(novaPostagem);

            // Retorne uma resposta JSON com o status de sucesso
            return ResponseEntity.ok(new SuccessResponse("Postagem salva com sucesso!"));

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Erro ao salvar a imagem."));
        }
    }

    // Classe para resposta de sucesso
    public class SuccessResponse {
        private String message;

        public SuccessResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    // Classe para resposta de erro
    public class ErrorResponse {
        private String error;

        public ErrorResponse(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
}

@DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarPostagem(@PathVariable Long id) {
        try {
            // Verifique se a postagem existe
            Postagens postagem = postagemRepository.findById(id).orElse(null);
            if (postagem == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Postagem não encontrada."));
            }

            // Exclua o arquivo de imagem do diretório
            String filePath = "C:/Users/Ionar/OneDrive/Área de Trabalho/backend ecoduca/ecoeduca/src/main/resources/static/imagens/" + postagem.getImagemUrl().split("/")[2];
            File arquivoImagem = new File(filePath);
            if (arquivoImagem.exists()) {
                arquivoImagem.delete(); // Exclui a imagem
            }

            // Exclua o registro da postagem do banco de dados
            postagemRepository.delete(postagem);

            // Retorne resposta de sucesso
            return ResponseEntity.ok(new SuccessResponse("Postagem excluída com sucesso!"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Erro ao excluir a postagem."));
        }
    }
 
    @GetMapping("/imagens/{fileName}")
    public ResponseEntity<Resource> getImagem(@PathVariable String fileName) throws IOException {
        Path filePath = Paths.get("C:/Users/Ionar/OneDrive/Área de Trabalho/backend ecoduca/ecoeduca/src/main/resources/static/imagens").resolve(fileName);
        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists() && resource.isReadable()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/postagens/upload")
    public ResponseEntity<?> criarPostagem(@ModelAttribute Postagens postagem, @RequestParam("imagem") MultipartFile imagem) throws IOException {
        String nomeArquivo = URLEncoder.encode(imagem.getOriginalFilename(), StandardCharsets.UTF_8);
        Path caminhoImagem = Paths.get("C:/Users/Ionar/OneDrive/Área de Trabalho/backend ecoduca/ecoeduca/src/main/resources/static/imagens").resolve(nomeArquivo);
        Files.copy(imagem.getInputStream(), caminhoImagem, StandardCopyOption.REPLACE_EXISTING);

        // Atualiza o campo imagemUrl para refletir o caminho acessível
        postagem.setImagemUrl("/imagens/" + nomeArquivo);

        postagemRepository.save(postagem);
        return ResponseEntity.ok(postagem);
    }

}



