package my.project.acspring.models.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import my.project.acspring.models.dao.IRoleDao;
import my.project.acspring.models.dao.IUserDao;
import my.project.acspring.models.entity.Role;
import my.project.acspring.models.entity.UserDto;
import my.project.acspring.models.entity.UserR;
import my.project.acspring.models.interfaces.IUserService;


@Service
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IRoleDao roleDao;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	

	@Transactional(readOnly = true)
	@Override
	public List<UserR> getAll() {
		return (List<UserR>) userDao.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public UserR getById(Long id) {
		return userDao.findById(id).orElse(null);
	}

	@Transactional(readOnly = true)
	@Override
	public Long getIdByUsername(String username) {
		return userDao.findIdByUsername(username);
	}

	@Transactional(readOnly = true)
	@Override
	public UserR getUserByUsername(String username) {
		return userDao.findByUsername(username);
	}

	@Transactional
	@Override
	public UserR save(UserR userR) {
		return userDao.save(userR);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		userDao.deleteById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Long count() {
		return userDao.count();
	}

	@Transactional(readOnly = true)
	@Override
	public boolean exists(Long id) {
		return userDao.existsById(id);
	}

	public UserR register(UserDto user) throws Exception {
		
		// La comprobación del nombre se hace en el catch (DataIntegrityViolationException e) de registerUser en RegistryController
//		if (checkIfUserExist(user.getUsername())) {
//			throw new Exception("El nombre de usuario ya existe en la base de datos.");
//		}
		
		UserR registeredUser = new UserR();
		BeanUtils.copyProperties(user, registeredUser);
		
		Role role = roleDao.findByName("ROLE_USER");
		Collection<Role> roles = new ArrayList<>();
		roles.add(role);
		
		registeredUser.setRoles(roles);
		registeredUser.setPassword(passwordEncoder.encode(user.getPassword()));
		
		return userDao.save(registeredUser);
	}
	
	public boolean checkIfUserExist(String userName) {
		return userDao.findByUsername(userName) != null ? true : false;
	}

}
