package my.project.acspring.controllers.backoffice;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import my.project.acspring.models.entity.Origin;
import my.project.acspring.models.interfaces.IOriginService;
import my.project.acspring.texts.CommonMessages;
import my.project.acspring.texts.OriginsMessages;
import my.project.acspring.texts.OriginsTitles;


/**
 * Gestiona los países en el backoffice de acceso sólo para el administrador.
 */
@SessionAttributes("origin")
@RequestMapping("/ac-spring/backoffice")
@Secured("ROLE_ADMIN")
@Controller
public class OriginsController {

	@Autowired
	private IOriginService originService;
	@Autowired
	private OriginsMessages originsMessages;
	@Autowired
	private OriginsTitles originsTitles;
	@Autowired
	private CommonMessages commonMessages;

	private static final String ORIGINS_LIST = "views/backoffice/list";
	private static final String ORIGIN_DETAILS = "views/backoffice/details";
	private static final String FORM_ORIGIN = "views/backoffice/form";
	private static final String ORIGINS_METHOD = "redirect:/ac-spring/backoffice/origins";


	/**
	 * Muestra los orígenes que tienen una lista de países y los vacíos. Si no hay orígenes
	 * en la base de datos muestra un mensaje en la vista.
	 * 
	 * @param empty Si es verdadero muestra los orígenes que tienen países, si es falso los
	 * 		  		vacíos y si es nulo todos.
	 * @param model Guarda la lista de orígenes, el título y el mensaje para mostrarlos en
	 *  			la vista. El título depende de la lista de orígenes (todos, con países
	 *  			o vacíos).
	 * @return Vista con la lista de orígenes.
	 */
	@GetMapping({"/origins", "/empty_origins/{empty}"})
	public String origins(@PathVariable(required = false) Boolean empty, Model model) {
		
		String title = OriginsTitles.ORIGINS_LIST_TITLE;
		List<Origin> origins = new ArrayList<Origin>();
		
		try {
			origins = originService.getAll();
			
			if (empty != null) {
				title = OriginsTitles.ORIGINS_WITH_COUNTRIES_TITLE;
				origins = originService.getAllEmpty(false);
				
				if (empty) {
					title = OriginsTitles.EMPTY_ORIGINS_TITLE;
					origins = originService.getAllEmpty(true);
				}
				
				if (origins.isEmpty())
					originsMessages.noOriginsMessage(model);
			}
			
		} catch (Exception e) {
			commonMessages.errorMessage(e, model);
		}
		
		model.addAttribute("title", title);
		model.addAttribute("origins", origins);
		
		return ORIGINS_LIST;
	}	


	/**
	 * Muestra un origen buscándolo por su id. Si no lo encuentra o se introduce en la URL un id
	 * inexistente redirecciona a <code>origins</code> a través de <code>messageAndRedirect</code>
	 * y muestra un mensaje en la vista. Si se introduce en la URL un id no entero se captura la
	 * excepción, redirecciona a <code>origins</code> a través de <code>exceptionAndRedirect</code>
	 * y muestra un mensaje en la vista.
	 * 
	 * @param id Id del origen. 
	 * @param model Guarda el origen, el título y el mensaje para mostrarlos en la vista.
	 * @param redAttrib Guarda un mensaje para mostrarlo en la vista cuando no encuentra un origen
	 * 		 		    o es nulo, o cuando se produce una excepción al parsear el id.
	 * @return Vista con el origen cuyo id coincide con el buscado.
	 */
	@GetMapping(value = "/origin/{id}")
	public String origin(@PathVariable String id, Model model, RedirectAttributes redAttrib) {

		Origin origin = new Origin();
		
		try {
			Long idOrigin = Long.parseLong(id);

			if (!originService.exists(idOrigin))
				messageAndRedirect(redAttrib);

			origin = originService.getById(idOrigin);
			
			if (origin == null)
				messageAndRedirect(redAttrib);
			
		} catch (NumberFormatException e) {
			exceptionAndRedirect(e, redAttrib);
			
		} catch (Exception e) {
			commonMessages.errorMessage(e, model);
		}

		originsTitles.originTitle(model, origin);
		model.addAttribute("origin", origin);

		return ORIGIN_DETAILS;
	}


	/**
	 * Muestra el formulario para introducir un origen nuevo o para editar uno existente. Para editar un origen lo busca
	 * por su id, y si no lo encuentra o se introduce en la URL un id inexistente redirecciona a <code>origins</code> a
	 * través de <code>messageAndRedirect</code> y muestra un mensaje en la vista. Si se introduce en la URL un id no
	 * entero captura la excpeción, redirecciona a <code>origins</code> através de <code>exceptionAndRedirect</code> y
	 * muestra un mensaje en la vista.
	 * 
	 * @param id Id del origen. Si no es nulo se muestran los datos del país en el formulario. 
	 * @param model Guarda el origen, el título y el mensaje para mostrarlos en la vista. El título y el mensaje dependen
	 * 				de si se añade un origen nuevo o se edita uno existente.
	 * @param redAttrib Guarda un mensaje para mostrarlo en la vista si no se encuentra un origen o cuando se produce una
	 * 					excepción al parsear el id.
	 * @return Vista del formulario, vacío si se introduce un origen nuevo, con datos si se edita uno existente.
	 */
	@GetMapping({"/new_origin", "/edit_origin/{id}"})
	public String newEditOrigin(@PathVariable(required = false) String id, Model model, RedirectAttributes redAttrib) {
		
		String title = OriginsTitles.NEW_ORIGIN_TITLE;
		String message = OriginsMessages.NEW_ORIGIN_MESSAGE;
		Origin origin = new Origin();
		
		try {
			
			if (id != null) {
				Long idOrigin = Long.parseLong(id);
				
				if (!originService.exists(idOrigin))
					messageAndRedirect(redAttrib);
				
				title = OriginsTitles.EDIT_ORIGIN_TITLE;
				message = OriginsMessages.EDIT_ORIGIN_MESSAGE;
				origin = originService.getById(idOrigin);
			}
			
		} catch (NumberFormatException e) {
			exceptionAndRedirect(e, redAttrib);
			
		} catch (Exception e) {
			commonMessages.errorMessage(e, model);
		}
		
		model.addAttribute("title", title);
		model.addAttribute("class", "warning").addAttribute("message", message);
		model.addAttribute("origin", origin);
		
		return FORM_ORIGIN;
	}


