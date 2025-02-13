package com.ezra.programandojuntos.auth.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ezra.programandojuntos.models.entity.Usuario;
import com.ezra.programandojuntos.models.services.IUsuarioService;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import static com.ezra.programandojuntos.auth.JwtConfig.*;
import static com.ezra.programandojuntos.constants.HttpStatusCode.*;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	private IUsuarioService usuarioService;
	
	private AuthenticationManager authenticationManager;
	
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		String username = null;
		String password = null;
		try {
			Usuario user = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
			username = user.getUsername();
			password = user.getPassword();
		} catch (StreamReadException e) {
			e.printStackTrace();
		} catch (DatabindException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		return this.authenticationManager.authenticate(authenticationToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		User user = (User) authResult.getPrincipal();
		String username = user.getUsername();		
		Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();
		
		//Usuario usuario = usuarioService.findByUsername(username);
		
	    boolean isAdmin = roles.stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
	     Claims claims = Jwts
	             .claims()
	             .add("authorities", new ObjectMapper().writeValueAsString(roles))
	             .add("username", username)
//	             .add("id", usuario.getId())
//	             .add("nombre", usuario.getNombre())
//	             .add("apellido", usuario.getApellido())
	             .add("isAdmin", isAdmin)

	             .build();

	     String jwt = Jwts.builder()
	             .subject(username)
	             .claims(claims)
	             .signWith(LLAVE_SECRETA)
	             .issuedAt(new Date())
	             .expiration(new Date(System.currentTimeMillis() + 3600000))
	             .compact();
		
		response.addHeader(AUTHORIZATION_HEADER, BEARER_PREFIX_TOKEN + jwt);
		
		Map<String, String> body = new HashMap<>();
		body.put("token", jwt);
		body.put("username", username);
		body.put("message", String.format("Hola %s has iniciado sesión con exito", username));
		body.put("token", jwt);
		//ObjectMapper convierte el body en un jason, y con write escribi enel cupero de la respuesta
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setContentType(APPLICATION_JSON_CONTENT_TYPE);
		response.setStatus(OK_200);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		Map<String, String> body = new HashMap<>();
		body.put("message", "Error en la autenticación");
		body.put("error", failed.getMessage());
		//ObjectMapper convierte el body en un jason, y con write escribi enel cupero de la respuesta
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setContentType(APPLICATION_JSON_CONTENT_TYPE);
		response.setStatus(UNAUTHORIZED_401);
	}
	
	
	


}
