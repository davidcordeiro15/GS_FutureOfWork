package com.api.GS.dto;

import jakarta.persistence.Entity;

import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class UserRequestDTO {
    private String nome;
    private String email;
    private String senha;
    private boolean estaTrabalhando;
    private boolean eadmin;



}
