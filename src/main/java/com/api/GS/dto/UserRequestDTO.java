package com.api.GS.dto;

import jakarta.persistence.Entity;

import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class UserRequestDTO {
    private String email;
    private String senha;
    private String nome;
    private boolean estaTrabalhando;



}
