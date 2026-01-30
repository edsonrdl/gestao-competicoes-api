package br.com.esportes.gestao_competicoes_api.modulo_inscricao.equipe;

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
    private Long id;

    @Email(message = "O formato do email é inválido")
    @Column(nullable = false)
    private String email;

    @Column(name = "telefone_principal", nullable = false)
    private String telefonePrincipal;

    @Column(name = "telefone_secundario")
    private String telefoneSecundario;

}