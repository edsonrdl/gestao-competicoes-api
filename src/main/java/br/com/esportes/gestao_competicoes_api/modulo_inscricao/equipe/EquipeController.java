package br.com.esportes.gestao_competicoes_api.modulo_inscricao.equipe;

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

    @PostMapping("/{id}/atualizar-documentacao")
    public ResponseEntity<String> enviarDocumento(
            @PathVariable Long id,
            @RequestParam("arquivo") MultipartFile arquivo) {
        try {
            equipeService.atualizarDocumentacao(id, arquivo);
            return ResponseEntity.ok("Documentação (foto) enviada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
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