package br.com.esportes.gestao_competicoes_api.modulo_competicao;

import jakarta.persistence.*;

@Entity
public class ModalidadeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "competicao_id")
    private CompeticaoModel competicao;

}