package com.backend.contausuario.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.contausuario.domain.Usuario;
import com.backend.contausuario.dto.UsuarioDTO;
import com.backend.contausuario.repositories.UsuarioRepository;
import com.backend.contausuario.util.TokenUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UsuarioService implements IUsuarioService {

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private UsuarioRepository repository;

	@Override
	public String insert(UsuarioDTO dto) {
		Usuario usuario = repository.findByEmail(dto.getEmail());

		if (Objects.isNull(usuario)) {
			usuario = Usuario.builder().id(null) //
					.nome(dto.getNome()) //
					.telefone(dto.getTelefone()) //
					.email(dto.getEmail()) //
					.build();

			repository.save(usuario);
		}

		return tokenUtils.gerarToken(usuario.getEmail());
	}

	@Override
	public Usuario findById(Long id) {
		Usuario usuario = repository.findById(id).get();
		return usuario;
	}

	@Override
	public Usuario findByToken(String token) {
		String email = tokenUtils.obterEmailPeloToken(token.substring(7));
		Usuario usuario = repository.findByEmail(email);
		return usuario;
	}

}