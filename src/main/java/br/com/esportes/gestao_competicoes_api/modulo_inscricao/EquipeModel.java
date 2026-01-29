package br.com.esportes.gestao_competicoes_api.modulo_inscricao;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class EquipeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String nomeResponsavel;
    private String contato;        // Email ou telemóvel
    private String documentacaoUrl; // Link para ficheiros no OneDrive/Drive

    @OneToMany(mappedBy = "equipa", cascade = CascadeType.ALL)
    private List<AtletaModel> atletas;

    // Getters e Setters
}