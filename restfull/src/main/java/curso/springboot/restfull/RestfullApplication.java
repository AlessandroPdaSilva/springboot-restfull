package curso.springboot.restfull;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
	/*
	 	@EntityScan(basePackages = {"curso.springboot.restfull.model"})
		@ComponentScan(basePackages = {"curso.*"})
		@EnableJpaRepositories(basePackages = {"curso.springboot.restfull.repository"})
		@EnableTransactionManagement
		@EnableWebMvc
		@RestController
		@EnableAutoConfiguration
	 */
public class RestfullApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(RestfullApplication.class, args);
	}
	
	
	// Exemplo de CrossOrigin global
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		
		/*
		 
			registry.addMapping("/usuario")
			.allowedMethods("POST","DELETE")
			.allowedOrigins("www.meusite.com.br");
		
		*/
	}
	
}
