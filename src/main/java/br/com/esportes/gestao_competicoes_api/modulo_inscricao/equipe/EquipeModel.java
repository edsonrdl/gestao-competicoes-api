package br.com.esportes.gestao_competicoes_api.modulo_inscricao.equipe;

import br.com.esportes.gestao_competicoes_api.modulo_inscricao.AtletaModel;
import br.com.esportes.gestao_competicoes_api.modulo_inscricao.ContatoModel;
import br.com.esportes.gestao_competicoes_api.modulo_inscricao.InscricaoModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "equipe")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EquipeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "nome_responsavel")
    private String nomeResponsavel;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contato_id", referencedColumnName = "id")
    private ContatoModel contato;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "doc_conteudo", length = 10000000)
    private byte[] documentacao;

    @Column(name = "doc_tipo")
    private String documentacaoTipo;


    @OneToMany(mappedBy = "equipe", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("equipe")
    private List<InscricaoModel> historicoParticipacoes = new ArrayList<>();

    @OneToMany(mappedBy = "equipe", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("equipe")
    private List<AtletaModel> atletas = new ArrayList<>();
}