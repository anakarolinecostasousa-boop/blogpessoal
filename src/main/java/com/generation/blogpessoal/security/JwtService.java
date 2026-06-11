package com.generation.blogpessoal.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private static final String SECRET_KEY = "blogpessoalblogpessoalblogpessoalblogpessoal";

	private Key getSignKey() {
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	}

	public String generateToken(String userName) {

		return Jwts.builder().subject(userName).issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)).signWith(getSignKey()).compact();
	}

	public boolean validateToken(String token, String userName) {

		final String username = extractUsername(token);

		return (username.equals(userName) && !isTokenExpired(token));
	}

	public String extractUsername(String token) {

		return extractAllClaims(token).getSubject();
	}

	private boolean isTokenExpired(String token) {

		return extractAllClaims(token).getExpiration().before(new Date());
	}

	private Claims extractAllClaims(String token) {

		return Jwts.parser().verifyWith((javax.crypto.SecretKey) getSignKey()).build().parseSignedClaims(token)
				.getPayload();
	}
}