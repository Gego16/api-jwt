package com.api.web.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.web.Dao.UserDao;
import com.api.web.Entity.AuthenticationRequests;
import com.api.web.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuarios/token")
@RequiredArgsConstructor
public class AutheticationController {
	
	private final AuthenticationManager authenticationManager;
	
	private final UserDao userDao;
	
	private final JwtUtil jwtUtil;
	
	@PostMapping("/autenticacion")
	public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequests request){
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
				);
		final UserDetails user = userDao.findUserByEmail(request.getEmail());
		if(user != null) {
			System.out.println(jwtUtil.generateToken(user));
			return ResponseEntity.ok(jwtUtil.generateToken(user));
			
		}
		return ResponseEntity.status(400).body("Ocuarrio algun error"); 
	}
	
	@GetMapping("/validar")
    public ResponseEntity<Boolean> validarToken(@RequestBody String token) {
        boolean isTokenValid = jwtUtil.isTokenValid(token, null);
        return ResponseEntity.ok(isTokenValid);
    }

}
