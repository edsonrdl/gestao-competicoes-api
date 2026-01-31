package br.com.esportes.gestao_competicoes_api.modulo_recurso;

import br.com.esportes.gestao_competicoes_api.modulo_campeonato.campeonato.CampeonatoModel;
import br.com.esportes.gestao_competicoes_api.modulo_campeonato.campeonato.CampeonatoRepository;
import br.com.esportes.gestao_competicoes_api.modulo_inscricao.equipe.EquipeModel;
import br.com.esportes.gestao_competicoes_api.modulo_inscricao.equipe.EquipeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecursoService {

    private RecursoRepository recursoRepository;

    private EquipeRepository equipeRepository;

    private CampeonatoRepository campeonatoRepository;

    public RecursoService(RecursoRepository recursoRepository,
                         EquipeRepository equipeRepository,
                         CampeonatoRepository campeonatoRepository) {
        this.recursoRepository = recursoRepository;
        this.equipeRepository = equipeRepository;
        this.campeonatoRepository = campeonatoRepository;
    }


    public RecursoModel abrirRecurso(Long idEquipe, Long idCampeonato, String descricao) {
        EquipeModel equipe = equipeRepository.findById(idEquipe)
                .orElseThrow(() -> new RuntimeException("Equipe não encontrada"));

        CampeonatoModel campeonato = campeonatoRepository.findById(idCampeonato)
                .orElseThrow(() -> new RuntimeException("Campeonato não encontrado"));

        RecursoModel recurso = new RecursoModel();
        recurso.setDescricao(descricao);
        recurso.setEquipeSolicitante(equipe);
        recurso.setCampeonato(campeonato);
        recurso.setStatus(StatusRecursoEnum.PENDENTE);
        recurso.setDataSolicitacao(LocalDateTime.now());

        return recursoRepository.save(recurso);
    }

    public RecursoModel analisarRecurso(Long idRecurso, String parecer, boolean deferido) {
        RecursoModel recurso = recursoRepository.findById(idRecurso)
                .orElseThrow(() -> new RuntimeException("Recurso não encontrado"));

        recurso.setParecerComissao(parecer);
        recurso.setDataAnalise(LocalDateTime.now());
        if (deferido) {
            recurso.setStatus(StatusRecursoEnum.DEFERIDO);
        } else {
            recurso.setStatus(StatusRecursoEnum.INDEFERIDO);
        }

        return recursoRepository.save(recurso);
    }

}
