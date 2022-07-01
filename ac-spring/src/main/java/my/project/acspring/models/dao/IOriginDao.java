package my.project.acspring.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import my.project.acspring.models.entity.Origin;


public interface IOriginDao extends CrudRepository<Origin, Long> {
	
	@Query(value = "SELECT id, nombre, vacio FROM origenes WHERE vacio = ?1 ORDER BY id ASC", nativeQuery = true)
	public List<Origin> findEmpty(boolean empty);
	
	public Origin findByName(String name);
	
	@Query(value = "SELECT COUNT(id) FROM origenes WHERE vacio = ?1", nativeQuery = true)
	public Long countEmpty(boolean empty);

}
