package br.com.esportes.gestao_competicoes_api.modulo_campeonato.campeonato.regulamento;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/regulamento-campeonato")
public class RegulamentoCampeonatoController {

    private final RegulamentoCampeonatoService service;

    public RegulamentoCampeonatoController(RegulamentoCampeonatoService service) {
        this.service = service;
    }

    @PostMapping("/{idCampeonato}/adicionar-regra")
    @Operation(summary = "Adicionar Regra ao Campeonato", description = "Adiciona um artigo ou cláusula ao regulamento de um campeonato existente.")
    public ResponseEntity<RegulamentoCampeonatoModel> adicionarRegra(
            @PathVariable Long idCampeonato,
            @RequestBody RegulamentoCampeonatoModel regra) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.adicionarRegra(idCampeonato, regra));
    }

    @GetMapping
    public ResponseEntity<List<RegulamentoCampeonatoModel>> listarTodas() {
        return ResponseEntity.ok(service.listarRegras());
    }

    @PutMapping("/{idRegra}")
    @Operation(summary = "Atualizar Regra", description = "Altera o texto da regra. Não permite trocar de campeonato.")
    public ResponseEntity<RegulamentoCampeonatoModel> atualizarRegra(
            @PathVariable Long idRegra,
            @RequestBody RegulamentoCampeonatoModel regra) {

        return ResponseEntity.ok(service.atualizarRegulamentoCampeonato(idRegra, regra));
    }

    @DeleteMapping("/{idRegra}")
    public ResponseEntity<Void> deletarRegra(@PathVariable Long idRegra) {
        service.deletarRegra(idRegra);
        return ResponseEntity.noContent().build();
    }
}