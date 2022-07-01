package my.project.acspring.utils.backoffice;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
public class RoleChecker {

public boolean hasRole(String role) {
		
		SecurityContext context = SecurityContextHolder.getContext();
		
		if(context == null)
			return false;
		
		
		Authentication auth = context.getAuthentication();
		
		if (auth == null)
			return false;
		
		
		Collection<? extends GrantedAuthority> roles = auth.getAuthorities();
		
		for (GrantedAuthority authority : roles)
			if (role.equals(authority.getAuthority()))
				return true;

		
		return false;
	}
}
