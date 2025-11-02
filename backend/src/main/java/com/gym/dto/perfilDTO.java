package com.gym.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class perfilDTO {
    private Long id;
    private String username;
    private String nombre;
    private String apellido;
    private String email;
    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimoAcceso;
    private Boolean activo;
    private Boolean bloqueado;
    private Integer intentosFallidos;

    public String getNombreCompleto(){
        return nombre + " " + apellido;
    }
}
