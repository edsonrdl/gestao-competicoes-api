package br.com.esportes.gestao_competicoes_api.modulo_inscricao.atleta;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF; // Se tiver a dependência do Hibernate Validator

@Entity
@Table(name = "atleta_documento")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AtletaDocumentoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @CPF(message = "CPF inválido")
    @Column(unique = true)
    @Schema(description = "CPF do atleta (apenas números)", example = "123.456.789-00")
    private String cpf;

    @Schema(description = "Registro Geral (RG)", example = "12.345.678-9")
    private String rg;

    @Schema(description = "Órgão Emissor do RG", example = "SSP/SP")
    private String orgaoEmissor;
}
