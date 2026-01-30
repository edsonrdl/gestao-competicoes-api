package br.com.esportes.gestao_competicoes_api.modulo_inscricao.equipe;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/equipes")
public class EquipeController {

    private final EquipeService equipeService;

    public EquipeController(EquipeService equipeService) {
        this.equipeService = equipeService;
    }

    @PostMapping("/criar-equipe")
    @Operation(summary = "1. Criar Equipe", description = "Cria a equipe e retorna o ID gerado. Use este ID para enviar a documentação depois.")
    public ResponseEntity<EquipeModel> criarEquipe(@RequestBody EquipeModel equipe) {
        return ResponseEntity.status(HttpStatus.CREATED).body(equipeService.salvarEquipe(equipe));
    }

    @GetMapping
    public ResponseEntity<List<EquipeModel>> listarTodas() {
        return ResponseEntity.ok(equipeService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipeModel> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(equipeService.buscarEquipe(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipeModel> atualizarDados(@PathVariable Long id, @RequestBody EquipeModel equipe) {
        return ResponseEntity.ok(equipeService.atualizarDadosEquipe(id, equipe));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        equipeService.deletarEquipe(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{id}/documentacao", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "2. Upload do Documento/Logo", description = "Envia a foto para uma equipe já criada.")
    public ResponseEntity<String> uploadDocumentacao(
            @PathVariable Long id,
            @RequestParam("arquivo") MultipartFile arquivo) {

        try {
            equipeService.atualizarDocumentacao(id, arquivo);
            return ResponseEntity.ok("Documentação enviada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao enviar foto: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/visualizar-documentacao")
    public ResponseEntity<byte[]> visualizarDocumento(@PathVariable Long id) {
        EquipeModel equipe = equipeService.buscarEquipe(id);

        if (equipe.getDocumentacao() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(equipe.getDocumentacaoTipo()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"doc_equipe_" + id + "\"")
                .body(equipe.getDocumentacao());
    }
}