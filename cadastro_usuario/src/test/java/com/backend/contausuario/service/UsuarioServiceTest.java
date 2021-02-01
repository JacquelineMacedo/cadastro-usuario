package com.backend.contausuario.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.backend.contausuario.domain.Usuario;
import com.backend.contausuario.repositories.UsuarioRepository;
import com.backend.contausuario.util.TokenUtils;

import junit.framework.Assert;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioServiceTest {

	@InjectMocks
	UsuarioService UsuarioService;

	@Mock
	private UsuarioRepository repository;

	@Mock
	private TokenUtils tokenUtils;

	@Test
	public void deveCriarUsuarioQuandoUsuarioNaoExistente() {
		Usuario usuario = new Usuario();
		usuario.setNome("Joao");
		usuario.setEmail("joao@gmail.com");
		usuario.setTelefone(985214572);

	}

	@Test
	public void deveBuscarUsuarioPorEmailQuandoUsuarioJaExistente() {
		Usuario usuario = new Usuario();
		usuario.setEmail("joao@gmail.com");

	}

	@Test
	public void deveBuscarUsuarioPorNomeQuandoUsuarioJaExistente() {
		Usuario usuario = new Usuario();
		usuario.setNome("João");

	}

	@Test
	public void DeveListarUsuariosQuandoUsuariosExistentes() {
		Usuario retornoEsperado = new Usuario();
		retornoEsperado.setNome("Joao");
		retornoEsperado.setEmail("joao@gmail.com");
		retornoEsperado.setTelefone(985214572);
		List<Usuario> retornoObtido = new ArrayList<Usuario>();
		retornoObtido.add(retornoEsperado);

		Assert.assertEquals(retornoEsperado, retornoObtido);
	}

	@Test
	public void deveGerarTokenApartirDoEmailQuandoUsuarioCriado() {

	}
}
