package com.api.GS.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "trilhas")
public class Trilhas {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trilhas_seq")
    @SequenceGenerator(name = "trilhas_seq", sequenceName = "TRILHAS_SEQ", allocationSize = 1)
    private int id;

    private String nome;
    @Column(columnDefinition = "CLOB")
    private String conteudo;

    @Column(columnDefinition = "CLOB")
    private String videos;

    @Column(name = "quantidade_de_matriculados")
    private int quantidadeDeMatriculados;
}
