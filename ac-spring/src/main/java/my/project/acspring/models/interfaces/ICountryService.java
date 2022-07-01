package my.project.acspring.models.interfaces;

import java.util.List;

import my.project.acspring.models.entity.Country;


public interface ICountryService {
	
	/**
	 * Obtiene todos los países.
	 * @return Lista de países.
	 */
	public List<Country> getAll();
	
	/**
	 * Obtiene todas las países vacíos o con marcas.
	 * @param vacio Si es true el país está vacío (no tiene marcas).
	 * @return Lista de países vacíos o con marcas.
	 */
	public List<Country> getAllEmpty(boolean vacio);
	
	/**
	 * Obtiene un país con un id determinado.
	 * @param id Id del país
	 * @return País que coincide con un id, o null si no encuentra ninguno.
	 */
	public Country getById(Long id);
	
	/**
	 * Guarda un país.
	 * @param pais País con sus datos.
	 * @return País guardado.
	 */
	public Country save(Country pais);
	
	/**
	 * Borra un país con un id determinado.
	 * @param id Id del país.
	 */
	public void delete(Long id);
	
	/**
	 * Comprueba si un país con un determinado id existe.
	 *
	 * @param id Id del país.
	 * @return {@literal true} si el país existe, {@literal false} en caso contrario.
	 */
	public boolean exists(Long id);
	
	/**
	 * Cuenta el número total de países.
	 * @return Número total de países.
	 */
	public Long countCountries();
	
	/**
	 * Cuenta el número total de países vacíos o con marcas
	 * @param vacio Si es true el país está vacío (no tiene marcas).
	 * @return Número total de países vacíos o con marcas
	 */
	public Long countEmpty(boolean vacio);

}
