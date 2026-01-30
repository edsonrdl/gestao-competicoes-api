package br.com.esportes.gestao_competicoes_api.modulo_inscricao.atleta;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "atleta_documento_id", referencedColumnName = "id")
    private AtletaDocumentoModel atletaDocumentoModel;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "foto_dados", length = 10000000)
    @JsonIgnore
    private byte[] foto;

    @Column(name = "foto_tipo")
    @JsonIgnore
    private String fotoTipo;

    @ManyToOne
    @JoinColumn(name = "equipe_id")
    @JsonIgnoreProperties({"atletas", "historicoParticipacoes", "documentacao", "contato"})
    private EquipeModel equipe;
}