package curso.springboot.restfull.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;



@Entity
public class Usuario implements UserDetails{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String login;
	private String senha;
	private String nome;
	
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuario", fetch = FetchType.LAZY)
	List<Telefone> listaTelefone;
	
	@OneToMany(fetch = FetchType.EAGER)	
	@JoinTable(name = "usuarios_role", // Nome da tabela
    	joinColumns = @JoinColumn(name = "usuario_id", // nome da coluna
                  					referencedColumnName = "id",// foreinkey
                  					table = "usuario"),
		
		inverseJoinColumns = @JoinColumn(name="role_id", // nome da coluna
										referencedColumnName = "id",// foreinkey
										table = "role"))// "JoinTable cria tabela no banco"
	private List<Role> roles;
	
	
	
	// GET E SET
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
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
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
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
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(id, other.id);
	}
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", login=" + login + ", senha=" + senha + ", nome=" + nome + "]";
	}
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}
	@Override
	public String getPassword() {
		return this.senha;
	}
	@Override
	public String getUsername() {
		return this.login;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	
	
}
