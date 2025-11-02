package com.gym.service;

import com.gym.dto.CambioPasswordDTO;
import com.gym.dto.perfilDTO;
import com.gym.entity.Usuario;
import com.gym.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PerfilService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public perfilDTO obtenerPerfil(String username){
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return mapToPerfilDTO(usuario);
    }

    @Transactional
    public perfilDTO actualizarPerfil(String username, perfilDTO perfilDTO){
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setNombre(perfilDTO.getNombre());
        usuario.setApellido(perfilDTO.getApellido());
        usuario.setEmail(perfilDTO.getEmail());

        Usuario usuarioActualizado = usuarioRepository.save(usuario);
        return mapToPerfilDTO(usuarioActualizado);
    }

    @Transactional
    public void cambiarPassword(String username, CambioPasswordDTO cambioPasswordDTO){
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(cambioPasswordDTO.getPasswordActual(), usuario.getPassword())){
            throw new RuntimeException("La contraseña actual es incorrecta");
        }

        if (!cambioPasswordDTO.getNuevoPassword().equals(cambioPasswordDTO.getConfirmarPassword())){
            throw new RuntimeException("Las nuevas contraseñas no coinciden");
        }

        usuario.setPassword(passwordEncoder.encode(cambioPasswordDTO.getNuevoPassword()));
        usuarioRepository.save(usuario);
    }

    private perfilDTO mapToPerfilDTO(Usuario usuario) {
        perfilDTO dto = new perfilDTO();
        dto.setId(usuario.getId());
        dto.setUsername(usuario.getUsername());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setEmail(usuario.getEmail());
        dto.setFechaCreacion(usuario.getFechaCreacion());
        dto.setUltimoAcceso(usuario.getUltimoAcceso());
        dto.setActivo(usuario.isActivo());
        dto.setBloqueado(usuario.getBloqueado());
        dto.setIntentosFallidos(usuario.getIntentosFallidos());
        return dto;
    }
}
