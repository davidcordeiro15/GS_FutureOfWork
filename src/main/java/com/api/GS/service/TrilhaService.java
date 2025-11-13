package com.api.GS.service;

import com.api.GS.model.Trilhas;
import com.api.GS.model.UsuarioGS;
import com.api.GS.repository.TrilhaRepository;
import com.api.GS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrilhaService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TrilhaRepository trilhasRepository;

    public Trilhas cadastrarTrilha(Trilhas novaTrilha) {
        return trilhasRepository.save(novaTrilha);
    }

    public List<Trilhas> listarTrilhas() {
        return trilhasRepository.findAll();
    }

    public Optional<Trilhas> buscarPorId(int id) {
        return trilhasRepository.findById(id);
    }

    public Trilhas atualizarTrilha(int id, Trilhas trilhaAtualizada) {
        Optional<Trilhas> existente = trilhasRepository.findById(id);
        if (existente.isEmpty()) {
            return null;
        }

        Trilhas trilhas = existente.get();
        trilhas.setNome(trilhaAtualizada.getNome());
        trilhas.setConteudo(trilhaAtualizada.getConteudo());
        trilhas.setVideos(trilhaAtualizada.getVideos());
        trilhas.setQuantidadeDeMatriculados(trilhaAtualizada.getQuantidadeDeMatriculados());

        return trilhasRepository.save(trilhas);
    }

    public boolean deletarTrilha(int id) {
        if (trilhasRepository.existsById(id)) {
            trilhasRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public String matricularUsuarioEmTrilha(String emailUsuario, int idTrilha) {
        Optional<UsuarioGS> usuarioOpt = userRepository.findByEmail(emailUsuario);
        Optional<Trilhas> trilhaOpt = trilhasRepository.findById(idTrilha);

        if (usuarioOpt.isEmpty() || trilhaOpt.isEmpty()) {
            return "Usuário ou trilha não encontrados.";
        }

        UsuarioGS usuario = usuarioOpt.get();
        Trilhas trilha = trilhaOpt.get();

        if (usuario.getTrilhasMatriculadas().contains(trilha)) {
            return "Usuário já matriculado nessa trilha.";
        }

        usuario.getTrilhasMatriculadas().add(trilha);
        trilha.setQuantidadeDeMatriculados(trilha.getQuantidadeDeMatriculados() + 1);

        userRepository.save(usuario);
        trilhasRepository.save(trilha);

        return "Usuário matriculado na trilha com sucesso!";
    }
}
