package my.project.acspring.texts;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Component
public class OriginsMessages {

	public static final String NEW_ORIGIN_MESSAGE = "Por favor, introduce los datos del origen.";
	public static final String EDIT_ORIGIN_MESSAGE = "Por favor, modifica los datos del origen.";

	
	/**
	 * Muestra un mensaje en la lista de orígenes cuando no hay registros en la base de datos.
	 * @param model Almacena el mensaje y su tipo.
	 */
	public void noOriginsMessage(Model model) {
		model.addAttribute("class", "warning").addAttribute("message", "No hay orígenes que mostrar.");
	}

	/**
	 * Muestra un mensaje en la lista de orígenes cuando se introduce en la barra de direcciones un id inexistente.
	 * @param id        Id del origen.
	 * @param redAttrib Almacena el mensaje y su tipo.
	 */
	public void noOriginMessage(RedirectAttributes redAttrib) {
		redAttrib.addFlashAttribute("class", "warning").addFlashAttribute("message", "No existe es origen.");
	}
	
	/**
	 * Muestra un mensaje en la lista de orígenes cuando se ha guardado correctamente un origen.
	 * @param model Almacena el mensaje y su tipo.
	 */
	public void savedOriginMessage(Model model) {
		model.addAttribute("class", "success").addAttribute("message", "Origen guardado correctamente.");
	}

	/**
	 * Muestra un mensaje en la lista de orígenes cuando se ha borrado correctamente un origen.
	 * @param model Almacena el mensaje y su tipo.
	 */
	public void deletedOriginMessage(Model model) {
		model.addAttribute("class", "success").addAttribute("message", "Origen borrado correctamente.");
	}

	/**
	 * Muestra un mensaje en la lista de orígenes cuando no se puede borrar un origen.
	 * @param redAttrib Almacena el mensaje y su tipo.
	 */
	public void notDeletedOriginMessage(RedirectAttributes redAttrib) {
		redAttrib.addFlashAttribute("class", "warning").addFlashAttribute("message", "No se puede borrar porque tiene países guardados.");
	}
	
	/**
	 * Muestra un mensaje cuando un origen no tiene países guardados.
	 * @param model  Almacena el mensaje y su tipo.
	 */
	public void noCountryOriginMessage(Model model) {
		model.addAttribute("class", "warning").addAttribute("message", "Este origen no tiene países.");
	}
	
}
