package com.api.GS.controller;

import com.api.GS.dto.TrilhaResponseDTO;
import com.api.GS.model.Trilhas;
import com.api.GS.service.TrilhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trilhas")
public class TrilhaController {

    @Autowired
    private TrilhaService trilhasService;

    // Cadastrar nova trilha
    @PostMapping
    public ResponseEntity<TrilhaResponseDTO> cadastrarTrilha(@RequestBody Trilhas novaTrilha) {
        Trilhas criada = trilhasService.cadastrarTrilha(novaTrilha);
        return ResponseEntity.ok(new TrilhaResponseDTO("Trilha cadastrada com sucesso", criada));
    }

    // Listar todas as trilhas
    @GetMapping
    public ResponseEntity<TrilhaResponseDTO> listarTrilhas() {
        List<Trilhas> lista = trilhasService.listarTrilhas();
        return ResponseEntity.ok(new TrilhaResponseDTO("Lista de trilhas obtida com sucesso", lista));
    }

    // Buscar trilha por ID
    @GetMapping("/{id}")
    public ResponseEntity<TrilhaResponseDTO> buscarPorId(@PathVariable int id) {
        return trilhasService.buscarPorId(id)
                .map(trilhas -> ResponseEntity.ok(new TrilhaResponseDTO("Trilha encontrada", trilhas)))
                .orElse(ResponseEntity.status(404).body(new TrilhaResponseDTO("Trilha não encontrada", null)));
    }

    // Atualizar trilha
    @PutMapping("/{id}")
    public ResponseEntity<TrilhaResponseDTO> atualizarTrilha(@PathVariable int id, @RequestBody Trilhas trilhaAtualizada) {
        Trilhas atualizada = trilhasService.atualizarTrilha(id, trilhaAtualizada);
        if (atualizada == null) {
            return ResponseEntity.status(404).body(new TrilhaResponseDTO("Trilha não encontrada", null));
        }
        return ResponseEntity.ok(new TrilhaResponseDTO("Trilha atualizada com sucesso", atualizada));
    }

    // Deletar trilha
    @DeleteMapping("/{id}")
    public ResponseEntity<TrilhaResponseDTO> deletarTrilha(@PathVariable int id) {
        boolean deletou = trilhasService.deletarTrilha(id);
        if (!deletou) {
            return ResponseEntity.status(404).body(new TrilhaResponseDTO("Trilha não encontrada", null));
        }
        return ResponseEntity.ok(new TrilhaResponseDTO("Trilha deletada com sucesso", null));
    }
}
