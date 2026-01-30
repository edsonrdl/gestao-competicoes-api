package br.com.esportes.gestao_competicoes_api.modulo_campeonato.campeonato;

import org.springframework.stereotype.Service;

@Service
public class CampeonatoService {

    private  final CampeonatoRepository campeonatoRepository;

    public CampeonatoService(CampeonatoRepository campeonatoRepository) {
        this.campeonatoRepository = campeonatoRepository;
    }

    public CampeonatoModel criarCompeticao(CampeonatoModel competicaoModel) {
        return campeonatoRepository.save(competicaoModel);
    }
    public CampeonatoModel buscarCompeticaoPorId(Long idCompeticao) {
        return campeonatoRepository.findById(idCompeticao)
                .orElseThrow(() -> new RuntimeException("Competição não encontrada!"));
    }

    public CampeonatoModel atualizarCampeonato(Long idCompeticao, CampeonatoModel competicaoNovosDados) {
        CampeonatoModel competicaoExistente = campeonatoRepository.findById(idCompeticao)
                .orElseThrow(() -> new RuntimeException("Competição não encontrada com ID: " + idCompeticao));

        competicaoExistente.setNome(competicaoNovosDados.getNome());
        competicaoExistente.setRegulamento(competicaoNovosDados.getRegulamento());
        competicaoExistente.setDataInicio(competicaoNovosDados.getDataInicio());
        competicaoExistente.setDataFim(competicaoNovosDados.getDataFim());

        return campeonatoRepository.save(competicaoExistente);
    }
    public void deletarCampeonatoPorId(Long id) {
        CampeonatoModel CampeonatoModel =  campeonatoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campeonato não encontrada para exclusão."));

        campeonatoRepository.delete(CampeonatoModel);
    }
}
