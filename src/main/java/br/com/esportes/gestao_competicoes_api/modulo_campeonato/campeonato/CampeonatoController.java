package br.com.esportes.gestao_competicoes_api.modulo_campeonato.campeonato;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/campeonato")
public class CampeonatoController {

    private CampeonatoService campeonatoService;

    public CampeonatoController(CampeonatoService campeonatoService) {
        this.campeonatoService = campeonatoService;
    }

    @PostMapping("/criar-campeonato")
    @Operation(summary = "Criar novo campeonato", description = "Perfil: Organizador (Comissão Técnica)/Cadastra um novo campeonato com suas datas e regulamento.")
    public ResponseEntity<CampeonatoModel> criarCampeonato(@Valid @RequestBody CampeonatoModel campeonatoModel){
        CampeonatoModel competicaoSalva = campeonatoService.criarCampeonato(campeonatoModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(competicaoSalva);

    }

    @GetMapping("/buscar-campeonato-por-id/{idCampeonato}")
    public ResponseEntity<CampeonatoModel> buscarCampeonatoPorId(@PathVariable Long idCampeonato) {
        CampeonatoModel campeonatoModel = campeonatoService.buscarCompeticaoPorId(idCampeonato);
        return ResponseEntity.ok(campeonatoModel);
    }

    @PutMapping("/atualizar-campeonato/{idCampeonato}")
    @Operation(summary = "Atualizar campeonato", description = "Atualiza os dados de uma campeonato existente.")
    public ResponseEntity<CampeonatoModel> atualizarCampeonato(
            @PathVariable Long idCampeonato,
            @RequestBody CampeonatoModel campeonatoModel) {

        CampeonatoModel competicaoAtualizada = campeonatoService.atualizarCampeonato(idCampeonato, campeonatoModel);
        return ResponseEntity.ok(competicaoAtualizada);
    }

    @DeleteMapping("/deletar-campeonato/{idCampeonato}")
    public ResponseEntity<Void> deletarModalidade(@PathVariable Long idCampeonato) {
        campeonatoService.deletarCampeonatoPorId(idCampeonato);
        return ResponseEntity.noContent().build();
    }
}
