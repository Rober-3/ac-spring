package my.project.acspring.texts;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import my.project.acspring.models.entity.Country;


@Component
public class CountriesTitles {

	public static final String COUNTRIES_LIST_TITLE = "Lista de países";
	public static final String COUNTRIES_WITH_BRANDS_TITLE = "Países con marcas";
	public static final String EMPTY_COUNTRIES_TITLE = "Países vacíos";
	
	public static final String NEW_COUNTRY_TITLE = "Nuevo país";
	public static final String EDIT_COUNTRY_TITLE = "Editar país";
	public static final String SAVED_COUNTRY_TITLE = "País guardado";
	public static final String DELETED_COUNTRY_TITLE = "País borrado";


	/**
	 * Muestra el título cuando se visualiza un pais determinado.
	 * 
	 * @param model   Almacena el título.
	 * @param country País del que se extrae el nombre.
	 */
	public void countryTitle(Model model, Country country) {
		model.addAttribute("title", country.getName());
	}
	
}
