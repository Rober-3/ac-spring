package my.project.acspring.utils.backoffice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import my.project.acspring.models.dao.IUserDao;
import my.project.acspring.models.entity.UserR;


@Service
public class LoggedInUserDetailsService implements UserDetailsService {
	
	@Autowired
	private IUserDao userDao;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserR userR = userDao.findByUsername(username);
		
		if (userR == null) {
			throw new UsernameNotFoundException("El usuario " + username + " no existe.");
		}
		
		return new LoggedInUserDetails(userR);
	}

}
