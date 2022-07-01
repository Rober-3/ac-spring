package my.project.acspring.models.interfaces;

import java.util.List;

import my.project.acspring.models.entity.Classic;


public interface IClassicService {
	
	/**
	 * Obtiene tres clásicos al azar.
	 * @return Lista con tres clásicos.
	 */
	public List<Classic> getThree();
	
	/**
	 * Obtiene todos los clásicos.
	 * @return Lista de clásicos.
	 */
	public List<Classic> getAll();
	
	/**
	 * Obtiene todos los clásicos de una marca.
	 * @param id Id de la marca.
	 * @param approved
	 * @return Lista de clásicos.
	 */
	public List<Classic> getByBrand(Long id);
	
	/**
	 * Obtiene todos los clásicos de un origen.
	 * @param name Nombre del origen.
	 * @return Lista de clásicos.
	 */
	public List<Classic> getByOrigin(String name);
	
	/**
	 * Obtiene todos los clásicos validados pendientes de validar y eliminados.
	 * @param approved approved = true: el clásico es visible en la parte pública y para los usuarios registrados. <br>
	 * 				   approved = false el clásico está pendiente de aprobación y sólo es visible para el administrador y
	 * 				   para el usuario que ha registrado el clásico.
	 * @return Lista de clásicos.
	 */
	public List<Classic> getByStatus(Boolean approved);
	
	/**
	 * Obtiene todos los clásicos eliminados.
	 * @return Lista de clásicos.
	 */
	public List<Classic> getDeleted();
	
	/**
	 * Obtiene todos los clásicos pertenecientes a un usuario.
	 * @param id Id del usuario.
	 * @return Lista de clásicos.
	 */
	public List<Classic> getByUser(Long id);
	
	/**
	 * Obtiene todos los clásicos validados y pendientes de validar pertenecientes a un usuario.
	 * @param id Id del usuario.
	 * @param approved approved = true  el clásico está validado.
	 * 				   approved = false el clásico está pendiente de validación.
	 * @return Lista de clásicos.
	 */
	public List<Classic> getByUserApproved(Long id, boolean approved);
	
	/**
	 * Obtiene un clásico por su id.
	 * @param id Id del clásico.
	 * @return Clásico.
	 */
	public Classic getById(Long id);
	
	/**
	 * Obtiene un clásico por su id que esté validado.
	 * @param id Id del clásico.
	 * @return Clásico.
	 */
	public Classic getByIdApproved(Long id);
	
	/**
	 * Obtiene un clásico perteneciente a un usuario
	 * @param idUser Id del usuario.
	 * @param idClassic Id del clásico.
	 * @return Clásico.
	 */
	public Classic getByIdByUser(Long idUser, Long idClassic);
	
	/**
	 * Guarda un clásico.
	 * @param classic Clásico con sus datos.
	 * @return Clásico guardado.
	 */
	public Classic save(Classic classic);
	
	/**
	 * Borra un clásico con un id determinado.
	 * @param id Id del clásico.
	 */
	public void delete(Long id);
	
	/**
	 * Borra un clásico perteneciente a un usuario. 
	 * @param idUser Id del usuario.
	 * @param idClassic Id del clásico.
	 */
	public void deleteByUser(Long idUser, Long idClassic);
	
	/**
	 * Comprueba si un clásico con un determinado id existe.
	 *
	 * @param id Id del clásico.
	 * @return {@literal true} si el clásico existe,
	 * 		   {@literal false} en caso contrario.
	 */
	public boolean exists(Long id);
	
	/**
	 * Valida un clásico.
	 * @param id Id del clásico.
	 */
	public void approve(Long id);
	
	/**
	 * Cuenta el total de clásicos.
	 * @return Número total de clásicos.
	 */
	public Long count();
	
	/**
	 * Cuenta el total de clásicos validados o pendientes de validación.
	 * 
	 * @param approved Si es true los clásicos están aprobados por el
	 * 				   administrador y si es false están pendientes.
	 * @return Número total de clásicos.
	 */
	public Long countApproved(boolean approved);
	
	/**
	 * Cuenta el total de clásicos validados de cada marca.
	 * @param id Id de la marca.
	 * @return Número total de clásicos.
	 */
	public Long countApprovedByBrand(Long id);
	
	/**
	 * Cuenta el total de clásicos eliminados.
	 * @return Número total de clásicos.
	 */
	public Long countDeleted();
	
	/**
	 * Cuenta el total de clásicos de un usuario.
	 * @param id Id del usuario.
	 * @return Número total de clásicos.
	 */
	public Long countUserClassics(Long id);
	
	/**
	 * Cuenta el número total de clásicos de un usuario determinado.
	 * @param id Id del usuario.
	 * @param approved Si es true los clásicos están aprobados por el
	 * 				   administrador y si es false están pendientes.
	 * @return Número total de clásicos.
	 */
	public Long countUserClassicsApproved(Long id, Boolean approved);

}
