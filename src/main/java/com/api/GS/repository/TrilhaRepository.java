package com.api.GS.repository;


import com.api.GS.model.Trilhas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrilhaRepository extends JpaRepository<Trilhas, Integer> {
}

