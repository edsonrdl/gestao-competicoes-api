package br.com.esportes.gestao_competicoes_api.modulo_inscricao;

import br.com.esportes.gestao_competicoes_api.modulo_inscricao.equipe.EquipeModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "atleta")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AtletaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(example = "Neymar Jr")
    private String nome;

    @Schema(example = "123.456.789-00")
    private String documento;
    
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "foto_dados", length = 10000000)
    @JsonIgnore
    private byte[] foto;

    @Column(name = "foto_tipo")
    @JsonIgnore
    private String fotoTipo;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "doc_imagem_dados", length = 10000000)
    @JsonIgnore
    private byte[] documentoImagem;

    @Column(name = "doc_imagem_tipo")
    @JsonIgnore
    private String documentoImagemTipo;

    @ManyToOne
    @JoinColumn(name = "equipe_id")
    @JsonIgnoreProperties({"atletas", "historicoParticipacoes", "documentacao", "contato"})
    private EquipeModel equipe;
}