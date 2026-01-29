package br.com.esportes.gestao_competicoes_api.modulo_competicao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModalidadeService {

    private ModalidadeRepository modalidadeRepository;

    private CompeticaoRepository competicaoRepository;

    public ModalidadeService(ModalidadeRepository modalidadeRepository,CompeticaoRepository competicaoRepository) {
        this.modalidadeRepository = modalidadeRepository;
        this.competicaoRepository = competicaoRepository;
    }

    public ModalidadeModel salvarModalidade(Long idCompeticao, ModalidadeModel modalidade) {
        CompeticaoModel competicaoPai = competicaoRepository.findById(idCompeticao)
                .orElseThrow(() -> new RuntimeException("Competição não encontrada!"));

        modalidade.setCompeticao(competicaoPai);

        return modalidadeRepository.save(modalidade);
    }
}