package curso.springboot.restfull.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import curso.springboot.restfull.model.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long>{

	@Query("SELECT u FROM Usuario u WHERE u.login = ?1")
	public Usuario findUsuarioByLogin(String login);
	
}
