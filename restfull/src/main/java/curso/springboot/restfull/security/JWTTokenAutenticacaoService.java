package curso.springboot.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.springboot.restfull.model.Usuario;
import curso.springboot.restfull.repository.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTTokenAutenticacaoService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	//validade do Token (Ex: millisegundos=172800000 ou 2 dias)
	private static final long EXPIRATION_TIME = 172800000;
	
	//Uma senha unica para compor a autenticacao e ajudar na segurança
	private static final String SECRET = "SenhaExtremamenteSecreta";
	
	//Prefixo padrão de Token
	private static final String TOKEN_PREFIX = "Bearer";
	private static final String HEADER_STRING = "Authorization";
	
	
	
	// GERANDO TOKEN
	public void addAuthentication(HttpServletResponse response , String username) throws IOException {
		
		//Montagem do Token
		String JWT = Jwts.builder()
						//Adiciona o usuario
				        .setSubject(username) 
				        
				        //Validade do Token
				        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) 
				        
				        //algoritimo de geração de senha + senha
				        .signWith(SignatureAlgorithm.HS512, SECRET).compact(); 
	
		
		//prefixo + JWT = token
		String token = TOKEN_PREFIX + " " + JWT; // (Ex: Bearer sdwitfvicnfsnwafwkjfijsefsd)
		
		//Adiciona no cabeçalho http
		response.addHeader(HEADER_STRING, token); //(Authorization: Bearer 87878we8we787w8e78w78e78w7e87w)
		
		//Adiciona no corpo http
		response.getWriter().write("{\"Authorization\": \""+token+"\"}");//(Authorization: Bearer 87878we8we787w8e78w78e78w7e87w)
		
		
	}
	
	
	
	// VALIDA TOKEN
	public Authentication getAuhentication(HttpServletRequest request) {
		
		
		String token = request.getHeader(HEADER_STRING);
		
		
		if (token != null) {
			
			// Tira prefixo
			String tokenLimpo = token.replace(TOKEN_PREFIX, "").trim();
			
			//Validação do token
			String username = Jwts.parser().setSigningKey(SECRET) 
								.parseClaimsJws(tokenLimpo) // (Ex: 87878we8we787w8e78w78e78w7e87w)
								.getBody().getSubject(); // String username = (Ex: João Silva ou joao@gmail.com)
			
			
			if (username != null) {
				
				Usuario usuario = usuarioRepository.findUsuarioByLogin(username);
				
				//Usuario usuario = ApplicationContextLoad.getApplicationContext().getBean(UsuarioRepository.class).findUserByLogin(user);
				
				if (usuario != null) {
					 
					return new UsernamePasswordAuthenticationToken(
							usuario.getLogin(), 
							usuario.getSenha(),
							usuario.getAuthorities());
				   
					
				}
			}
			
		}
	
		return null; /*Não autorizado*/
		
	}
	
	
	
	
	
}
