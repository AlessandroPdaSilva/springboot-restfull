package curso.springboot.restfull.security;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;

import curso.springboot.restfull.model.Usuario;
import curso.springboot.restfull.repository.UsuarioRepository;


@Service
@Transactional
public class ImplementacaoUserDetailsService implements UserDetailsService{

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario u = usuarioRepository.findUsuarioByLogin(username);
		
		if(u == null) {
			throw new UsernameNotFoundException("Usuario não encontrado");
		}
		
		return new User(u.getLogin(), u.getSenha(), u.getAuthorities());
	}

}
