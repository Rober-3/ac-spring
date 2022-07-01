package my.project.acspring.controllers.backoffice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import my.project.acspring.models.entity.Classic;
import my.project.acspring.models.interfaces.IClassicService;
import my.project.acspring.texts.ClassicsMessages;
import my.project.acspring.texts.ClassicsTitles;
import my.project.acspring.texts.CommonMessages;


/**
 * Gestiona los clásicos en el backoffice con métodos exclusivos para el administrador.
 */
@SessionAttributes("classic")
@RequestMapping("/ac-spring/backoffice")
@Secured("ROLE_ADMIN")
@Controller
public class ClassicsAdminController {

	@Autowired
	private IClassicService classicService;
	@Autowired
	private ClassicsMessages classicsMessages;
	@Autowired
	private CommonMessages commonMessages;

	private static final String CLASSICS_LIST = "views/backoffice/list";
	private static final String CLASSIC_VIEW = "views/backoffice/details";
	private static final String CLASSICS_BY_STATUS = "redirect:/ac-spring/backoffice/classics_status";


	/**
	 * Muestra los clásicos aprobados por el administrador, los pendientes de aprobación y los borrados por los usuarios. Si no hay
	 * clásicos en la base de datos muestra un mensaje en la vista.
	 * 
	 * @param approved Si es verdadero y deleted falso muestra los clásicos aprobados, y si es falso y deleted falso los pendientes.
	 * @param deleted Si es verdadero y approved nulo muestra los clásicos borrados por el usuario al que pertenecen, y si es falso
	 * 				  los otros dos tipos.
	 * @param model Guarda la lista de clásicos, el título y el mensaje para mostrarlos en la vista. El título mostrado depende de
	 * 				la lista de clásicos (aprobados, pendientes o eliminados).
	 * @return Vista con la lista de clásicos.
	 */
	@GetMapping("/classics_status")
	public String classicsByStatus(@RequestParam(required = false) Boolean approved, @RequestParam Boolean deleted, Model model) {

		String title = ClassicsTitles.CLASSICS_DELETED_TITLE;
		List<Classic> classics = new ArrayList<Classic>();

		try {
			
			if (deleted) {
				classics = classicService.getDeleted();

			} else {

				if (approved)
					title = ClassicsTitles.CLASSICS_APPROVED_TITLE;
				else
					title = ClassicsTitles.CLASSICS_PENDING_TITLE;

				classics = classicService.getByStatus(approved);
			}
			
		} catch (Exception e) {
			commonMessages.errorMessage(e, model);
		}

		if (classics.isEmpty())
			classicsMessages.noClassicsMessage(model);
		
		model.addAttribute("title", title);
		model.addAttribute("classics", classics);

		return CLASSICS_LIST;
	}


	/**
	 * Aprueba un clásico haciéndolo visible al público y a los usuarios a los que no pertenece buscándolo
	 * por su id, y cambia su placa de estado (aprobado, pendiente o eliminado). Si no lo encuentra o se
	 * introduce en la URL un id inexistente redirecciona a {@code classicsByStatus} y muestra un mensaje
	 * en la vista. Si se introduce en la URL un id no entero captura la excepción, redirecciona a {@code
	 * classicsByStatus} y muestra un mensaje en la vista.
	 * 
	 * @param id Id del clásico.
	 * @param model Guarda el clásico para mostrarlo en la vista.
	 * @param redAttrib Guarda un mensaje para mostrarlo en la vista si no encuentra un clásico o cuando
	 * 					se produce una excepción al parsear el id.
	 * @return Vista con el clásico aprobado o redirección a {@code classicsByStatus} en caso de no poder
	 * 		   aprobar un clásico o una excepción.
	 */
	@RequestMapping(value = "/approve_classic/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
	public String approveClassic(@PathVariable String id, Model model, RedirectAttributes redAttrib) {

		Classic classic = new Classic();
		
		try {
			Long idClassic = Long.parseLong(id);

			if (!classicService.exists(idClassic)) {
				classicsMessages.noClassicMessage(redAttrib);
				return CLASSICS_BY_STATUS;
			}
			
			classicService.approve(idClassic);
			classic = classicService.getById(idClassic);
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			commonMessages.numberFormatExceptionMessage(redAttrib);
			return CLASSICS_BY_STATUS;
			
		} catch (Exception e) {
			commonMessages.errorMessage(e, model);
		}

		classicsMessages.approvedClassicMessage(model);
		classicsMessages.statusBadge(classic, model);
		model.addAttribute("classic", classic);

		return CLASSIC_VIEW;
	}

} // ClassicsAdminController
