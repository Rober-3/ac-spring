package my.project.acspring.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import my.project.acspring.models.entity.Classic;


public interface IClassicDao extends CrudRepository<Classic, Long> {

	static final String SELECT = "SELECT * ";
	static final String SELECT_COUNT = "SELECT COUNT(id) FROM clasicos c ";
	
	static final String FROM = "FROM clasicos c, marcas m, paises p, origenes o, usuarios u ";
	static final String FROM_ = "FROM clasicos c ";
	
	static final String WHERE = "WHERE c.aprobado = ?1 ";
	static final String WHERE_ = "WHERE c.eliminado IS TRUE ";
	static final String WHERE__ = "WHERE u.id = ?1 ";
	static final String WHERE___ = "WHERE c.usuario_id = ?1 ";
	static final String WHERE____ = "WHERE c.id = ?1 ";
	
	static final String AND = "AND c.marca_id = m.id AND c.usuario_id = u.id AND m.pais_id = p.id AND p.origen_id = o.id ";
	static final String AND_ = "AND c.eliminado IS FALSE ";
	static final String AND__ = "AND c.aprobado = ?2 ";
	static final String AND___ = "AND c.id = ?2 ";
	static final String AND____ = "AND c.aprobado IS TRUE ";
	
	static final String ORDER_BY = "ORDER BY c.id ASC";
	static final String UPDATE = "UPDATE clasicos c ";
	
	
	@Query(value = SELECT + FROM_ + "WHERE c.aprobado IS TRUE " + AND_ + "ORDER BY RAND() LIMIT 3", nativeQuery = true)
	public List<Classic> findThree();
	
	@Query(value = SELECT + FROM + "WHERE m.id = ?1 " + AND + AND____ + AND_ + ORDER_BY, nativeQuery = true)
	public List<Classic> findByBrand(Long id);
	
	@Query(value = SELECT + FROM + "WHERE o.nombre = ?1 " + AND + AND____ + ORDER_BY, nativeQuery = true)
	public List<Classic> findByOrigin(String name);
	
	@Query(value = SELECT + FROM + WHERE + AND_ + AND + ORDER_BY, nativeQuery = true)
	public List<Classic> findByStatus(boolean approved);

	@Query(value = SELECT + FROM + WHERE_ + AND + ORDER_BY, nativeQuery = true)
	public List<Classic> findDeleted();
	
	@Query(value = SELECT + FROM + WHERE__ + AND + AND_ + ORDER_BY, nativeQuery = true)
	public List<Classic> findByUser(Long id);
	
	@Query(value = SELECT + FROM + WHERE__ + AND__ + AND_ + AND + ORDER_BY, nativeQuery = true)
	public List<Classic> findByUserApproved(Long id, boolean approved);
	
	@Query(value = SELECT + FROM + WHERE____ + AND, nativeQuery = true)
	public Classic findOne(Long id);
	
	@Query(value = SELECT + FROM + WHERE____ + AND____, nativeQuery = true)
	public Classic findOneApproved(Long id);
	
	@Query(value = SELECT + FROM + WHERE___ + AND + AND___, nativeQuery = true)
	public Classic findOneByUser(Long idUser, Long idClassic);
	
	@Query(value = SELECT_COUNT + WHERE + AND_, nativeQuery = true)
	public Long countApproved(boolean approved);
	
	@Query(value = "SELECT COUNT(id) FROM clasicos c WHERE c.marca_id = ?1 AND c.aprobado IS TRUE AND c.eliminado IS FALSE ", nativeQuery = true)
	public Long countApprovedByBrand(Long id);
	
	@Query(value = SELECT_COUNT + WHERE_, nativeQuery = true)
	public Long countDeleted();
	
	@Query(value = SELECT_COUNT + WHERE___ + AND_, nativeQuery = true)
	public Long countUserClassics(Long id);
	
	@Query(value = SELECT_COUNT + WHERE___ + AND__ + AND_, nativeQuery = true)
	public Long countUserClassicsApproved(Long id, boolean approved);
	
	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query(value = UPDATE + "SET c.eliminado = TRUE " + WHERE___ + AND___, nativeQuery = true)
	public void deleteByUser(Long idUser, Long idClassic);
	
	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query(value = UPDATE + "SET c.aprobado = TRUE " + WHERE____, nativeQuery = true)
	public void validate(Long id);
	
}
