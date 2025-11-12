package com.api.GS.controller;

import com.api.GS.dto.UserRequestDTO;
import com.api.GS.model.UsuarioGS;
import com.api.GS.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Cadastra um novo usuário no sistema.
     * Espera um JSON contendo email, senha e nome.
     */
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarUsuario(@RequestBody UserRequestDTO userDTO) {
        try {
            UsuarioGS novoUsuario = userService.cadastrarUsuario(userDTO);
            return ResponseEntity.ok(novoUsuario);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    /**
     * Realiza o login do usuário e retorna um token (ou mensagem de erro).
     */
    @PostMapping("/login")
    public ResponseEntity<?> logarNaConta(@RequestBody UserRequestDTO user) {
        String token = userService.loginComToken(user.getEmail(), user.getSenha());

        if (token != null) {
            return ResponseEntity.ok().body("{\"token\": \"" + token + "\"}");
        } else {
            return ResponseEntity.status(401).body("{\"erro\": \"Email ou senha incorretos.\"}");
        }
    }

    /**
     * Atualiza os dados de um usuário existente.
     * O ID deve ser informado na URL e os novos dados no corpo da requisição.
     */
    @PutMapping("/alterar")
    public ResponseEntity<String> alterarUsuario(@RequestBody UserRequestDTO userDTO) {
        try {
            userService.cadastrarUsuario(userDTO);
            return ResponseEntity.ok("Usuário alterado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }


    /**
     * Exclui um usuário com base no ID.
     */
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarUsuario(@PathVariable int id) {
        boolean deletado = userService.deletarUsuario(id);
        if (deletado) {
            return ResponseEntity.ok("Usuário deletado com sucesso.");
        } else {
            return ResponseEntity.status(404).body("Usuário não encontrado.");
        }
    }
}
