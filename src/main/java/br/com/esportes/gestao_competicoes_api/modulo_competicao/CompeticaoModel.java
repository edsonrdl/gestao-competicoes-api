package br.com.esportes.gestao_competicoes_api.modulo_competicao;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class CompeticaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(columnDefinition = "TEXT")
    private String regulamento;

    private LocalDate dataInicio;

    private LocalDate dataFim;

    @OneToMany(mappedBy = "competicao")
    private List<ModalidadeModel> modalidades;

}