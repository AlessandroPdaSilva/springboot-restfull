package curso.springboot.restfull.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import org.springframework.web.servlet.ModelAndView;

import curso.springboot.restfull.model.Telefone;
import curso.springboot.restfull.model.Usuario;
import curso.springboot.restfull.model.UsuarioDto;
import curso.springboot.restfull.model.UsuarioRelatorio;
import curso.springboot.restfull.repository.TelefoneRepository;
import curso.springboot.restfull.repository.UsuarioRepository;
import curso.springboot.restfull.service.RelatorioService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private TelefoneRepository telefoneRepository;
	
	@Autowired
	private RelatorioService relatorioService;

	// LISTAR USUARIOS
	@GetMapping(value = "")
	@CacheEvict(value = "cachelistausurio", allEntries = true)// limpa cache nao usado
	@CachePut("cachelistausurio")// atualiza lista de cache
	public ResponseEntity listarUsuario(){
		
		PageRequest page = PageRequest.of(0, 5, Sort.by("nome"));
		
		Page<Usuario> listaUsuario = usuarioRepository.findAll(page);
		
		return new ResponseEntity<Page<Usuario>>(listaUsuario,HttpStatus.OK);
	}
	
	
	
	// LISTAR USUARIOS PAGINACAO
	@GetMapping(value = "page/{pagina}")
	@CacheEvict(value = "cachelistausurio", allEntries = true)// limpa cache nao usado
	@CachePut("cachelistausurio")// atualiza lista de cache
	public ResponseEntity listarUsuarioPaginacao(@PathVariable(value = "pagina") int pagina){
		
		PageRequest page = PageRequest.of(pagina, 5, Sort.by("nome"));
		
		Page<Usuario> listaUsuario = usuarioRepository.findAll(page);
		
		return new ResponseEntity<Page<Usuario>>(listaUsuario,HttpStatus.OK);
	}
		
	
	
	// CONSULTAR USUARIO
	@GetMapping(value = "/{id}")
	public ResponseEntity consultarUsuario(@PathVariable(value = "id") Long id){
		
		Usuario u = usuarioRepository.findById(id).get();
		
		// usando DTO
    	UsuarioDto usuDto = new UsuarioDto(u);
		
		return new ResponseEntity<UsuarioDto>(usuDto,HttpStatus.OK);
	}
	
	
	// CONSULTAR USUARIO BY NOME
	@GetMapping(value = "/consultaByNome/{nome}")
	public ResponseEntity consultarUsuarioByNome(@PathVariable(value = "nome") String nome){
		
		List<Usuario> listaUsuario = usuarioRepository.findUsuarioByNome(nome);
		
		// usando DTO
		List<UsuarioDto> listaUsuarioDto = new ArrayList<>();
		
		for(Usuario aux: listaUsuario) {
			listaUsuarioDto.add(new UsuarioDto(aux));
		}
		
		
		return new ResponseEntity<List<UsuarioDto>>(listaUsuarioDto,HttpStatus.OK);
	}
	
	
	// SALVAR USUARIO
    @PostMapping(value = "")
    public ResponseEntity<?> salvarUsuario(@RequestBody Usuario usuario){
    	
    	// se usuario atualizar senha
    	if(usuario.getSenha() != null) {
    		
    		//Criptografar senha
    		String senhaCriptografada = new BCryptPasswordEncoder().encode(usuario.getSenha());
    		usuario.setSenha(senhaCriptografada);
    		
    	}else {
    		usuario.setSenha("");
    	}
		
    	
    		
    	
    	
		List<Telefone> telAux = usuario.getListaTelefone();
		
		// Salvando Usuario
    	usuario = usuarioRepository.save(usuario);
		
    	
    	if(usuario.getListaTelefone() != null) {
			
    		// Adicionando Telefone
	    	for(Telefone fone: telAux) {
	    		fone.setUsuario(usuario);
	    	}
	    	telefoneRepository.saveAll(usuario.getListaTelefone());
    	
    	}
    	
    	// usando DTO
    	UsuarioDto usuDto = new UsuarioDto(usuario);
    	
    	return new ResponseEntity<UsuarioDto>(usuDto, HttpStatus.CREATED);
    }
	
    // EDITAR USUARIO
    @PutMapping(value = "")
    public ResponseEntity<?> editarUsuario(@RequestBody Usuario usuario){
    	
    	
    	if(usuario.getId() == null) {
    		return new ResponseEntity<String>("Id do usuario não informado", HttpStatus.OK);
    	}
    	
		// se usuario atualizar senha
    	Usuario uAux = usuarioRepository.findById(usuario.getId()).get();
    	if(usuario.getSenha() != null) {
    		
    		//Criptografar senha
    		String senhaCriptografada = new BCryptPasswordEncoder().encode(usuario.getSenha());
    		usuario.setSenha(senhaCriptografada);
    		
    	}else {
    		usuario.setSenha(uAux.getSenha());
    	}
    	
    	
    	// Adicionando Telefone
    	for(Telefone fone: usuario.getListaTelefone()) {
    		fone.setUsuario(usuario);
    	}
    	telefoneRepository.saveAll(usuario.getListaTelefone());
    	
    	// Salvando Usuario
    	usuario = usuarioRepository.save(usuario);
    	
    	
    	// usando DTO
    	UsuarioDto usuDto = new UsuarioDto(usuario);
    	
    	return new ResponseEntity<UsuarioDto>(usuDto, HttpStatus.OK);
    }
    
    // DELETAR USUARIO
 	@DeleteMapping(value = "/{id}")
 	public ResponseEntity<?> deletarUsuario(@PathVariable(value = "id") Long id){
 		
 		usuarioRepository.deleteById(id);
 		
 		return new ResponseEntity<String>("Deletado com sucesso",HttpStatus.OK);
 	}
    
    
 	// DELETAR TELEFONE
 	@DeleteMapping(value = "/telefone/{idtelefone}")
	public ResponseEntity<?> excluirTelefone(@PathVariable("idtelefone") Long idTelefone){
		
		// deletar telefone
		telefoneRepository.deleteById(idTelefone);
		 
		return new ResponseEntity<String>("ok",HttpStatus.OK);
	}
 	
 	
 	// DOWNLOAD RELATORIO
 	@GetMapping(value = "/relatorio", produces = "application/text")
 	public ResponseEntity<String> downloadRelatorio(HttpServletRequest request) throws Exception{
 		
 		byte[] pdf = relatorioService.gerarRelatorio("relatorio-usuario", new HashMap(), request.getServletContext());
 		
 		String base64Pdf = "data:application/pdf;base64,"+ Base64.encodeBase64String(pdf);
 		
 		return new ResponseEntity<String>(base64Pdf,HttpStatus.OK);
 	}
 	
 	
 	// DOWNLOAD RELATORIO PARAM
  	@PostMapping(value = "/relatorio/", produces = "application/text")
  	public ResponseEntity<String> downloadRelatorioParam(HttpServletRequest request, @RequestBody UsuarioRelatorio usuarioRelatorio) throws Exception{
  		
  		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
  		SimpleDateFormat formatDatabase = new SimpleDateFormat("yyyy-MM-dd");
  		
  		String dataInicio = formatDatabase.format(format.parse(usuarioRelatorio.getDataInicial()));
  		String dataFinal = formatDatabase.format(format.parse(usuarioRelatorio.getDataFinal()));
  		
  		HashMap<String,Object> params = new HashMap<String, Object>();
  		
  		params.put("DATA_INICIO", dataInicio);
  		params.put("DATA_FIM", dataFinal);
  		
  		byte[] pdf = relatorioService.gerarRelatorio("relatorio-usuario-param", params, request.getServletContext());
  		
  		String base64Pdf = "data:application/pdf;base64,"+ Base64.encodeBase64String(pdf);
  		
  		return new ResponseEntity<String>(base64Pdf,HttpStatus.OK);
  	}
 	
 	
 	/***
 	 COMO CONSUMIR API EXTERNA PELO JAVA
 	 
	 	 URL url = new URL("https://viacep.com.br/ws/"+ usuario.getCep() + "/json/");
	 	 URLConnection connection = url.openConnection();
	 	 InputStream is = connection.getInputStream;
	 	 BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
	 	 
	 	 String cep = "";
	 	 StringBuilder jsonCep =new StringBuilder();
	 	 
	 	 While((cep = br.readLine) != null){
	 	 	jsonCep.append(cep);
	 	 }
	 	 
	 	 System.out.println(jsonCep.toString());
	 	 
	 	 Usuario uAux = new Gson().fromJson(jsonCep.toString(), Usuario.class);
	 	 
	 	 usuario.setCep(uAux.getCep());
	 	 usuario.setLogradouro(uAux.getLogradouro());
	 	 usuario.setBairro(uAux.getBairro());
	 	 ...
	 	 
 	 
 	 */
    
    
}
