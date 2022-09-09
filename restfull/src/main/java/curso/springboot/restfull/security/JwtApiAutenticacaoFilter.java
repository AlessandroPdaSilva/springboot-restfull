package curso.springboot.restfull.security;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;


public class JwtApiAutenticacaoFilter extends GenericFilterBean {

	//Filtro para autenticação
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		
		Authentication authentication = new JWTTokenAutenticacaoService().getAuhentication((HttpServletRequest) request,(HttpServletResponse) response);
				
		/*Coloca o processo de autenticação no spring security*/
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		
		chain.doFilter(request, response);
		
	}

}
