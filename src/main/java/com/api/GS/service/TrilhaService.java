package com.api.GS.service;

import com.api.GS.model.trilhas;
import com.api.GS.repository.TrilhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrilhaService {

    @Autowired
    private TrilhaRepository trilhasRepository;

    public trilhas cadastrarTrilha(trilhas novaTrilha) {
        return trilhasRepository.save(novaTrilha);
    }

    public List<trilhas> listarTrilhas() {
        return trilhasRepository.findAll();
    }

    public Optional<trilhas> buscarPorId(int id) {
        return trilhasRepository.findById(id);
    }

    public trilhas atualizarTrilha(int id, trilhas trilhaAtualizada) {
        Optional<trilhas> existente = trilhasRepository.findById(id);
        if (existente.isEmpty()) {
            return null;
        }

        trilhas trilha = existente.get();
        trilha.setNome(trilhaAtualizada.getNome());
        trilha.setConteudo(trilhaAtualizada.getConteudo());
        trilha.setVideos(trilhaAtualizada.getVideos());
        trilha.setQuantidadeDeMatriculados(trilhaAtualizada.getQuantidadeDeMatriculados());

        return trilhasRepository.save(trilha);
    }

    public boolean deletarTrilha(int id) {
        if (trilhasRepository.existsById(id)) {
            trilhasRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
