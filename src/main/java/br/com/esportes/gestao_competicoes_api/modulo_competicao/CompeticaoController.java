package br.com.esportes.gestao_competicoes_api.modulo_competicao;


import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
