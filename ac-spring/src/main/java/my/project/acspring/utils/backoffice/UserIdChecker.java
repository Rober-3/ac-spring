package my.project.acspring.utils.backoffice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import my.project.acspring.models.interfaces.IUserService;


@Component
public class UserIdChecker {

@Autowired IUserService userService;
	
	public Long idCheck(Authentication auth) {
		
		String username = "";
		
		if (auth != null)
			username = auth.getName();
		
		Long id = userService.getIdByUsername(username);
		
		return id;
	}
}
