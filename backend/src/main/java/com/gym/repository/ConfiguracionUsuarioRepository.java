package com.gym.repository;

import com.gym.entity.ConfiguracionUsuario;
import com.gym.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfiguracionUsuarioRepository extends JpaRepository<ConfiguracionUsuario, Long> {
    Optional<ConfiguracionUsuario> findByUsuario(Usuario usuario);
    Optional<ConfiguracionUsuario> findByUsuarioUsername(String username);
}
