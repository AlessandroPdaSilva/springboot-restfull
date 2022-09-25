package curso.springboot.restfull.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private ImplementacaoUserDetailsService userDetailsService;
	
	
	// CONFIGURAÇÃO
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		
		http.csrf()
		
		// Ativando validacao por token
		.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) 
		
		// Qualquer usuário acessa a pagina inicial
		.disable().authorizeRequests().antMatchers("/").permitAll()
		.antMatchers("/index", "/recuperaconta/**").permitAll()
		
		//.antMatchers(HttpMethod.GET, "/usuario").hasAnyRole("ADMIN")
		.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
		
		// Redireciona apos ser deslogado
		.anyRequest().authenticated().and().logout().logoutSuccessUrl("/index") 
		
		// Mapeia url de logout e invalida usuario
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		
		//Filtro no login para autenticação JWT
		.and().addFilterBefore(new JWTLoginFilter("/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
		
		//Filtro para qualquer requisicao JWT
		.addFilterBefore(new JwtApiAutenticacaoFilter(), UsernamePasswordAuthenticationFilter.class);
		
		
	}
	
	
	// AUTENTICAÇÃO DO USUARIO
	@Override 
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		// autenticação com service
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(new BCryptPasswordEncoder());
		
		
		/**
		 autencicação em memoria
		
				auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
				.withUser("adm")
				.password("$2a$10$Xnjw09p8DffXlccIuqEV.OcmmdBGd71QrqmtLrvTAqiZIqa6rtky6")
				.roles("ADMIN");
		*/
	}
	
	
	
	// IGNORA URLs ESPECIFICAS
	@Override 
	public void configure(WebSecurity web) throws Exception {
		//web.ignoring().antMatchers("/materialize/**");
	}
	
	
	
}
