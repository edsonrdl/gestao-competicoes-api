package br.com.esportes.gestao_competicoes_api.modulo_inscricao.inscricao;


import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Inscrever equipes", description = "Perfil: Representante da Equipe /Inscrever as equipes equipe nas modalidades do campeonato.")
    public ResponseEntity<InscricaoModel> inscreverTime(
            @RequestParam Long idEquipe,
            @RequestParam Long idModalidade) {

        InscricaoModel inscricao = inscricaoService.realizarInscricao(idEquipe, idModalidade);
        return ResponseEntity.ok(inscricao);
    }
}