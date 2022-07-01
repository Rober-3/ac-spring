package my.project.acspring.texts;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import my.project.acspring.models.entity.Brand;


@Component
public class BrandsTitles {

	public static final String BRANDS_LIST_TITLE = "Lista de marcas";
	public static final String BRANDS_WITH_CLASSICS_TITLE = "Marcas con clásicos";
	public static final String EMPTY_BRANDS_TITLE = "Marcas vacías";

	public static final String NEW_BRAND_TITLE = "Nueva marca";
	public static final String EDIT_BRAND_TITLE = "Editar marca";
	public static final String SAVED_BRAND_TITLE = "Marca guardada";
	public static final String DELETED_BRAND_TITLE = "Marca borrada";


	/**
	 * Muestra el título cuando se visualiza una marca determinada.
	 * 
	 * @param model Almacena el título.
	 * @param brand Marca de la que se extrae el nombre.
	 */
	public void brandTitle(Model model, Brand brand) {
		model.addAttribute("title", brand.getName());
	}
	
}
