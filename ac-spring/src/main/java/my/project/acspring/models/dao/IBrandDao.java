package my.project.acspring.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import my.project.acspring.models.entity.Brand;


public interface IBrandDao extends CrudRepository<Brand, Long> {

	static final String SELECT = "SELECT * ";
	static final String FROM = "FROM marcas m ";

	static final String WHERE = "WHERE c.marca_id = m.id ";
	static final String WHERE_ = "WHERE m.vacia = ?1 ";

	static final String AND = "AND c.eliminado IS FALSE ";
	static final String AND_IN = "AND c.aprobado IN (SELECT c.aprobado FROM clasicos c WHERE c.aprobado IS TRUE) ";

	
	@Query(value = "SELECT DISTINCT m.nombre, p.nombre, m.id, c.marca_id, p.id, m.pais_id, c.aprobado, c.eliminado, m.vacia "
				 + "FROM marcas m, clasicos c, paises p " + WHERE + "AND p.id = m.pais_id " + AND + AND_IN + "ORDER BY m.nombre ASC", nativeQuery = true)
	public List<Brand> findWithClassicsApproved();

	@Query(value = SELECT + FROM + WHERE_ + "ORDER BY m.id ASC", nativeQuery = true)
	public List<Brand> findEmpty(boolean empty);

	@Query(value = SELECT + "FROM marcas m, clasicos c " + WHERE + "AND m.id = ?1 " + AND + AND_IN + "ORDER BY c.id ASC", nativeQuery = true)
	public Brand findByIdWithClassicsApproved(Long id);

	@Query(value = "SELECT COUNT(id) " + FROM + WHERE_, nativeQuery = true)
	public Long countEmpty(boolean empty);

}
