package my.project.acspring.texts;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Component
public class CountriesMessages {

	public static final String NEW_COUNTRY_MESSAGE = "Por favor, introduce los datos del país.";
	public static final String EDIT_COUNTRY_MESSAGE = "Por favor, modifica los datos del país.";
	
	
	/**
	 * Muestra un mensaje en la lista de países cuando no hay registros en la base de datos.
	 * @param model Almacena el mensaje y su tipo.
	 */
	public void noCountriesMessage(Model model) {
		model.addAttribute("class", "warning").addAttribute("message", "No hay países que mostrar.");
	}

	/**
	 * Muestra un mensaje en la lista de países cuando se introduce en la barra de direcciones un id inexistente.
	 * @param redAttrib Almacena Almacena el mensaje y su tipo.
	 */
	public void noCountryMessage(RedirectAttributes redAttrib) {
		redAttrib.addFlashAttribute("class", "warning").addFlashAttribute("message", "No existe ese país.");
	}

	/**
	 * Muestra un mensaje en la lista de países cuando se ha guardado correctamente un país.
	 * @param model Almacena el mensaje y su tipo.
	 */
	public void savedCountryMessage(Model model) {
		model.addAttribute("class", "success").addAttribute("message", "País guardado correctamente.");
	}

	/**
	 * Muestra un mensaje en la lista de países cuando se ha borrado correctamente un país.
	 * @param model Almacena el mensaje y su tipo.
	 */
	public void deletedCountryMessage(Model model) {
		model.addAttribute("class", "success").addAttribute("message", "País borrado correctamente.");
	}
	
	/**
	 * Muestra un mensaje en la lista de países cuando no se puede borrar un país.
	 * @param redAttribl Almacena el mensaje y su tipo.
	 */
	public void notDeletedCountryMessage(RedirectAttributes redAttrib) {
		redAttrib.addFlashAttribute("class", "warning").addFlashAttribute("message", "No se puede borrar porque tiene marcas guardadas.");
	}

	/**
	 * Muestra un mensaje cuando un país no tiene marcas guardadas.
	 * @param model Almacena el mensaje y su tipo.
	 */
	public void noBrandCountryMessage(Model model) {
		model.addAttribute("class", "warning").addAttribute("message", "Este país no tiene marcas.");
	}
	
}
