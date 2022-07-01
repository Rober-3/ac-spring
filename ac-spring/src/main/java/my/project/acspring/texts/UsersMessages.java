package my.project.acspring.texts;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Component
public class UsersMessages {

	/**
	 * Muestra un mensaje cuando se carga el formulario de usuarios.
	 * @param model Almacena el mensaje y su tipo.
	 */
	public void newUserMessage(Model model) {
		model.addAttribute("class", "info").addAttribute("message", "Por favor, introduce tus datos.");
	}
	
	/**
	 * Muestra un mensaje en el formulario de usuarios cuando se edita un usuario.
	 * @param model Almacena el mensaje y su tipo.
	 */
	public void editProfileMessage(Model model) {
		model.addAttribute("class", "info").addAttribute("message", "Por favor, modifica tus datos.");
	}

	/**
	 * Muestra un mensaje cuando se ha creado correctamente un usuario nuevo.
	 * @param model Almacena el mensaje y su tipo.
	 */
	public void savedUserMessage(Model model) {
		model.addAttribute("class", "success").addAttribute("message", "Usuario creado correctamente. Por favor, inicia sesión.");
	}
	
	/**
	 * Muestra un mensaje cuando se ha guardado correctamente el perfil de un usuario.
	 * @param model Almacena el mensaje y su tipo.
	 */
	public void savedProfileMessage(Model model) {
		model.addAttribute("class", "success").addAttribute("message", "Perfil guardado correctamente.");
	}
	
	/**
	 * Muestra un mensaje en la lista de usuarios cuando no hay registros en la base de datos.
	 * @param model Almacena el mensaje y su tipo.
	 */
	public void noUsersMessage(Model model) {
		model.addAttribute("class", "warning").addAttribute("message", "No hay usuarios que mostrar.");
	}

	/**
	 * Muestra un mensaje en la lista de usuarios cuando se introduce en la barra de direcciones un id inexistente.
	 * @param redAttrib Almacena el mensaje y su tipo.
	 */
	public void noUserMessage(RedirectAttributes redAttrib) {
		redAttrib.addFlashAttribute("class", "warning").addFlashAttribute("message", "No existe ese usuario.");
	}
	
	/**
	 * Muestra un mensaje cuando un usuario intenta acceder a la página del perfil de otro usuario.
	 * @param redAttrib
	 */
	public void forbiddenAccessMessage(RedirectAttributes redAttrib) {
		redAttrib.addFlashAttribute("class", "danger").addFlashAttribute("message", "No puedes acceder al perfil de ese usuario.");
	}
	
}
