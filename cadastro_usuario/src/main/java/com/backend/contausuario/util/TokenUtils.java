package com.backend.contausuario.util;

import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenUtils {
    private static final Integer VALIDADE_TOKEN_10_MIN = 10 * 60 * 1000;

    @Value("${app.chave}")
    private String chave;

    private Claims getValueFromToken(String token) {
        return Jwts.parser().setSigningKey(chave).parseClaimsJws(token).getBody();
    }

    public <T> T obterValorPeloToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getValueFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Date obterDataExpiracaoPeloToken(String token) {
        return obterValorPeloToken(token, Claims::getExpiration);
    }

    public String obterEmailPeloToken(String token) {
        return obterValorPeloToken(token, Claims::getSubject);
    }

    private Boolean isTokenExpirado(String token) {
        final Date expiration = obterDataExpiracaoPeloToken(token);
        return expiration.before(new Date());
    }

    public String gerarToken(String subject) {
        return Jwts.builder().setClaims(new HashMap<>()).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + VALIDADE_TOKEN_10_MIN))
                .signWith(SignatureAlgorithm.HS512, chave)
                .compact();
    }

    public Boolean validarToken(String token, UserDetails userDetails) {
        final String username = obterEmailPeloToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpirado(token));
    }
}