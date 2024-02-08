package com.api.web.util;



import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	// Clave secreta utilizada para firmar los tokens JWT
	private String jwtSigningKey = "secret";
	
	// Extraer el nombre de usuario de un token
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	// Extraer la fecha de expiración de un token
	public Date extractExpiration(String token) {
		return extractClaim(token,Claims::getExpiration);
	}
	
	// Verificar si un token tiene una reclamación específica
	public boolean hasClaim(String token,String clainName) {
		final Claims claims = extractAllClaims(token);
		return claims.get(clainName) !=null;
	}
	
	// Extraer una reclamación específica de un token
	public <T> T extractClaim(String token,Function<Claims,T>claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	// Extraer todas las reclamaciones de un token
	private Claims extractAllClaims(String token) {
	    return Jwts.parser().setSigningKey(jwtSigningKey).parseClaimsJws(token).getBody();
	}

	// Verificar si un token ha expirado
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	// Generar un token JWT para un usuario
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims,userDetails);
	}
	
	 // Generar un token JWT para un usuario con reclamaciones adicionales
	public String generateToken(UserDetails userDetails,Map<String,Object> claims) {
		return createToken(claims, userDetails);
	}
	
	// Crear un token JWT con las reclamaciones y detalles del usuario proporcionados
	private String createToken(Map<String,Object>claims,UserDetails userDetails) {
		return Jwts.builder().setClaims(claims)
				.setSubject(userDetails.getUsername())
				.claim("authorities", userDetails.getAuthorities())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(2)))
						.signWith(SignatureAlgorithm.HS256,jwtSigningKey).compact();
	}
	
	 // Verificar la validez de un token comparándolo con los detalles del usuario
	public Boolean isTokenValid(String token , UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
