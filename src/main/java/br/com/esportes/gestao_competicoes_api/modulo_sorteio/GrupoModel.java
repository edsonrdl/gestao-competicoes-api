package br.com.esportes.gestao_competicoes_api.modulo_sorteio;

import br.com.esportes.gestao_competicoes_api.modulo_competicao.ModalidadeModel;
import br.com.esportes.gestao_competicoes_api.modulo_inscricao.InscricaoModel;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class GrupoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome; // Ex: Grupo A

    @ManyToOne
    @JoinColumn(name = "modalidade_id")
    private ModalidadeModel modalidade;

    @Column(columnDefinition = "TEXT")
    private String logAuditoriaSorteio;

    @OneToMany(mappedBy = "grupo")
    private List<InscricaoModel> equipasDoGrupo;
}