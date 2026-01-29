package br.com.esportes.gestao_competicoes_api.modulo_sorteio;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SorteioService {


//    private InscricaoRepository inscricaoRepo;
//
//    private GrupoRepository grupoRepo;
//
//    @Transactional
//    public void realizarSorteio(Long modalidadeId) {
//        // 1. Buscar todas as equipes inscritas na modalidade
//        List<Inscricao> inscritos = inscricaoRepo.findByModalidadeId(modalidadeId);
//
//        // 2. Separar cabeças de chave
//        List<Inscricao> cabecas = inscritos.stream().filter(Inscricao::isCabecaDeChave).toList();
//        List<Inscricao> normais = inscritos.stream().filter(i -> !i.isCabecaDeChave()).toList();
//
//        // 3. Algoritmo de distribuição (Simplificado)
//        // Criar Grupos (ex: A, B, C...)
//        // Distribuir cabeças de chave um em cada grupo
//        // Sortear (Shuffle) as normais e distribuir
//
//        // 4. Auditoria
//        String log = "Sorteio iniciado às " + LocalDateTime.now() + ". Total inscritos: " + inscritos.size();
//
//        // 5. Salvar Grupos com o Log
//    }
}