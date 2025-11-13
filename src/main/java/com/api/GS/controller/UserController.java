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
     * Cadastra um novo usuário e retorna um token de acesso.
     */
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarUsuario(@RequestBody UserRequestDTO userDTO) {
        try {
            UsuarioGS novoUsuario = userService.cadastrarUsuario(userDTO);

            // Gera token automaticamente após cadastro
            String token = userService.loginComToken(userDTO.getEmail(), userDTO.getSenha());

            return ResponseEntity.ok()
                    .body("{\"mensagem\": \"Usuário cadastrado com sucesso.\", \"token\": \"" + token + "\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"erro\": \"" + e.getMessage() + "\"}");
        }
    }

    /**
     * Realiza o login do usuário e retorna um token JWT.
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
     * Atualiza os dados de um usuário com base no email.
     * O corpo da requisição deve conter o email e os novos dados.
     */
    @PutMapping("/alterar")
    public ResponseEntity<?> alterarUsuario(@RequestBody UserRequestDTO userDTO) {
        try {
            UsuarioGS usuarioAtualizado = userService.atualizarPorEmail(userDTO);
            return ResponseEntity.ok(usuarioAtualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"erro\": \"" + e.getMessage() + "\"}");
        }
    }

    /**
     * Exclui um usuário com base no ID.
     */
    @DeleteMapping("/deletar/{email}")
    public ResponseEntity<?> deletarUsuario(@PathVariable String email) {
        boolean deletado = userService.deletarUsuarioPorEmail(email);
        if (deletado) {
            return ResponseEntity.ok("{\"mensagem\": \"Usuário deletado com sucesso.\"}");
        } else {
            return ResponseEntity.status(404).body("{\"erro\": \"Usuário não encontrado.\"}");
        }
    }

}
