package com.backend.contausuario.service;

import org.springframework.security.core.context.SecurityContextHolder;

import com.backend.contausuario.service.security.UserSS;

public class UserService {

	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}
}
