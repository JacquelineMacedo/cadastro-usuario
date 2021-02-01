package com.backend.contausuario.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UsuarioDTO {

	private String nome;
	private String email;
	private Integer telefone;
}
