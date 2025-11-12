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
    public Optional<UsuarioGS> atualizarUsuario(int id, UserRequestDTO dto) {
        Optional<UsuarioGS> existente = userRepository.findById(id);

        if (existente.isEmpty()) {
            return Optional.empty();
        }

        UsuarioGS usuario = existente.get();
        if (dto.getNome() != null) usuario.setNome(dto.getNome());
        if (dto.getEmail() != null) usuario.setEmail(dto.getEmail());
        if (dto.getSenha() != null) usuario.setSenha(dto.getSenha());

        userRepository.save(usuario);
        return Optional.of(usuario);
    }

    /**
     * Exclui um usuário do banco de dados pelo ID.
     * Retorna true se o usuário foi deletado, false caso não exista.
     */
    public boolean deletarUsuario(int id) {
        Optional<UsuarioGS> existente = userRepository.findById(id);
        if (existente.isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
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
}
