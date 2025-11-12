package com.api.GS.repository;

import com.api.GS.model.UsuarioGS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UsuarioGS, Integer> {

    /**
     * Busca um usuário pelo e-mail.
     * Retorna um Optional que pode conter o usuário encontrado.
     */
    Optional<UsuarioGS> findByEmail(String email);
}
