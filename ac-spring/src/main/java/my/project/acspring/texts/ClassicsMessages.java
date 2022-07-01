package my.project.acspring.texts;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import my.project.acspring.models.entity.Classic;


@Component
public class ClassicsMessages {

	public static final String NEW_CLASSIC_MESSAGE = "Por favor, introduce los datos del clásico.";
	public static final String EDIT_CLASSIC_MESSAGE = "Por favor, modifica los datos del clásico.";

	/**
	 * Muestra un mensaje en la lista de clásicos cuando se ha guardado correctamente un clásico.
	 * @param model Almacena el mensaje y su tipo.
	 */
	public void savedClassicMessage(Model model) {
		model.addAttribute("class", "success").addAttribute("message", "Clásico guardado correctamente.");
	}

	/**
	 * Muestra un mensaje en la lista de clásicos cuando se ha borrado correctamente un clásico.
	 * @param model Almacena el mensaje y su tipo.
	 */
	public void deletedClassicMessage(Model model) {
		model.addAttribute("class", "success").addAttribute("message", "Clásico borrado correctamente.");
	}

	/**
	 * Muestra un mensaje en la lista de clásicos del panel del administrador cuando se ha aprobado correctamente un clásico.
	 * @param model Almacena el mensaje y su tipo.
	 */
	public void approvedClassicMessage(Model model) {
		model.addAttribute("class", "success").addAttribute("message", "Clásico aprobado correctamente.");
	}

	/**
	 * Muestra un mensaje en la lista de clásicos cuando no hay registros en la base de datos.
	 * @param model Almacena el mensaje y su tipo.
	 */
	public void noClassicsMessage(Model model) {
		model.addAttribute("class", "warning").addAttribute("message", "No hay clásicos que mostrar.");
	}

	/**
	 * Muestra un mensaje en la lista de clásicos cuando se introduce en la barra de direcciones un id inexistente.
	 * @param redAttrib Almacena el mensaje y su tipo.
	 */
	public void noClassicMessage(RedirectAttributes redAttrib) {
		redAttrib.addFlashAttribute("class", "warning").addFlashAttribute("message", "No existe ese clásico.");
	}

	/**
	 * Muestra un mensaje cuando un usuario intenta acceder a un clásico que no le pertenece.
	 * @param redAttrib Almacena el mensaje y su tipo.
	 */
	public void forbiddenClassicMessage(RedirectAttributes redAttrib) {
		redAttrib.addFlashAttribute("class", "danger")
		 		 .addFlashAttribute("message", "No tienes privilegios para acceder a ese clásico.");
	}

	/**
	 * Muestra un mensaje en la lista de clásicos cuando se introduce una marca que no existe.
	 * @param model Almacena el mensaje y su tipo.
	 */
	public void classicsBrandMessage(Model model) {
		model.addAttribute("class", "warning").addAttribute("message", "No hay clásicos de esa marca.");
	}

	/**
	 * Muestra un mensaje en la lista de clásicos cuando se introduce un origen que no existe.
	 * @param model Almacena el mensaje y su tipo.
	 */
	public void classicsOriginMessage(Model model) {
		model.addAttribute("class", "warning").addAttribute("message", "No hay clásicos con ese origen.");
	}

	/**
	 * Muestra en las vistas de información y de edición de cada clásico y en la lista de
	 * clásicos del administrador una placa de colore con el estado de cada clásico.
	 * @param classic Clásico aprobado, pendiente o eliminado.
	 * @param model   Almacena el tipo de placa y el estado del clásico.
	 */
	public void statusBadge(Classic classic, Model model) {

		String classBadge = "";
		String status = "";

		if (classic.getApproved() && !classic.getDeleted()) {
			classBadge = "badge bg-success text-white";
			status = "Aprobado";

		} else if (!classic.getApproved() && !classic.getDeleted()) {
			classBadge = "badge bg-warning text-dark";
			status = "Pendiente";

		} else {
			classBadge = "badge bg-danger text-white";
			status = "Eliminado";
		}

		model.addAttribute("classBadge", classBadge).addAttribute("status", status);
	}
	
}
