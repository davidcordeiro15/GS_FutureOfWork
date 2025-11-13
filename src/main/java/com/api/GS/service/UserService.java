package com.api.GS.service;

import com.api.GS.dto.UserRequestDTO;
import com.api.GS.model.UsuarioGS;
import com.api.GS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Cadastra um novo usuário no banco de dados.
     * Verifica se o e-mail já está em uso antes de salvar.
     */
    public UsuarioGS cadastrarUsuario(UserRequestDTO dto) {
        Optional<UsuarioGS> existente = userRepository.findByEmail(dto.getEmail());

        if (existente.isPresent()) {
            throw new RuntimeException("E-mail já cadastrado.");
        }

        UsuarioGS novo = new UsuarioGS();
        novo.setId(gerarNovoId());
        novo.setEmail(dto.getEmail());
        novo.setSenha(dto.getSenha());
        novo.setNome(dto.getNome());
        novo.setEstaTrabalhando(false);

        return userRepository.save(novo);
    }

    /**
     * Realiza o login verificando e-mail e senha.
     * Caso as credenciais sejam válidas, retorna um token simples.
     */
    public String loginComToken(String email, String senha) {
        Optional<UsuarioGS> usuario = userRepository.findByEmail(email);

        if (usuario.isPresent() && usuario.get().getSenha().equals(senha)) {
            return gerarTokenSimples(usuario.get());
        }

        return null;
    }

    /**
     * Atualiza os dados de um usuário existente com base no ID.
     */
    public UsuarioGS atualizarPorEmail(UserRequestDTO dto) {
        UsuarioGS usuario = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o email: " + dto.getEmail()));

        usuario.setNome(dto.getNome());
        usuario.setSenha(dto.getSenha());
        usuario.setEstaTrabalhando(dto.isEstaTrabalhando());

        return userRepository.save(usuario);
    }


    /**
     * Exclui um usuário do banco de dados pelo email.
     * Retorna true se o usuário foi deletado, false caso não exista.
     */
    public boolean deletarUsuarioPorEmail(String email) {
        Optional<UsuarioGS> usuarioOpt = userRepository.findByEmail(email);

        if (usuarioOpt.isPresent()) {
            userRepository.delete(usuarioOpt.get());
            return true;
        } else {
            return false;
        }
    }


    /**
     * Gera um token simples baseado em UUID.
     * Esse método é apenas ilustrativo — em produção, use JWT.
     */
    private String gerarTokenSimples(UsuarioGS usuario) {
        return UUID.randomUUID().toString();
    }

    /**
     * Gera um ID incremental simples.
     * Em produção, é melhor usar @GeneratedValue na entidade.
     */
    private int gerarNovoId() {
        long count = userRepository.count();
        return (int) count + 1;
    }

    /*
    * Método para autenticar usuário por email e senha
    * */
    public UsuarioGS autenticar(String email, String senha) {
        Optional<UsuarioGS> usuarioOpt = userRepository.findByEmail(email);

        if (usuarioOpt.isPresent()) {
            UsuarioGS usuario = usuarioOpt.get();
            if (usuario.getSenha().equals(senha)) {
                return usuario;
            }
        }
        return null; // Retorna null se não encontrar ou senha incorreta
    }
}