	/**
	 * Guarda un origen en la base de datos. Si se intenta guardar uno con un nombre existente
	 * captura la excepción, dirige de nuevo al formulario de origen y muestra un mensaje en la
	 * vista.
	 * 
	 * @param origin Origen a guardar.
	 * @param result Evalúa si los datos introducidos son correctos. Si son incorrectos dirige
	 * 				 de nuevo al formulario de orgien para corregirlos.
	 * @param model Guarda el origen, el título y el mensaje para mostrarlos en la vista. El
	 * 				título y el mensaje dependen de si se guarda un país nuevo o uno existente.
	 * @return Vista con el origen guardado.
	 */
	@PostMapping(value = "/save_origin")
	public String saveOrigin(@Valid Origin origin, BindingResult result, Model model) {

		try {
			
			if (result.hasErrors()) {
				String title = (origin.getId() == 0) ? OriginsTitles.NEW_ORIGIN_TITLE : OriginsTitles.EDIT_ORIGIN_TITLE;
				String message = (origin.getId() == 0) ? OriginsMessages.NEW_ORIGIN_MESSAGE : OriginsMessages.EDIT_ORIGIN_MESSAGE;
				
				model.addAttribute("title", title);
				model.addAttribute("class", "info").addAttribute("message", message);
				
				return FORM_ORIGIN;
			}
			
			origin = originService.save(origin);

		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			commonMessages.nameExistsMessage(model);
			model.addAttribute("title", OriginsTitles.NEW_ORIGIN_TITLE);
			
		} catch (Exception e) {
			commonMessages.errorMessage(e, model);
		}

		originsMessages.savedOriginMessage(model);
		model.addAttribute("title", OriginsTitles.SAVED_ORIGIN_TITLE);
		model.addAttribute("origin", origin);

		return ORIGIN_DETAILS;
	}


	/**
	 * Borra un origen de la base de datos buscándolo por su id. Si no lo encuentra o se introduce en
	 * la URL un id inexistente redirecciona a {@code origins} a través de {@code messageAndRedirect} 
	 * y muestra un mensaje en la vista. Si se introduce en la URL un id no entero captura la excepción,
	 * redirecciona a {@code origins} a través de {@code exceptionAndRedirect} y muestra un mensaje en 
	 * la vista. Si el origen tiene países no permite el borrado, redirecciona a {@code origins} y muestra
	 * un mensaje en la vista.
	 * 
	 * @param id Id del origen.
	 * @param model Guarda la lista de orígenes, el título y el mensaje para mostrarlos en la vista.
	 * @param redAttrib Guarda un mensaje para mostrarlo en la vista si no se encuentra un origen o se
	 * 					produce una excepción al parsear el id.
	 * @return Vista con la lista de orígenes o redirección a {@code origins} en caso de no borrar un origen.
	 */
	@RequestMapping(value = "/delete_origin/{id}", method = { RequestMethod.DELETE, RequestMethod.GET})
	public String deleteOrigin(@PathVariable String id, Model model, RedirectAttributes redAttrib) {

		List<Origin> origins = new ArrayList<Origin>();
		
		try {
			Long idOrigin = Long.parseLong(id);

			if (!originService.exists(idOrigin))
				messageAndRedirect(redAttrib);
			
			Origin origin = originService.getById(idOrigin);

			if (!origin.getCountries().isEmpty()) {
				originsMessages.notDeletedOriginMessage(redAttrib);
				return ORIGINS_METHOD;
			}

			originService.delete(idOrigin);
			origins = originService.getAll();
			
		} catch (NumberFormatException e) {
			exceptionAndRedirect(e, redAttrib);
			
		} catch (Exception e) {
			commonMessages.errorMessage(e, model);
		}

		originsMessages.deletedOriginMessage(model);
		model.addAttribute("title", OriginsTitles.ORIGINS_LIST_TITLE);
		model.addAttribute("origins", origins);

		return ORIGINS_LIST;
	}


	/**
	 * Redirecciona a {@code origins} y muestra un mensaje en la vista
	 * cuando no se encuentran resultados.
	 * 
	 * @param redAttrib Contiene el mensaje a mostrar.
	 * @return Redirección a {@code origins}.
	 */
	private String messageAndRedirect(RedirectAttributes redAttrib) {
		originsMessages.noOriginMessage(redAttrib);
		return ORIGINS_METHOD;
	}
	
	
	/**
	 * Redirecciona a {@code origins</code> y muestra un mensaje en la vista cuando se
	 * produce una excepción.
	 * 
	 * @param e Excepción producida.
	 * @param redAttrib Contiene el mensaje a mostrar.
	 * @return Redirección a {@code origins}.
	 */
	private String exceptionAndRedirect(Exception e, RedirectAttributes redAttrib) {
		e.printStackTrace();
		commonMessages.numberFormatExceptionMessage(redAttrib);
		return ORIGINS_METHOD;
	}

} // OriginsController
