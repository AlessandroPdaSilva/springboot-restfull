package curso.springboot.restfull.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import curso.springboot.restfull.ObjErro;
import curso.springboot.restfull.model.Usuario;
import curso.springboot.restfull.repository.UsuarioRepository;

@RestController
@RequestMapping(value = "/recuperaconta")
public class RecuperaContaController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@PostMapping(value = "")
    public ResponseEntity<?> recuperarUsuario(@RequestBody Usuario usuario){
		
		ObjErro status = new ObjErro();
		
		Usuario u = usuarioRepository.findUsuarioByLogin(usuario.getLogin());
		
		if(u == null) {
			status.setCode("404");
			status.setError("Usuario nao encontrado");
		}else {
			status.setCode("200");
			status.setError("Acesso enviado para o seu email");
		}
		
		return new ResponseEntity<ObjErro>(status, HttpStatus.OK);
	}
	
	
}
