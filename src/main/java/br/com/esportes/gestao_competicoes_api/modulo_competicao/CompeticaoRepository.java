package br.com.esportes.gestao_competicoes_api.modulo_competicao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompeticaoRepository extends CrudRepository<CompeticaoModel, Long> {
}
