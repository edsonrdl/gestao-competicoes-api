package br.com.esportes.gestao_competicoes_api.modulo_inscricao.equipe;

import br.com.esportes.gestao_competicoes_api.modulo_inscricao.atleta.AtletaModel;
import br.com.esportes.gestao_competicoes_api.modulo_inscricao.inscricao.InscricaoModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Nome oficial da equipe", example = "Tabajara Futebol Clube")
    private String nome;

    @Column(name = "nome_responsavel")
    @Schema(description = "Nome do responsável/técnico", example = "Seu Creysson")
    private String nomeResponsavel;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contato_id", referencedColumnName = "id")
    private ContatoModel contato;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "doc_conteudo", length = 10000000)
    @JsonIgnore
    private byte[] documentacao;

    @Column(name = "doc_tipo")
    @JsonIgnore
    private String documentacaoTipo;

    @OneToMany(mappedBy = "equipe", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("equipe")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private List<InscricaoModel> historicoParticipacoes = new ArrayList<>();

    @OneToMany(mappedBy = "equipe", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("equipe")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private List<AtletaModel> atletas = new ArrayList<>();
}