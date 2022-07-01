package my.project.acspring.texts;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import my.project.acspring.models.entity.Origin;


@Component
public class OriginsTitles {

	public static final String ORIGINS_LIST_TITLE = "Lista de orígenes";
	public static final String ORIGINS_WITH_COUNTRIES_TITLE = "Orígenes con países";
	public static final String EMPTY_ORIGINS_TITLE = "Orígenes vacíos";
	
	public static final String NEW_ORIGIN_TITLE = "Nuevo origen";
	public static final String EDIT_ORIGIN_TITLE = "Editar origen";
	public static final String SAVED_ORIGIN_TITLE = "Origen guardado";
	public static final String DELETED_ORIGIN_TITLE = "Origen borrado";


	/**
	 * Muestra el título cuando se visualiza un origen determinado.
	 * 
	 * @param model   Almacena el título.
	 * @param classic Origen del que se extrae el nombre.
	 */
	public void originTitle(Model model, Origin origin) {
		model.addAttribute("title", origin.getName());
	}
	
}
