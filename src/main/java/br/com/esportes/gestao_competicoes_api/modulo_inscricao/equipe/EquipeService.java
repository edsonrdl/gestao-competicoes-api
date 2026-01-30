package br.com.esportes.gestao_competicoes_api.modulo_inscricao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class EquipeService {


    private EquipeRepository equipeRepository;

    public EquipeService(EquipeRepository equipeRepository) {
        this.equipeRepository = equipeRepository;
    }

    public void atualizarDocumentacao(Long idEquipe, MultipartFile arquivo) throws IOException {
        EquipeModel equipe = equipeRepository.findById(idEquipe)
                .orElseThrow(() -> new RuntimeException("Equipe não encontrada"));

        String tipoArquivo = arquivo.getContentType();

        if (tipoArquivo == null || !tipoArquivo.startsWith("image/")) {
            throw new RuntimeException("Formato inválido! Por favor, envie apenas fotos (JPG, PNG).");
        }

        equipe.setDocumentacao(arquivo.getBytes());
        equipe.setDocumentacaoTipo(tipoArquivo);

        equipeRepository.save(equipe);
    }

    // Método auxiliar para recuperar a imagem depois
    public EquipeModel buscarEquipe(Long id) {
        return equipeRepository.findById(id).orElseThrow();
    }
}