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
}
