package br.com.esportes.gestao_competicoes_api.modulo_competicao;

import org.springframework.stereotype.Service;

@Service
public class CompeticaoService {

    private  final CompeticaoRepository competicaoRepository;

    public CompeticaoService(CompeticaoRepository competicaoRepository) {
        this.competicaoRepository = competicaoRepository;
    }

    public CompeticaoModel salvarCompeticao(CompeticaoModel competicaoModel) {
        return competicaoRepository.save(competicaoModel);
    }
    public CompeticaoModel buscarCompeticaoPorId(Long idCompeticao) {
        return competicaoRepository.findById(idCompeticao)
                .orElseThrow(() -> new RuntimeException("Competição não encontrada!"));
    }

    public CompeticaoModel atualizarCompeticao(Long idCompeticao, CompeticaoModel competicaoNovosDados) {
        CompeticaoModel competicaoExistente = competicaoRepository.findById(idCompeticao)
                .orElseThrow(() -> new RuntimeException("Competição não encontrada com ID: " + idCompeticao));

        competicaoExistente.setNome(competicaoNovosDados.getNome());
        competicaoExistente.setRegulamento(competicaoNovosDados.getRegulamento());
        competicaoExistente.setDataInicio(competicaoNovosDados.getDataInicio());
        competicaoExistente.setDataFim(competicaoNovosDados.getDataFim());

        return competicaoRepository.save(competicaoExistente);
    }
}
