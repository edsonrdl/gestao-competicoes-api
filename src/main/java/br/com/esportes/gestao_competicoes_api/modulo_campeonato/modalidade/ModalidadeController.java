package br.com.esportes.gestao_competicoes_api.modulo_campeonato;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/competicoes")
public class ModalidadeController {


    private final ModalidadeService modalidadeService;

    public ModalidadeController(ModalidadeService modalidadeService) {
        this.modalidadeService = modalidadeService;
    }

    @PostMapping("/{idCompeticao}/modalidades")
    public ResponseEntity<ModalidadeModel> criarModalidade(
            @PathVariable Long idCompeticao,
            @RequestBody @Valid ModalidadeModel modalidade) {

        ModalidadeModel novaModalidade = modalidadeService.salvarModalidade(idCompeticao, modalidade);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaModalidade);
    }
}
