package br.com.esportes.gestao_competicoes_api.modulo_competicao;


import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/competicoes")
public class CompeticaoController {

    private CompeticaoService competicaoService;

    public CompeticaoController(CompeticaoService competicaoService) {
        this.competicaoService = competicaoService;
    }

    @PostMapping("/salvar-competicao")
    @Operation(summary = "Criar nova competição", description = "Cadastra um novo campeonato com suas datas e regulamento.")
    public ResponseEntity<CompeticaoModel> salvarCompeticao(@Valid @RequestBody CompeticaoModel competicaoModel){
        CompeticaoModel competicaoSalva = competicaoService.salvarCompeticao(competicaoModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(competicaoSalva);

    }

    @GetMapping("/{idCompeticao}")
    public ResponseEntity<CompeticaoModel> buscarCampeonatoPorId(@PathVariable Long idCompeticao) {
        CompeticaoModel competicaoModel = competicaoService.buscarCompeticaoPorId(idCompeticao);
        return ResponseEntity.ok(competicaoModel);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar competição", description = "Atualiza os dados de uma competição existente.")
    public ResponseEntity<CompeticaoModel> atualizar(
            @PathVariable Long id,
            @RequestBody CompeticaoModel competicaoModel) {

        CompeticaoModel competicaoAtualizada = competicaoService.atualizarCompeticao(id, competicaoModel);
        return ResponseEntity.ok(competicaoAtualizada);
    }

}
