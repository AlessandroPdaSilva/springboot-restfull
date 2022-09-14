package curso.springboot.restfull.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import curso.springboot.restfull.model.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long>{

	@Query("SELECT u FROM Usuario u WHERE u.login = ?1")
	public Usuario findUsuarioByLogin(String login);
	
	@Query("SELECT u FROM Usuario u WHERE u.nome LIKE %?1%")
	public List<Usuario> findUsuarioByNome(String nome);
	
	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "UPDATE usuario SET token = ?1 WHERE login = ?2")
	public void atualizaToken(String login,String token);

	
	
}
