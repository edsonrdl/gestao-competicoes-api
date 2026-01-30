package br.com.esportes.gestao_competicoes_api.modulo_inscricao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipeRepository extends JpaRepository<EquipeModel,Long> {
}
