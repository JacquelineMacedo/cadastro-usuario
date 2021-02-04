package com.backend.contausuario.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.contausuario.domain.Usuario;
import com.backend.contausuario.dto.UsuarioDTO;
import com.backend.contausuario.enums.Perfil;
import com.backend.contausuario.repositories.UsuarioRepository;
import com.backend.contausuario.service.exceptions.AuthorizationException;
import com.backend.contausuario.service.exceptions.ObjectNotFoundException;
import com.backend.contausuario.service.security.UserSS;
import com.backend.contausuario.util.TokenUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UsuarioService implements IUsuarioService {

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private UsuarioRepository repository;
	
	public Usuario find(Long id) {
		UserSS user = UserService.authenticated();
		if (user==null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso Negado");
	}

		Optional<Usuario> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Usuario.class.getName()));
	}

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
	
	public Usuario update(Long id) {
		Usuario usuario = repository.getOne(id);
		usuario = repository.save(usuario);
		return usuario;
	}
}