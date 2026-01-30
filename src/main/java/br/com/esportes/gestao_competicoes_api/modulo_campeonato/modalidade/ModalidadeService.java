package br.com.esportes.gestao_competicoes_api.modulo_campeonato;

import org.springframework.stereotype.Service;

@Service
public class ModalidadeService {

    private ModalidadeRepository modalidadeRepository;

    private CampeonatoRepository competicaoRepository;

    public ModalidadeService(ModalidadeRepository modalidadeRepository, CampeonatoRepository competicaoRepository) {
        this.modalidadeRepository = modalidadeRepository;
        this.competicaoRepository = competicaoRepository;
    }

    public ModalidadeModel salvarModalidade(Long idCompeticao, ModalidadeModel modalidade) {
        CampeonatoModel campeonato = competicaoRepository.findById(idCompeticao)
                .orElseThrow(() -> new RuntimeException("Campeonato não encontrada!"));

        modalidade.setCampeonato(campeonato);

        return modalidadeRepository.save(modalidade);
    }
}