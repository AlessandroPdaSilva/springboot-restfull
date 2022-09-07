package curso.springboot.restfull;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

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
public class RestfullApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestfullApplication.class, args);
	}

}
