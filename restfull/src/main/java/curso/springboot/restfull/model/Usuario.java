package curso.springboot.restfull.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
public class Usuario implements UserDetails{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(unique = true)
	private String login;
	
	private String senha;
	private String nome;
	
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuario", fetch = FetchType.LAZY)
	List<Telefone> listaTelefone;
	
	@ManyToMany(fetch = FetchType.EAGER)	
	@JoinTable(name = "usuarios_role", // Nome da tabela
    	joinColumns = @JoinColumn(name = "usuario_id", // nome da coluna
                  					referencedColumnName = "id",// foreinkey
                  					table = "usuario"),
		
		inverseJoinColumns = @JoinColumn(name="role_id", // nome da coluna
										referencedColumnName = "id",unique = false,// foreinkey
										table = "role"))// "JoinTable cria tabela no banco"
	private List<Role> roles;
	
	String token = "";
	
	@ManyToOne
	private Profissao profissao;
	
	@JsonFormat(pattern = "dd/MM/yyyy",shape = JsonFormat.Shape.STRING,locale = "pt-BR", timezone = "Brazil/East")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(iso=ISO.DATE, pattern="dd/MM/yyyy")
	private Date dataNascimento;
	
	
	// GET E SET
	
	public Profissao getProfissao() {
		return profissao;
	}
	public void setProfissao(Profissao profissao) {
		this.profissao = profissao;
	}
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
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
	
	
	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}
	@JsonIgnore
	@Override
	public String getPassword() {
		return this.senha;
	}
	@JsonIgnore
	@Override
	public String getUsername() {
		return this.login;
	}
	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	
	
}
