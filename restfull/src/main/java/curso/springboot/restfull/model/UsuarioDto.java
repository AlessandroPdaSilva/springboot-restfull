package curso.springboot.restfull.model;

import java.util.Date;
import java.util.List;

public class UsuarioDto {

	private Long id;
	private String login;
	private String nome;
	
	List<Telefone> listaTelefone;
	private Date dataNascimento;
	
	//CONSTRUTOR
	public UsuarioDto(Usuario usuario) {
		this.id = usuario.getId();
		this.login = usuario.getLogin();
		this.nome = usuario.getNome();
		this.listaTelefone = usuario.getListaTelefone();
		this.dataNascimento = usuario.getDataNascimento();
	}
	
	// GET E SET
	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<Telefone> getListaTelefone() {
		return listaTelefone;
	}
	public void setListaTelefone(List<Telefone> listaTelefone) {
		this.listaTelefone = listaTelefone;
	}
	
	
	
	
}
