package my.project.acspring.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import my.project.acspring.models.entity.UserR;


public interface IUserDao extends CrudRepository<UserR, Long> {
	
	public UserR findByUsername(String username);

	@Query(value = "SELECT id FROM usuarios WHERE nombre_usuario = ?1", nativeQuery = true)
	public Long findIdByUsername(String username);

}
