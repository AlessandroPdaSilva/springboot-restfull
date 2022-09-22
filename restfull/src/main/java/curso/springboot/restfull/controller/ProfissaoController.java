package curso.springboot.restfull.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import curso.springboot.restfull.model.Profissao;
import curso.springboot.restfull.repository.ProfissaoRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/profissao")
public class ProfissaoController {
	
	@Autowired
	private ProfissaoRepository profissaoRepository;
	
	// LISTAR PROFISSAO
	@GetMapping(value = "")
	@CacheEvict(value = "cachelistaprofissao", allEntries = true)// limpa cache nao usado
	@CachePut("cachelistaprofissao")// atualiza lista de cache
	public ResponseEntity listarProfissao(){
		
		
		List<Profissao> listaProfissao = profissaoRepository.findAll();
		
		return new ResponseEntity<List<Profissao>>(listaProfissao,HttpStatus.OK);
	}

}
