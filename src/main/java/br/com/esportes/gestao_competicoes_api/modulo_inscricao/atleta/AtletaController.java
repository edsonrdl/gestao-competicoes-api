package br.com.esportes.gestao_competicoes_api.modulo_inscricao.atleta;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/atletas")
public class AtletaController {

    private final AtletaService atletaService;

    public AtletaController(AtletaService atletaService) {
        this.atletaService = atletaService;
    }

    @PostMapping("/{idEquipe}/criar-atleta")
    @Operation(summary = "1. Criar Atleta", description = "Cria o atleta vinculado à equipe e retorna o ID. Use este ID para enviar a foto depois.")
    public ResponseEntity<AtletaModel> criarAtleta(
            @PathVariable Long idEquipe,
            @RequestBody AtletaModel atleta) {

        AtletaModel novoAtleta = atletaService.criarAtletaVinculado(idEquipe, atleta);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAtleta);
    }

    @PostMapping(value = "/{id}/enviar-foto-atleta", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "2. Upload da Foto", description = "Envia a foto para um atleta já criado.")
    public ResponseEntity<String> uploadFoto(
            @PathVariable Long id,
            @RequestParam("arquivo") MultipartFile arquivo) {
        try {
            atletaService.salvarFoto(id, arquivo);
            return ResponseEntity.ok("Foto salva com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AtletaModel> atualizar(@PathVariable Long id, @RequestBody AtletaModel novosDados) {

        AtletaModel atleta = atletaService.buscar(id);

        atleta.setNome(novosDados.getNome());
        atleta.setCpfDocumento(novosDados.getCpfDocumento());
        atleta.setRg(novosDados.getRg());
        atleta.setOrgaoEmissor(novosDados.getOrgaoEmissor());

        if (novosDados.getEquipe() != null) {
            atleta.setEquipe(novosDados.getEquipe());
        }

        return ResponseEntity.ok(atletaService.salvar(atleta));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtletaModel> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(atletaService.buscar(id));
    }

    @GetMapping
    public ResponseEntity<List<AtletaModel>> listar() {
        return ResponseEntity.ok(atletaService.listar());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        atletaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/visualizar-foto-atleta")
    public ResponseEntity<byte[]> verFotoAtleta(@PathVariable Long id) {
        AtletaModel atleta = atletaService.buscar(id);
        if (atleta.getFoto() == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(atleta.getFotoTipo()))
                .body(atleta.getFoto());
    }
}