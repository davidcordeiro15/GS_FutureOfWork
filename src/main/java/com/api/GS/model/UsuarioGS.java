package com.api.GS.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "usuariosGS")
public class UsuarioGS {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuarios_seq")
    @SequenceGenerator(name = "usuarios_seq", sequenceName = "USUARIOS_SEQ", allocationSize = 1)
    private int id;

    private String email;
    private String senha;
    private String nome;
    @Column(name = "esta_trabalhando")
    private boolean estaTrabalhando;

}
