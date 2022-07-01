package my.project.acspring.texts;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;


@Component
public class ClassicsTitles {

	public static final String CLASSICS_LIST_TITLE = "Lista de clásicos";
	public static final String AMERICAN_CLASSICS_TITLE = "Clásicos americanos";
	public static final String EUROPEAN_CLASSICS_TITLE = "Clásicos europeos";

	public static final String CLASSICS_APPROVED_TITLE = "Clásicos aprobados";
	public static final String CLASSICS_PENDING_TITLE = "Clásicos pendientes de aprobación";
	public static final String CLASSICS_DELETED_TITLE = "Clásicos borrados por los usuarios";

	public static final String MY_CLASSICS_TITLE = "Mis clásicos";
	public static final String MY_CLASSICS_APPROVED_TITLE = "Mis clásicos aprobados";
	public static final String MY_CLASSICS_PENDING_TITLE = "Mis clásicos pendientes de aprobación";
	
	public static final String CLASSIC_DETAILS_TITLE = "Detalles del clásico";
	public static final String NEW_CLASSIC_TITLE = "Nuevo clásico";
	public static final String EDIT_CLASSIC_TITLE = "Editar clásico";
	public static final String SAVED_CLASSIC_TITLE = "Clásico guardado";
	public static final String DELETED_CLASSIC_TITLE = "Clásico borrado";


	/**
	 * Muestra el título cuando se visualizan los clásicos de una marca determinada.
	 * 
	 * @param model Almacena el título.
	 */
	public void classicsBrandTitle(Model model, String brand) {
		model.addAttribute("title", "Clásicos de " + brand);
	}

	/**
	 * Muestra el título cuando se visualizan los clásicos de un origen determinado.
	 * 
	 * @param model Almacena el título.
	 */
	public void classicsOriginTitle(Model model, String origin) {
		model.addAttribute("title", "Clásicos de origen " + origin);
	}
	
}
