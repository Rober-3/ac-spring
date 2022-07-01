package my.project.acspring.texts;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Component
public class CommonMessages {
	
	/**
	 * Muestra un mensaje de error cuando se introduce un nombre que ya existe en la base de datos.
	 * @param model Guarda el mensaje y su tipo.
	 */
	public void nameExistsMessage(Model model) {
		model.addAttribute("class", "warning").addAttribute("message", "Ese nombre ya existe. Por favor, introduce otro.");
	}
	
	/**
	 * Muestra un mensaje de error cuando se produce una excepción al parsear un número.
	 * @param redAttrib Guarda el mensaje y su tipo.
	 */
	public void numberFormatExceptionMessage(RedirectAttributes redAttrib) {
		redAttrib.addFlashAttribute("class", "danger")
				 .addFlashAttribute("message", "Ha habido un error con el id. Por favor, ponte en contacto con el administrador.");
	}
	
	/**
	 * Muestra un mensaje de error en la vista cuando se produce una excepción.
	 * @param e Excepción producida.
	 * @param model Guarda el mensaje para mostrarlo en la vista.
	 * @see <code>exceptionMessage</code>
	 */
	public void errorMessage(Exception e, Model model) {
		e.printStackTrace();
		model.addAttribute("class", "danger").addAttribute("message", "Ha habido un error. Por favor, ponte en contacto con el administrador.");
	}
	
	/**
	 * Muestra un mensaje de error cuando se produce una excepción al copiar una imagen en el directorio de fotos.
	 * @param model Guarda el mensaje y su tipo.
	 */
	public void copyPhotoExceptionMessage(Model model) {
		model.addAttribute("class", "danger").addAttribute("message", "Ha habido un error al copiar la foto.");
	}

}
