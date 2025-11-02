package com.gym.dto;

import lombok.Data;

@Data
public class ConfiguracionDTO {
    private Boolean notificacionesEmail;
    private Boolean notificacionesPush;
    private String tema;
    private String idioma;
    private Boolean twoFactorAuth;
}
