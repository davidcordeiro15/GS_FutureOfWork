package com.api.GS.dto;



import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrilhaResponseDTO {
    private String mensagem;
    private Object dados;
}

