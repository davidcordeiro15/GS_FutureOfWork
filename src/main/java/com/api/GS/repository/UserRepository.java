package com.api.GS.repository;

import com.api.GS.model.usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<usuarios, Integer> {

    /**
     * Busca um usuário pelo e-mail.
     * Retorna um Optional que pode conter o usuário encontrado.
     */
    Optional<usuarios> findByEmail(String email);
}
