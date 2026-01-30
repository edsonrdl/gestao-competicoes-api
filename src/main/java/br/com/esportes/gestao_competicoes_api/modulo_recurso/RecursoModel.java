package br.com.esportes.gestao_competicoes_api.modulo_recurso;

import br.com.esportes.gestao_competicoes_api.modulo_campeonato.campeonato.CampeonatoModel;
import br.com.esportes.gestao_competicoes_api.modulo_inscricao.EquipeModel;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class RecursoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Enumerated(EnumType.STRING)
    private StatusRecursoEnum status;

    @Column(columnDefinition = "TEXT")
    private String parecerComissao;

    private LocalDateTime dataSolicitacao;
    private LocalDateTime dataAnalise;

    @ManyToOne
    @JoinColumn(name = "equipa_id")
    private EquipeModel equipaSolicitante;

    @ManyToOne
    @JoinColumn(name = "competicao_id")
    private CampeonatoModel competicao;
}

