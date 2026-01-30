package br.com.esportes.gestao_competicoes_api.modulo_campeonato.modalidade;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/modalidade")
public class ModalidadeController {

    private final ModalidadeService modalidadeService;

    public ModalidadeController(ModalidadeService modalidadeService) {
        this.modalidadeService = modalidadeService;
    }

    @PostMapping("/{idCampeonato}/criar-modalidade")
    public ResponseEntity<ModalidadeModel> criarModalidade(
            @PathVariable Long idCampeonato,
            @RequestBody @Valid ModalidadeModel modalidade) {

        ModalidadeModel novaModalidade = modalidadeService.criarModalidade(idCampeonato, modalidade);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaModalidade);
    }

    @GetMapping("/buscar-modalidade-por-id/{idCampeonato}")
    public ResponseEntity<ModalidadeModel> buscarModalidadePorId(@PathVariable Long idCampeonato) {
        ModalidadeModel modalidadeModel=modalidadeService.buscarModalidadePorId(idCampeonato);
        return ResponseEntity.status(HttpStatus.OK).body(modalidadeModel);
    }

    @PutMapping("/atualizar-modalidade/{idModalidade}")
    @Operation(summary = "Atualizar modalidade", description = "Perfil usado:      /Atualiza os dados de uma modalidade existente pelo  pelo id da modalidade.")
    public ResponseEntity<ModalidadeModel> atualizarModalidade(
            @PathVariable Long idModalidade,
            @RequestBody ModalidadeModel modalidadeModel) {

        ModalidadeModel modalidadeAtualizada = modalidadeService.atualizarModalidade(idModalidade, modalidadeModel);
        return ResponseEntity.ok(modalidadeAtualizada);
    }

    @DeleteMapping("/deletar-modalidade/{idModalidedade}")
    public ResponseEntity<Void> deletarModalidade(@PathVariable Long idModalidedade) {
        modalidadeService.deletarModalidadePorId(idModalidedade);
        return ResponseEntity.noContent().build();
    }
}
