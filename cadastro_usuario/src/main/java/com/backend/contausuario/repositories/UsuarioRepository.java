package com.backend.contausuario.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.contausuario.domain.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Usuario findByEmail(String email);
}
