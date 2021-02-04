package com.backend.contausuario.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

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
	
	@NotEmpty(message = "Prenchimento obrigatório")
	@Email(message = "Email invalido")
	private String email;
	
	private Integer telefone;
}
