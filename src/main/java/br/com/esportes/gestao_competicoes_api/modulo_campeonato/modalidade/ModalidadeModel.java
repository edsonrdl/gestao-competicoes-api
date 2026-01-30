package br.com.esportes.gestao_competicoes_api.modulo_campeonato;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "modalidade")
public class ModalidadeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(name="nome")
    @Schema(description = "Nome da modalidade", example =  "Futsal Masculino", type = "string")
    private String nome;

    @ManyToOne
    @JoinColumn(name = "campeonato_id")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonIgnore
    private CampeonatoModel campeonato;

}