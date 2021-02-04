package com.backend.contausuario.service;

import com.backend.contausuario.domain.Usuario;
import com.backend.contausuario.dto.UsuarioDTO;

public interface IUsuarioService {
	String insert(UsuarioDTO dto);

	Usuario findById(Long id);

	Usuario findByToken(String token);
	
	Usuario update(Long id);
}
