package com.api.GS.controller;

import com.api.GS.dto.TrilhaResponseDTO;
import com.api.GS.model.Trilhas;
import com.api.GS.model.UsuarioGS;
import com.api.GS.service.TrilhaService;
import com.api.GS.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/trilhas")
public class TrilhaController {

    @Autowired
    private TrilhaService trilhasService;

    @Autowired
    private UserService userService;

    /**
     * Autentica o usuário com base no email e senha enviados.
     */
    private UsuarioGS autenticar(String email, String senha) {
        UsuarioGS usuario = userService.autenticar(email, senha);
        if (usuario == null) {
            throw new RuntimeException("Credenciais inválidas.");
        }
        return usuario;
    }

    /**
     * Cadastrar uma nova trilha (apenas administradores podem criar).
     */
    @PostMapping("/cadastrar")
    public ResponseEntity<TrilhaResponseDTO> cadastrarTrilha(@RequestBody Map<String, Object> payload) {
        String email = (String) payload.get("email");
        String senha = (String) payload.get("senha");
        Map<String, Object> trilhaData = (Map<String, Object>) payload.get("trilha");

        UsuarioGS usuario = autenticar(email, senha);
        if (!usuario.isEadmin()) {
            return ResponseEntity.status(403)
                    .body(new TrilhaResponseDTO("Apenas administradores podem cadastrar trilhas", null));
        }

        Trilhas novaTrilha = new Trilhas();
        novaTrilha.setNome((String) trilhaData.get("nome"));
        novaTrilha.setConteudo((String) trilhaData.get("conteudo"));
        novaTrilha.setQuantidadeDeMatriculados(
                trilhaData.get("quantidadeDeMatriculados") != null ?
                        (int) trilhaData.get("quantidadeDeMatriculados") : 0
        );

        Trilhas criada = trilhasService.cadastrarTrilha(novaTrilha);
        return ResponseEntity.ok(new TrilhaResponseDTO("Trilha cadastrada com sucesso", criada));
    }

    /**
     * Listar todas as trilhas (qualquer usuário autenticado pode ver).
     */
    @GetMapping("/listar")
    public ResponseEntity<TrilhaResponseDTO> listarTrilhas() {
        List<Trilhas> lista = trilhasService.listarTrilhas();
        return ResponseEntity.ok(new TrilhaResponseDTO("Lista de trilhas obtida com sucesso", lista));
    }

    /**
     * Buscar uma trilha pelo ID (qualquer usuário autenticado pode ver).
     */
    @PostMapping("/buscar/{id}")
    public ResponseEntity<TrilhaResponseDTO> buscarPorId(
            @PathVariable int id,
            @RequestBody Map<String, Object> payload) {

        String email = (String) payload.get("email");
        String senha = (String) payload.get("senha");

        autenticar(email, senha);

        return trilhasService.buscarPorId(id)
                .map(trilha -> ResponseEntity.ok(new TrilhaResponseDTO("Trilha encontrada", trilha)))
                .orElse(ResponseEntity.status(404).body(new TrilhaResponseDTO("Trilha não encontrada", null)));
    }

    /**
     * Atualizar trilha existente (apenas administradores podem alterar).
     */
    @PostMapping("/atualizar/{id}")
    public ResponseEntity<TrilhaResponseDTO> atualizarTrilha(
            @PathVariable int id,
            @RequestBody Map<String, Object> payload) {

        String email = (String) payload.get("email");
        String senha = (String) payload.get("senha");
        Map<String, Object> trilhaData = (Map<String, Object>) payload.get("trilha");

        UsuarioGS usuario = autenticar(email, senha);
        if (!usuario.isEadmin()) {
            return ResponseEntity.status(403)
                    .body(new TrilhaResponseDTO("Apenas administradores podem atualizar trilhas", null));
        }

        Trilhas trilhaAtualizada = new Trilhas();
        trilhaAtualizada.setNome((String) trilhaData.get("nome"));
        trilhaAtualizada.setConteudo((String) trilhaData.get("conteudo"));
        trilhaAtualizada.setQuantidadeDeMatriculados(
                trilhaData.get("quantidadeDeMatriculados") != null ?
                        (int) trilhaData.get("quantidadeDeMatriculados") : 0
        );

        Trilhas atualizada = trilhasService.atualizarTrilha(id, trilhaAtualizada);
        if (atualizada == null) {
            return ResponseEntity.status(404).body(new TrilhaResponseDTO("Trilha não encontrada", null));
        }
        return ResponseEntity.ok(new TrilhaResponseDTO("Trilha atualizada com sucesso", atualizada));
    }

    /**
     * Deletar uma trilha (apenas administradores podem deletar).
     */
    @PostMapping("/deletar/{id}")
    public ResponseEntity<TrilhaResponseDTO> deletarTrilha(
            @PathVariable int id,
            @RequestBody Map<String, Object> payload) {

        String email = (String) payload.get("email");
        String senha = (String) payload.get("senha");

        UsuarioGS usuario = autenticar(email, senha);
        if (!usuario.isEadmin()) {
            return ResponseEntity.status(403)
                    .body(new TrilhaResponseDTO("Apenas administradores podem deletar trilhas", null));
        }

        boolean deletou = trilhasService.deletarTrilha(id);
        if (!deletou) {
            return ResponseEntity.status(404).body(new TrilhaResponseDTO("Trilha não encontrada", null));
        }
        return ResponseEntity.ok(new TrilhaResponseDTO("Trilha deletada com sucesso", null));
    }

    @PostMapping("/matricular")
    public ResponseEntity<String> matricularUsuario(@RequestBody Map<String, Object> payload) {

        String emailUsuario = (String) payload.get("emailUsuario");
        Integer idTrilha = (Integer) payload.get("idTrilha");

        if (emailUsuario == null || idTrilha == null) {
            return ResponseEntity.badRequest().body("{\"erro\": \"Campos obrigatórios: emailUsuario e idTrilha\"}");
        }

        String resultado = trilhasService.matricularUsuarioEmTrilha(emailUsuario, idTrilha);

        if (resultado.contains("sucesso")) {
            return ResponseEntity.ok("{\"mensagem\": \"" + resultado + "\"}");
        } else {
            return ResponseEntity.badRequest().body("{\"erro\": \"" + resultado + "\"}");
        }
    }


}
