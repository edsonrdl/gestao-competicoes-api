package br.com.esportes.gestao_competicoes_api.modulo_inscricao;

import br.com.esportes.gestao_competicoes_api.modulo_competicao.ModalidadeModel;
import br.com.esportes.gestao_competicoes_api.modulo_sorteio.GrupoModel;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class InscricaoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "equipa_id")
    private EquipeModel equipa;

    // Alterado de Competicao para Modalidade para maior precisão
    @ManyToOne
    @JoinColumn(name = "modalidade_id")
    private ModalidadeModel modalidade;

    private boolean cabecaDeChave; // Para o algoritmo de sorteio

    @ManyToOne
    @JoinColumn(name = "grupo_id") // Preenchido após o sorteio ser realizado
    private GrupoModel grupo;

    private LocalDateTime dataInscricao;
}

