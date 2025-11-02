package com.gym.dto;

import lombok.Data;

@Data
public class CambioPasswordDTO {
    private String passwordActual;
    private String nuevoPassword;
    private String confirmarPassword;
}
