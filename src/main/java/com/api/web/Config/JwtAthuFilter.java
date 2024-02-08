package com.api.web.Config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.api.web.Dao.UserDao;
import com.api.web.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAthuFilter extends OncePerRequestFilter{
	

		private final UserDao userDao;
		
		private final JwtUtil jwtUtils;
		
	@Override
	protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain) throws ServletException, IOException{
		final String authHeader = request.getHeader("AUTHORIZATION");
		// Variables para almacenar el nombre de usuario y el token JWT
		final String userEmail;
		final String jwtToken;
		
	 if(authHeader == null || !authHeader.startsWith("Bearer")) {
		 filterChain.doFilter(request, response);
		 return;
	 }
	// Extraer el token JWT eliminando el prefijo "Bearer"
	 jwtToken = authHeader.substring(7);
	 
	// Extraer el nombre de usuario del token utilizando el JwtUtil
	 userEmail = jwtUtils.extractUsername(jwtToken);
	
	 // Verificar si el nombre de usuario no es nulo y no hay autenticación previa configurada
	 if(userEmail != null && SecurityContextHolder.getContext().getAuthentication()==null) {
		
		 UserDetails userDetails = userDao.findUserByEmail(userEmail);
		
		 if(jwtUtils.isTokenValid(jwtToken, userDetails)) {
			 UsernamePasswordAuthenticationToken authToken = 
					 new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
			 authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			 SecurityContextHolder.getContext().setAuthentication(authToken);
			 
		 }
	 }
	 
	 filterChain.doFilter(request, response);
	}
	

}
