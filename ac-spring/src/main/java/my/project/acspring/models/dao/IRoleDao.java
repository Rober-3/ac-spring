package my.project.acspring.models.dao;

import org.springframework.data.repository.CrudRepository;

import my.project.acspring.models.entity.Role;

public interface IRoleDao extends CrudRepository<Role, Long> {
	
	public Role findByName(String name);

}
