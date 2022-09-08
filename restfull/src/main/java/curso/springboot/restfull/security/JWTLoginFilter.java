package curso.springboot.restfull.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import curso.springboot.restfull.model.Usuario;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

	//GERENCIADOR DE AUTENTICACAO
	protected JWTLoginFilter(String url, AuthenticationManager authenticationManager) {
       
		/*Obriga a autenticar a URL*/
		super(new AntPathRequestMatcher(url));
       
       /*Gerenciador de autenticacao*/
       setAuthenticationManager(authenticationManager);
		
	}

	/*RETORNA USUARIO*/
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		
		/*Está pegando o token para validar*/
		Usuario u = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
		
		/*Retorna o usuario login, senha e acessos*/
		return getAuthenticationManager().
				authenticate(new UsernamePasswordAuthenticationToken(u.getLogin(), u.getSenha()));
	}
	
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		new JWTTokenAutenticacaoService().addAuthentication(response, authResult.getName());
	
	}

	

}