package my.project.acspring.utils.backoffice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import my.project.acspring.models.entity.Role;
import my.project.acspring.models.entity.UserR;


/**
 * Muestra los atributos del usuario que ha iniciado sesión.
 */
public class LoggedInUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private UserR userR;
	

	public LoggedInUserDetails(UserR userR) {
		this.userR = userR;
	}
	
	
	public Long getId() {
		return this.userR.getId();
	}
	
	public String getEmail() {
		return this.userR.getEmail();
	}
	
	public String getPhoto() {
		return this.userR.getPhoto();
	}
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<Role> roles = userR.getRoles();
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		
		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		
		return authorities;
	}

	@Override
	public String getPassword() {
		return userR.getPassword();
	}

	@Override
	public String getUsername() {
		return userR.getUsername();
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
		return userR.getEnabled();
	}

}
