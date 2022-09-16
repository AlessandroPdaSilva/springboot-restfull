package curso.springboot.restfull.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import curso.springboot.restfull.model.Telefone;
import curso.springboot.restfull.model.Usuario;

@Repository
@Transactional
public interface TelefoneRepository extends CrudRepository<Telefone, Long>{
	@Query("SELECT t FROM Telefone t WHERE t.usuario.id = ?1")
	public List<Telefone> pesquisarTelefoneByidUsuario(Long idUsuario);
}
