package br.com.esportes.gestao_competicoes_api.modulo_sorteio;

import br.com.esportes.gestao_competicoes_api.modulo_inscricao.InscricaoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/sorteio")
public class SorteiroController {

    private SorteioService sorteioService;

    public SorteiroController(SorteioService sorteioService, InscricaoRepository inscricaoRepository) {
        this.sorteioService = sorteioService;
    }

    @PostMapping("/realizar-sorteio")
    public ResponseEntity<List<GrupoModel>> realizarSorteio(
            @RequestParam Long modalidadeId,
            @RequestParam Integer numeroDeGrupos) {

        List<GrupoModel> grupos = sorteioService.realizarSorteio(modalidadeId, numeroDeGrupos);
        return ResponseEntity.ok(grupos);
    }
}
