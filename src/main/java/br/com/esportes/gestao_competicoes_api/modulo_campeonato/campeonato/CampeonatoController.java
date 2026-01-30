package br.com.esportes.gestao_competicoes_api.modulo_campeonato.campeonato;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/campeonato")
public class CampeonatoController {

    private CampeonatoService competicaoService;

    public CampeonatoController(CampeonatoService competicaoService) {
        this.competicaoService = competicaoService;
    }

    @PostMapping("/criar-campeonato")
    @Operation(summary = "Criar novo campeonato", description = "Cadastra um novo campeonato com suas datas e regulamento.")
    public ResponseEntity<CampeonatoModel> criarCompeticao(@Valid @RequestBody CampeonatoModel competicaoModel){
        CampeonatoModel competicaoSalva = competicaoService.criarCompeticao(competicaoModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(competicaoSalva);

    }

    @GetMapping("/buscar-campeonato-por-id/{idCampeonato}")
    public ResponseEntity<CampeonatoModel> buscarCampeonatoPorId(@PathVariable Long idCampeonato) {
        CampeonatoModel competicaoModel = competicaoService.buscarCompeticaoPorId(idCampeonato);
        return ResponseEntity.ok(competicaoModel);
    }

    @PutMapping("/atualizar-campeonato/{id}")
    @Operation(summary = "Atualizar campeonato", description = "Atualiza os dados de uma campeonato existente.")
    public ResponseEntity<CampeonatoModel> atualizarCamp(
            @PathVariable Long id,
            @RequestBody CampeonatoModel competicaoModel) {

        CampeonatoModel competicaoAtualizada = competicaoService.atualizarCampeonato(id, competicaoModel);
        return ResponseEntity.ok(competicaoAtualizada);
    }

    @DeleteMapping("/deletar-campeonato/{idCampeonato}")
    public ResponseEntity<Void> deletarModalidade(@PathVariable Long idCampeonato) {
        competicaoService.deletarCampeonatoPorId(idCampeonato);
        return ResponseEntity.noContent().build();
    }
}
