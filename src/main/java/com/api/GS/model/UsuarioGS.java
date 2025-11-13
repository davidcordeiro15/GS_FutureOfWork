package com.api.GS.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "TRILHASGS")
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
    private boolean admin;

    @ManyToMany
    @JoinTable(
            name = "usuarios_trilhas",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_trilha")
    )
    private List<Trilhas> trilhasMatriculadas;

}
