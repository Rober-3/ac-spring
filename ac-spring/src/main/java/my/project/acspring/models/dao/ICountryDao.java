package my.project.acspring.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import my.project.acspring.models.entity.Country;


public interface ICountryDao extends CrudRepository<Country, Long> {
	
	@Query(value = "SELECT p.id, p.nombre, p.origen_id, p.vacio, o.id, o.nombre " 
				 + "FROM paises p, origenes o "
				 + "WHERE p.vacio = ?1 " 
				 + "AND p.origen_id = o.id " 
				 + "ORDER BY p.id ASC", nativeQuery = true)
	public List<Country> findEmpty(boolean empty);

	@Query(value = "SELECT COUNT(id) FROM paises WHERE vacio = ?1", nativeQuery = true)
	public Long countEmpty(boolean empty);

}
