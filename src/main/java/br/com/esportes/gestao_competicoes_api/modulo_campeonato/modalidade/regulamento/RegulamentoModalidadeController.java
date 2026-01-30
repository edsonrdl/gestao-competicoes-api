package br.com.esportes.gestao_competicoes_api.modulo_campeonato.modalidade.regulamento;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/regulamento-modalidade")
public class RegulamentoModalidadeController {

    private final RegulamentoModalidadeService service;

    public RegulamentoModalidadeController(RegulamentoModalidadeService service) {
        this.service = service;
    }

    @PostMapping("/{idModalidade}/adicionar-regra")
    @Operation(summary = "Adicionar Regra à Modalidade", description = "Adiciona uma regra específica (Ex: tempo de jogo) a uma modalidade existente.")
    public ResponseEntity<RegulamentoModalidadeModel> adicionarRegra(
            @PathVariable Long idModalidade,
            @RequestBody RegulamentoModalidadeModel regra) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.adicionarRegra(idModalidade, regra));
    }

    @GetMapping
    public ResponseEntity<List<RegulamentoModalidadeModel>> listarTodas() {
        return ResponseEntity.ok(service.listarRegras());
    }

    @PutMapping("/{idRegra}")
    @Operation(summary = "Atualizar Regra da Modalidade", description = "Corrige ou altera uma regra específica sem mudar a modalidade vinculada.")
    public ResponseEntity<RegulamentoModalidadeModel> atualizarRegra(
            @PathVariable Long idRegra,
            @RequestBody RegulamentoModalidadeModel regra) {

        return ResponseEntity.ok(service.atualizarRegulamentoModalidade(idRegra, regra));
    }

    @DeleteMapping("/{idRegra}")
    public ResponseEntity<Void> deletarRegra(@PathVariable Long idRegra) {
        service.deletarRegra(idRegra);
        return ResponseEntity.noContent().build();
    }
}