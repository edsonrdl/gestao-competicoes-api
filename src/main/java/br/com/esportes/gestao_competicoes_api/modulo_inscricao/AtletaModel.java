package br.com.esportes.gestao_competicoes_api.modulo_inscricao;

import jakarta.persistence.*;

@Entity
public class AtletaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String documento; // CC ou Passaporte
    private String fotoUrl;

    @ManyToOne
    @JoinColumn(name = "equipa_id")
    private EquipeModel equipa;

    // Getters e Setters
}
