package my.project.acspring.models.interfaces;

import java.util.List;

import my.project.acspring.models.entity.Brand;


public interface IBrandService {
	
	/**
	 * Obtiene todas las marcas.
	 * @return Lista de marcas.
	 */
	public List<Brand> getAll();
	
	/**
	 * Obtiene todas las marcas vacías o con clásicos.
	 * @param empty Si es true la marca está vacía (no tiene clásicos).
	 * @return Lista de marcas vacías o con clásicos.
	 */
	public List<Brand> getAllEmpty(boolean empty);

	/**
	 * Obtiene los nombres y los países de todas las marcas que tengan clásicos aprobados
	 * @return Lista de marcas.
	 */
	public List<Brand> getAllWithClassicsApproved();
	
	/**
	 * Obtiene una marca con un id determinado.
	 * @param id Id de la marca.
	 * @return Marca que coincide con un id o null si no encuentra ninguna.
	 */
	public Brand getById(Long id);
	
	/**
	 * Obtiene una marca con un id determinado con todos sus clásicos aprobados.
	 * @param id Id de la marca.
	 * @return Marca que coincide con un id o null si no encuentra ninguna.
	 */
	public Brand getByIdWithClassicsApproved(Long id);
	
	/**
	 * Guarda una marca.
	 * @param brand Marca con sus datos.
	 * @return Marca guardada.
	 */
	public Brand save(Brand brand);
	
	/**
	 * Borra una marca con un id determinado.
	 * @param id Id de la marca.
	 */
	public void delete(Long id);
	
	/**
	 * Comprueba si una marca con un determinado id existe.
	 *
	 * @param id Id de la marca.
	 * @return {@literal true} si la marca existe, {@literal false} en caso contrario.
	 */
	public boolean exists(Long id);
	
	/**
	 * Cuenta el número total de marcas.
	 * @return Número total de marcas.
	 */
	public Long count();
	
	/**
	 * Cuenta el número total de marcas vacías o con clásicos.
	 * @param empty Si es true la marca está vacía (no tiene clásicos).
	 * @return Número total de marcas vacías o con clásicos.
	 */
	public Long countEmpty(boolean empty);

}
