package curso.springboot.restfull;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
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
