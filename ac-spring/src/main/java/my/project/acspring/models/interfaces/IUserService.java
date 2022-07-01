package my.project.acspring.models.interfaces;

import java.util.List;

import my.project.acspring.models.entity.UserDto;
import my.project.acspring.models.entity.UserR;


public interface IUserService {

	/**
	 * Obtiene todos los usuarios.
	 * @return Lista de usuarios.
	 */
	public List<UserR> getAll();
	
	/**
	 * Obtiene un usuario por su id.
	 * @param id Id del usuario.
	 * @return Usuario que coincide con un id o null si no encuentra ninguno.
	 */
	public UserR getById(Long id);
	
	/**
	 * Obtiene el id de usuario por su nombre.
	 * @param username Nombre de usuario.
	 * @return Usuario que coincide con un id o null si no encuentra ninguno.
	 */
	public Long getIdByUsername(String username);
	
	public UserR getUserByUsername(String username);
	
	/**
	 * Guarda un nuevo usuario.
	 * @param userR Usuario con sus datos.
	 * @return Usuario guardado.
	 */
	public UserR save(UserR userR);
	
	/**
	 * Borra un usuario por su id.
	 * @param id Id del usuario.
	 */
	public void delete(Long id);
	
	/**
	 * Cuenta el número total de usuario.
	 * @return Número total de usuarios.
	 */
	public Long count();
	
	/**
	 * Comprueba si un usuario con un determinado id existe.
	 *
	 * @param id Id del usuario.
	 * @return {@literal true} si el usuario existe, {@literal false} en caso contrario.
	 */
	public boolean exists(Long id);
	
	public UserR register(UserDto userDto) throws Exception;
	
}
