package com.api.GS.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "USUARIOSGS")
public class UsuarioGS {
    @Id
    private int id;

    private String nome;
    private String email;
    private String senha;
    @Column(name = "esta_trabalhando")
    private boolean estaTrabalhando;
    private boolean eadmin;

    @ManyToMany
    @JoinTable(
            name = "usuarios_trilhas",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "trilha_id")
    )
    private List<Trilhas> trilhasMatriculadas;

}
