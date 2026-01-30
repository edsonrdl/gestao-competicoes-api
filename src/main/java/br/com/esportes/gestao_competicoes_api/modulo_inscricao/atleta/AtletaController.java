package br.com.esportes.gestao_competicoes_api.modulo_inscricao.atleta;


import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/atletas")
public class AtletaController {

    private final AtletaService atletaService;

    public AtletaController(AtletaService atletaService) {
        this.atletaService = atletaService;
    }

    @PostMapping("/{idEquipe}/criar-atleta")
    @Operation(summary = "Criar Atleta na Equipe", description = "Cria um atleta já vinculado à equipe informada na URL.")
    public ResponseEntity<AtletaModel> criarAtleta(
            @PathVariable Long idEquipe,
            @RequestBody AtletaModel atleta) {

        AtletaModel novoAtleta = atletaService.criarAtletaVinculado(idEquipe, atleta);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAtleta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AtletaModel> atualizar(@PathVariable Long id, @RequestBody AtletaModel novosDados) {
        AtletaModel atleta = atletaService.buscar(id);
        atleta.setNome(novosDados.getNome());
        atleta.setAtletaDocumentoModel(novosDados.getAtletaDocumentoModel());

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

    @PostMapping(value = "/{id}/enviar-foto-atleta", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFoto(@PathVariable Long id, @RequestParam("arquivo") MultipartFile arquivo) {
        try {
            atletaService.salvarFoto(id, arquivo);
            return ResponseEntity.ok("Foto salva!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/visualizar-foto-atleta")
    public ResponseEntity<byte[]> verFotoAtleta(@PathVariable Long id) {
        AtletaModel atleta = atletaService.buscar(id);
        if (atleta.getFoto() == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(atleta.getFotoTipo()))
                .body(atleta.getFoto());
    }
    
    @PostMapping(value = "/{idEquipe}/criar-atleta-com-foto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Criar Atleta com Foto", description = "Envia os dados do atleta (JSON) e a foto em uma única requisição.")
    public ResponseEntity<AtletaModel> criarCompleto(
            @PathVariable Long idEquipe,
            @RequestPart("dados") AtletaModel atleta, // O JSON vem aqui dentro
            @RequestPart(value = "arquivo", required = false) MultipartFile arquivo // A foto vem aqui
    ) {
        try {
            AtletaModel novoAtleta = atletaService.criarAtletaVinculado(idEquipe, atleta);

            if (arquivo != null && !arquivo.isEmpty()) {
                atletaService.salvarFoto(novoAtleta.getId(), arquivo);
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(novoAtleta);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}