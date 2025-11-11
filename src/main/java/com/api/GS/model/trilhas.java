package com.api.GS.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Base64;

@Entity
@Getter
@Setter
public class trilhas {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private String nome;
    private String conteudo;
    private String videos;
    private int quantidadeDeMatriculados;
}
