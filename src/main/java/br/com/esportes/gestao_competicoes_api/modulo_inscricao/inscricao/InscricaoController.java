package br.com.esportes.gestao_competicoes_api.modulo_inscricao.inscricao;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inscricoes")
public class InscricaoController {

    private final InscricaoService inscricaoService;
    public InscricaoController(InscricaoService inscricaoService) {
        this.inscricaoService = inscricaoService;
    }

    @PostMapping
    public ResponseEntity<InscricaoModel> inscreverTime(
            @RequestParam Long idEquipe,
            @RequestParam Long idModalidade) {

        InscricaoModel inscricao = inscricaoService.realizarInscricao(idEquipe, idModalidade);
        return ResponseEntity.ok(inscricao);
    }
}