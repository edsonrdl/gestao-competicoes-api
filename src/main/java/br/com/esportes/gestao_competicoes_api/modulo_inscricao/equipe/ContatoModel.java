package br.com.esportes.gestao_competicoes_api.modulo_inscricao.equipe;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "contato")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContatoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY) // O usuário não envia ID
    private Long id;

    @Email(message = "O formato do email é inválido")
    @Column(nullable = false)
    @Schema(description = "E-mail oficial de contato", example = "diretoria@tabajarafutebol.com.br")
    private String email;

    @Column(name = "telefone_principal", nullable = false)
    @Schema(description = "Celular ou WhatsApp principal", example = "(11) 99876-5432")
    private String telefonePrincipal;

    @Column(name = "telefone_secundario")
    @Schema(description = "Telefone para recado ou fixo (Opcional)", example = "(11) 3322-1100")
    private String telefoneSecundario;

}