package curso.springboot.restfull.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import curso.springboot.restfull.model.Usuario;
import curso.springboot.restfull.repository.UsuarioRepository;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	// LISTAR USUARIOS
	@CrossOrigin(origins = "*")
	@GetMapping(value = "")
	@CacheEvict(value = "cachelistausurio", allEntries = true)// limpa cache nao usado
	@CachePut("cachelistausurio")// atualiza lista de cache
	public ResponseEntity listarUsuario(){
		
		List<Usuario> listaUsuario = (List<Usuario>) usuarioRepository.findAll();
		
		return new ResponseEntity<List<Usuario>>(listaUsuario,HttpStatus.OK);
	}
	
	
	// CONSULTAR USUARIO
	@GetMapping(value = "/{id}")
	public ResponseEntity consultarUsuario(@PathVariable(value = "id") Long id){
		
		Usuario u = usuarioRepository.findById(id).get();
		
		return new ResponseEntity<Usuario>(u,HttpStatus.OK);
	}
	
	// SALVAR USUARIO
	@CrossOrigin(origins = "localhost:8080")
    @PostMapping(value = "")
    public ResponseEntity<?> salvarUsuario(@RequestBody Usuario usuario){
    	
		//Criptografar senha
		String senhaCriptografada = new BCryptPasswordEncoder().encode(usuario.getSenha());
		usuario.setSenha(senhaCriptografada);
		
    	usuario = usuarioRepository.save(usuario);
    	
    	return new ResponseEntity<Usuario>(usuario, HttpStatus.CREATED);
    }
	
    // EDITAR USUARIO
    @PutMapping(value = "")
    public ResponseEntity<?> editarUsuario(@RequestBody Usuario usuario){
    	
    	
    	if(usuario.getId() == null) {
    		return new ResponseEntity<String>("Id do usuario não informado", HttpStatus.OK);
    	}
    	
		// se usuario atualizar senha
    	Usuario uAux = usuarioRepository.findUsuarioByLogin(usuario.getLogin());
    	if(!uAux.getSenha().equals(usuario.getSenha())) {
    		
    		//Criptografar senha
    		String senhaCriptografada = new BCryptPasswordEncoder().encode(usuario.getSenha());
    		usuario.setSenha(senhaCriptografada);
    		
    	}
    	
    	
    	usuario = usuarioRepository.save(usuario);
    	
    	return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    }
    
    // DELETAR USUARIO
 	@DeleteMapping(value = "/{id}")
 	public ResponseEntity<?> deletarUsuario(@PathVariable(value = "id") Long id){
 		
 		usuarioRepository.deleteById(id);
 		
 		return new ResponseEntity<String>("Deletado com sucesso",HttpStatus.OK);
 	}
    
    
    
    
}
