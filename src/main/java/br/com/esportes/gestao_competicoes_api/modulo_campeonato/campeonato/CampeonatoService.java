package br.com.esportes.gestao_competicoes_api.modulo_campeonato;

import org.springframework.stereotype.Service;

@Service
public class CampeonatoService {

    private  final CampeonatoRepository competicaoRepository;

    public CampeonatoService(CampeonatoRepository competicaoRepository) {
        this.competicaoRepository = competicaoRepository;
    }

    public CampeonatoModel salvarCompeticao(CampeonatoModel competicaoModel) {
        return competicaoRepository.save(competicaoModel);
    }
    public CampeonatoModel buscarCompeticaoPorId(Long idCompeticao) {
        return competicaoRepository.findById(idCompeticao)
                .orElseThrow(() -> new RuntimeException("Competição não encontrada!"));
    }

    public CampeonatoModel atualizarCompeticao(Long idCompeticao, CampeonatoModel competicaoNovosDados) {
        CampeonatoModel competicaoExistente = competicaoRepository.findById(idCompeticao)
                .orElseThrow(() -> new RuntimeException("Competição não encontrada com ID: " + idCompeticao));

        competicaoExistente.setNome(competicaoNovosDados.getNome());
        competicaoExistente.setRegulamento(competicaoNovosDados.getRegulamento());
        competicaoExistente.setDataInicio(competicaoNovosDados.getDataInicio());
        competicaoExistente.setDataFim(competicaoNovosDados.getDataFim());

        return competicaoRepository.save(competicaoExistente);
    }
}
