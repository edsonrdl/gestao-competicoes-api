package br.com.esportes.gestao_competicoes_api.modulo_campeonato;


import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/competicoes")
public class CampeonatoController {

    private CampeonatoService competicaoService;

    public CampeonatoController(CampeonatoService competicaoService) {
        this.competicaoService = competicaoService;
    }

    @PostMapping("/salvar-competicao")
    @Operation(summary = "Criar nova competição", description = "Cadastra um novo campeonato com suas datas e regulamento.")
    public ResponseEntity<CampeonatoModel> salvarCompeticao(@Valid @RequestBody CampeonatoModel competicaoModel){
        CampeonatoModel competicaoSalva = competicaoService.salvarCompeticao(competicaoModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(competicaoSalva);

    }

    @GetMapping("/{idCompeticao}")
    public ResponseEntity<CampeonatoModel> buscarCampeonatoPorId(@PathVariable Long idCompeticao) {
        CampeonatoModel competicaoModel = competicaoService.buscarCompeticaoPorId(idCompeticao);
        return ResponseEntity.ok(competicaoModel);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar competição", description = "Atualiza os dados de uma competição existente.")
    public ResponseEntity<CampeonatoModel> atualizarCamp(
            @PathVariable Long id,
            @RequestBody CampeonatoModel competicaoModel) {

        CampeonatoModel competicaoAtualizada = competicaoService.atualizarCompeticao(id, competicaoModel);
        return ResponseEntity.ok(competicaoAtualizada);
    }

}
