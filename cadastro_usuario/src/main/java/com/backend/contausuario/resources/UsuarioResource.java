package com.backend.contausuario.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.backend.contausuario.domain.Usuario;
import com.backend.contausuario.dto.UsuarioDTO;
import com.backend.contausuario.service.IUsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/usuarios")
@RequiredArgsConstructor
public class UsuarioResource {

	@Autowired
	private IUsuarioService usuarioService;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UsuarioDTO> insert(@RequestBody UsuarioDTO dto) {

		String token = usuarioService.insert(dto);

		URI uri = ServletUriComponentsBuilder //
				.fromCurrentRequest() //
				.path("/me") //
				.buildAndExpand() //
				.toUri();

		return ResponseEntity //
				.created(uri) //
				.header("Authorization", "bearer " + token) //
				.build();
	}

	@GetMapping("/me")
	public ResponseEntity<Usuario> findByToken(@RequestHeader("authorization") String token) {
		try {
			Usuario usuario = usuarioService.findByToken(token);
			return ResponseEntity.ok(usuario);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("error", e.getMessage()).build();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Usuario> setDelivered(@PathVariable Long id) {
		Usuario usuario = usuarioService.update(id);
		return ResponseEntity.ok().body(usuario);
	}
}
