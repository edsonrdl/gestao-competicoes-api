package br.com.esportes.gestao_competicoes_api.modulo_inscricao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/equipes")
public class EquipeController {


    private EquipeService equipeService;

    public EquipeController(EquipeService equipeService) {
            this.equipeService = equipeService;
    }

    @PostMapping("/{id}/documentacao")
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

    @GetMapping("/{id}/documentacao")
    public ResponseEntity<byte[]> verDocumento(@PathVariable Long id) {
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