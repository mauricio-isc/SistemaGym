package com.gym.service;

import com.gym.dto.ConfiguracionDTO;
import com.gym.entity.ConfiguracionUsuario;
import com.gym.entity.Usuario;
import com.gym.repository.ConfiguracionSistemaRepository;
import com.gym.repository.ConfiguracionUsuarioRepository;
import com.gym.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfiguracionService {

    private final ConfiguracionUsuarioRepository configuracionUsuarioRepository;
    private final UsuarioRepository usuarioRepository;

    public ConfiguracionDTO obtenerConfiguracion(String username) {
        ConfiguracionUsuario configuracion = configuracionUsuarioRepository
                .findByUsuarioUsername(username)
                .orElseGet(() -> crearConfiguracionPorDefecto(username));

        return mapToDTO(configuracion);
    }

    @Transactional
    public ConfiguracionDTO actualizarConfiguracion(String username, ConfiguracionDTO configuracionDTO) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        ConfiguracionUsuario configuracion = configuracionUsuarioRepository
                .findByUsuario(usuario)
                .orElseGet(() -> {
                    ConfiguracionUsuario nuevaConfig = ConfiguracionUsuario.builder()
                            .usuario(usuario)
                            .build();
                    return nuevaConfig;
                });

        // Actualizar configuración
        configuracion.setNotificacionesEmail(configuracionDTO.getNotificacionesEmail());
        configuracion.setTema(configuracionDTO.getTema());
        configuracion.setIdioma(configuracionDTO.getIdioma());
        configuracion.setTwoFactorAuth(configuracionDTO.getTwoFactorAuth());

        ConfiguracionUsuario configuracionGuardada = configuracionUsuarioRepository.save(configuracion);
        return mapToDTO(configuracionGuardada);
    }

    private ConfiguracionUsuario crearConfiguracionPorDefecto(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        ConfiguracionUsuario configuracion = ConfiguracionUsuario.builder()
                .usuario(usuario)
                .build();

        return configuracionUsuarioRepository.save(configuracion);
    }

    private ConfiguracionDTO mapToDTO(ConfiguracionUsuario configuracion) {
        ConfiguracionDTO dto = new ConfiguracionDTO();
        dto.setNotificacionesEmail(configuracion.getNotificacionesEmail());
        dto.setTema(configuracion.getTema());
        dto.setIdioma(configuracion.getIdioma());
        dto.setTwoFactorAuth(configuracion.getTwoFactorAuth());
        return dto;
    }
}
