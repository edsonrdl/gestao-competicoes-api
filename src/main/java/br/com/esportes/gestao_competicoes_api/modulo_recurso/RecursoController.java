package br.com.esportes.gestao_competicoes_api.modulo_recurso;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recursos")
public class RecursoController {


    private RecursoService recursoService;

    public RecursoController(RecursoService recursoService) {
        this.recursoService = recursoService;
    }

    @PostMapping
    @Operation(summary = "Abrir Recurso", description = "Time envia uma solicitação de recurso.")
    public ResponseEntity<RecursoModel> abrirRecurso(@RequestBody RecursoModel payload) {

        Long idEquipe = payload.getEquipeSolicitante().getId();
        Long idCamp = payload.getCampeonato().getId();

        RecursoModel novoRecurso = recursoService.abrirRecurso(idEquipe, idCamp, payload.getDescricao());
        return ResponseEntity.status(HttpStatus.CREATED).body(novoRecurso);
    }


    @PutMapping("/{id}/analise")
    @Operation(summary = "Julgar Recurso", description = "Comissão insere o parecer e define se aceita ou nega.")
    public ResponseEntity<RecursoModel> analisar(
            @PathVariable Long id,
            @RequestBody Map<String, Object> dadosAnalise) {

        String parecer = (String) dadosAnalise.get("parecer");
        Boolean deferido = (Boolean) dadosAnalise.get("deferido");

        RecursoModel recursoAtualizado = recursoService.analisarRecurso(id, parecer, deferido);
        return ResponseEntity.ok(recursoAtualizado);
    }

    @GetMapping("/campeonato/{idCampeonato}")
    public ResponseEntity<List<RecursoModel>> listarPorCampeonato(@PathVariable Long idCampeonato) {
        return ResponseEntity.ok(recursoService.listarPorCampeonato(idCampeonato));
    }
}
