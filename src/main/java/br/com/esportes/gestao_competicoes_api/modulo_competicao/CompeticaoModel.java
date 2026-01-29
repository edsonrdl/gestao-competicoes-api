package br.com.esportes.gestao_competicoes_api.modulo_competicao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "competicao")
public class CompeticaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "regulamento")
    private String regulamento;

    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    @Column(name = "datafim")
    private LocalDate dataFim;

    @OneToMany(mappedBy = "competicao")
    private List<ModalidadeModel> modalidades;

}