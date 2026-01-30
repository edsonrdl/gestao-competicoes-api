package br.com.esportes.gestao_competicoes_api.modulo_campeonato.modalidade.regulamento;


import br.com.esportes.gestao_competicoes_api.modulo_campeonato.modalidade.ModalidadeModel;
import br.com.esportes.gestao_competicoes_api.modulo_campeonato.modalidade.ModalidadeRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RegulamentoModalidadeService {

    private final RegulamentoModalidadeRepository regulamentoRepository;
    private final ModalidadeRepository modalidadeRepository;

    public RegulamentoModalidadeService(RegulamentoModalidadeRepository regulamentoRepository,
                                        ModalidadeRepository modalidadeRepository) {
        this.regulamentoRepository = regulamentoRepository;
        this.modalidadeRepository = modalidadeRepository;
    }

    public RegulamentoModalidadeModel adicionarRegra(Long idModalidade, RegulamentoModalidadeModel regra) {
        ModalidadeModel modalidade = modalidadeRepository.findById(idModalidade)
                .orElseThrow(() -> new RuntimeException("Modalidade não encontrada com ID: " + idModalidade));

        regra.setModalidade(modalidade);
        return regulamentoRepository.save(regra);
    }

    public List<RegulamentoModalidadeModel> listarRegras() {
        return regulamentoRepository.findAll();
    }

    public RegulamentoModalidadeModel atualizarRegulamentoModalidade(Long idRegra, RegulamentoModalidadeModel novosDados) {

        RegulamentoModalidadeModel regraExistente = regulamentoRepository.findById(idRegra)
                .orElseThrow(() -> new RuntimeException("Regra não encontrada com ID: " + idRegra));

        regraExistente.setTitulo(novosDados.getTitulo());
        regraExistente.setDescricao(novosDados.getDescricao());


        return regulamentoRepository.save(regraExistente);
    }

    public void deletarRegra(Long idRegra) {
        if (!regulamentoRepository.existsById(idRegra)) {
            throw new RuntimeException("Regra não encontrada para exclusão.");
        }
        regulamentoRepository.deleteById(idRegra);
    }
}
