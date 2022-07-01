package my.project.acspring.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import my.project.acspring.models.dao.IRoleDao;
import my.project.acspring.models.entity.Role;
import my.project.acspring.models.interfaces.IRoleService;


@Service
public class RoleServiceImpl implements IRoleService {
	
	@Autowired
	private IRoleDao roleDao;

	
	@Transactional(readOnly = true)
	@Override
	public Role getByName(String name) {
		return roleDao.findByName(name);
	}

}
