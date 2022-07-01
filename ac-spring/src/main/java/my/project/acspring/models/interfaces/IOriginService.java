package my.project.acspring.models.interfaces;

import java.util.List;

import my.project.acspring.models.entity.Origin;


public interface IOriginService {
	
	/**
	 * Obtiene todos los orígenes.
	 * @return Lista de orígenes.
	 */
	public List<Origin> getAll();
	
	/**
	 * Obtiene todos los orígenes vacíos o con países
	 * @param empty Si es true el origen está vacío (no tiene países).
	 * @return Lista de orígenes vacíos o con países.
	 */
	public List<Origin> getAllEmpty(boolean empty);
	
	/**
	 * Obtiene un origen por su id.
	 * @param id Id del origen.
	 * @return Origen que coincide con un id o null si no encuentra ninguno.
	 */
	public Origin getById(Long id);
	
	/**
	 * Obtiene un origen por su nombre.
	 * @param name Nombre del origen.
	 * @return Origen que coincide con el nombre.
	 */
	public Origin getByName(String name);
	
	/**
	 * Guarda un origen.
	 * @param origin Origen con sus datos.
	 * @return Origen guardado.
	 */
	public Origin save(Origin origin);
	
	/**
	 * Borra un origen con un id determinado.
	 * @param id Id del origen.
	 */
	public void delete(Long id);
	
	/**
	 * Comprueba si un origen con un determinado id existe.
	 *
	 * @param id Id del origen.
	 * @return {@literal true} si el origen existe, {@literal false} en caso contrario.
	 */
	public boolean exists(Long id);
	
	/**
	 * Cuenta el número total de orígenes.
	 * @return Número total de orígenes.
	 */
	public Long count();
	
	/**
	 * Cuenta el número total de orígenes vacíos o con países.
	 * @param empty Si es true el origen está vacío (no tiene países).
	 * @return Número total de orígenes vacíos o con países.
	 */
	public Long countEmpty(boolean empty);

}
