package my.project.acspring.texts;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Component
public class BrandsMessages {
	
	public static final String NEW_BRAND_MESSAGE = "Por favor, introduce los datos de la marca.";
	public static final String EDIT_BRAND_MESSAGE = "Por favor, modifica los datos de la marca.";

	
	/**
	 * Muestra un mensaje en la lista de marcas cuando no hay registros en la base de datos.
	 * @param model Almacena el mensaje y su tipo.
	 */
	public void noBrandsMessage(Model model) {
		model.addAttribute("class", "warning").addAttribute("message", "No hay marcas que mostrar.");
	}

	/**
	 * Muestra un mensaje en la lista de marcas cuando se introduce en la barra de direcciones un id inexistente.
	 * @param redAttrib Almacena Almacena el mensaje y su tipo.
	 */
	public void noBrandMessage(RedirectAttributes redAttrib) {
		redAttrib.addFlashAttribute("class", "warning").addFlashAttribute("message", "No existe esa marca.");
	}
	
	/**
	 * Muestra un mensaje en la lista de marcas cuando se ha guardado correctamente una marca.
	 * @param model Almacena el mensaje y su tipo.
	 */
	public void savedBrandMessage(Model model) {
		model.addAttribute("class", "success").addAttribute("message", "Marca guardada correctamente.");
	}

	/**
	 * Muestra un mensaje en la lista de marcas cuando se ha borrado correctamente una marca.
	 * @param model Almacena el mensaje y su tipo.
	 */
	public void deletedBrandMessage(Model model) {
		model.addAttribute("class", "success").addAttribute("message", "Marca borrada correctamente.");
	}

	/**
	 * Muestra un mensaje en la lista de marcas cuando no se puede borrar una marca.
	 * @param redAttrib Almacena el mensaje y su tipo.
	 */
	public void notDeletedBrandMessage(RedirectAttributes redAttrib) {
		redAttrib.addFlashAttribute("class", "warning").addFlashAttribute("message", "No se puede borrar porque tiene clásicos guardados.");
	}

	/**
	 * Muestra un mensaje cuando una marca no tiene clásicos guardados.
	 * @param model Almacena el mensaje y su tipo.
	 */
	public void noClassicBrandMessage(Model model) {
		model.addAttribute("class", "warning").addAttribute("message", "Esta marca no tiene clásicos.");
	}

}
