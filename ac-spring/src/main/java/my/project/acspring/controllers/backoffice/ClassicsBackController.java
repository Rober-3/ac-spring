package my.project.acspring.controllers.backoffice;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import my.project.acspring.models.entity.Brand;
import my.project.acspring.models.entity.Classic;
import my.project.acspring.models.entity.UserR;
import my.project.acspring.models.interfaces.IBrandService;
import my.project.acspring.models.interfaces.IClassicService;
import my.project.acspring.models.interfaces.IUserService;
import my.project.acspring.texts.ClassicsMessages;
import my.project.acspring.texts.ClassicsTitles;
import my.project.acspring.texts.CommonMessages;
import my.project.acspring.utils.backoffice.RoleChecker;
import my.project.acspring.utils.frontoffice.PhotoMethods;


/**
 * Gestiona los clásicos en el backoffice de acceso sólo para el administrador y los usuarios registrados.
 */
@SessionAttributes("classic")
@RequestMapping("/ac-spring/backoffice")
@Secured({"ROLE_ADMIN", "ROLE_USER"})
@Controller
public class ClassicsBackController {
	
	@Autowired
	private IClassicService classicService;
	@Autowired
	private IBrandService brandService;
	@Autowired
	private IUserService userService;
	@Autowired
	private RoleChecker roleChecker;
	@Autowired
	private PhotoMethods photoMethods;
	@Autowired
	private ClassicsMessages classicsMessages;
	@Autowired
	private CommonMessages commonMessages;
	
	private static final String CLASSICS_LIST = "views/backoffice/list";
	private static final String CLASSIC_DETAILS = "views/backoffice/details";
	private static final String FORM_CLASSIC = "views/backoffice/form";
	private static final String CLASSICS_METHOD = "redirect:/ac-spring/backoffice/classics";
	private static final String ADMIN = "ROLE_ADMIN";
	private static final String USER = "ROLE_USER";


	/**
	 * Muestra todos los clásicos, los aprobados por el administrador y los pendientes de aprobación. A través de
	 * {@code hasRole} se determina el rol del usuario para mostrar determinados clásicos:<br>
	 * 
	 * - si es administrador muestra todos<br>
	 * - si es usuario registrado sólo muestra los que le pertenecen<br>
	 * 
	 * Si no hay clásicos en la base de datos muestra un mensaje en la vista.
	 * 
	 * @param approved Se usa cuando el usuario está registrado: si es nulo muestra todos los clásicos pertenecientes
	 * 				   al usuario, si es verdadero sus aprobados y si es falso sus pendientes.
	 * @param model Guarda la lista de clásicos, el título y el mensaje para mostrarlos en la vista. El título que se
	 * 				muestra depende de si el usuario está registrado o es administrador, y si es usuario depende del
	 * 				estado de sus clásicos (todos, aprobados o pendientes).
	 * @param auth Se emplea para obtener el nombre del usuario autenticado que luego se pasa como parámetro al método
	 * 			   {@code getUserByUsername}, el cual obtiene el usuario autenticado que se utiliza para invocar {@code
	 *			   getByUser} y obtener sus clásicos.
	 * @return Vista con la lista de clásicos.
	 */
	@GetMapping("/classics")
	public String classics(@RequestParam(required = false) Boolean approved, Model model, Authentication auth) {
		
		String title = ClassicsTitles.CLASSICS_LIST_TITLE;
		List<Classic> classics = new ArrayList<Classic>();
		
		try {
			
			if (roleChecker.hasRole(ADMIN))
				classics = classicService.getAll();
			
			if (roleChecker.hasRole(USER)) {
				
				UserR userR = userService.getUserByUsername(auth.getName());
				
				if (approved == null) {
					title = ClassicsTitles.MY_CLASSICS_TITLE;
					classics = classicService.getByUser(userR.getId());
					
				} else {	
					
					if (approved)
						title = ClassicsTitles.MY_CLASSICS_APPROVED_TITLE;
					else
						title = ClassicsTitles.MY_CLASSICS_PENDING_TITLE;
					
					classics = classicService.getByUserApproved(userR.getId(), approved);
				}
			}
			
			if (classics.isEmpty())
				classicsMessages.noClassicsMessage(model);
			
		} catch (Exception e) {
			commonMessages.errorMessage(e, model);
		}
		
		model.addAttribute("title", title);
		model.addAttribute("classics", classics);
		
		return CLASSICS_LIST;
	}
	
	
	/**
	 * Muestra un clásico buscándolo por su id. A través de {@code hasRole} determina el rol del usuario:<br>
	 * 
	 * - si es administrador se muestra el clásico sin restricciones junto a una placa de color con su estado (aprobado,
	 * 	 pendiente o eliminado)<br>
	 * - si es un usuario registrado muestra el clásico si le pertenece, en caso contrario muestra un mensaje en la vista<br>
	 * 
	 * Si no encuentra el clásico o se introduce en la URL un id inexistente redirecciona a {@code classics} a través de
	 * {@code messageAndRedirect} y muestra un mensaje en la vista. Si se introduce en la URL un id no entero captura la
	 * excepción, redirecciona a {@code classics} a través de {@code exceptionAndRedirect} y muestra un mensaje en la vista.
	 * 
	 * @param id Id del clásico.
	 * @param model Guarda el clásico, el título y el mensaje para mostrarlos en la vista.
	 * @param redAttrib Guarda un mensaje para mostrarlo en la vista si no encuentra un clásico o es nulo, y cuando salta
	 * 					una excepción al parsear el id.
	 * @param auth Se usa para obtener el nombre del usuario autenticado que se pasa a {@code getUserByUsername}, el cual
	 * 			   obtiene el usuario autenticado que se utiliza para invocar {@code getByIdByUser} y obtener el clásico
	 * 			   perteneciente al usuario.
	 * @return Vista con el clásico cuyo id coincide con el buscado o redirección a {@code classics} en caso de no haber un
	 * 		   clásico para mostrar.
	 */
	@GetMapping("/classic/{id}")
	public String classic(@PathVariable String id, Model model, RedirectAttributes redAttrib, Authentication auth) {
		
		Classic classic = new Classic();
		
		try {
			Long idClassic = Long.parseLong(id);
			
			if (roleChecker.hasRole(ADMIN)) {
				classic = classicService.getById(idClassic);
				classicsMessages.statusBadge(classic, model);
			}
			
			if (roleChecker.hasRole(USER)) {
				UserR userR = userService.getUserByUsername(auth.getName());
				classic = classicService.getByIdByUser(userR.getId(), idClassic);	
				
				if (classic == null) {
					classicsMessages.forbiddenClassicMessage(redAttrib);
					return CLASSICS_METHOD;
				}
			}
			
			if (!classicService.exists(idClassic))
				messageAndRedirect(redAttrib);
			
		} catch (NumberFormatException e) {
			exceptionAndRedirect(e, redAttrib);
			
		} catch (Exception e) {
			commonMessages.errorMessage(e, model);
		}
		
		model.addAttribute("title", ClassicsTitles.CLASSIC_DETAILS_TITLE);
		model.addAttribute("classic", classic);
		
		return CLASSIC_DETAILS;
	}


	/**
	 * Muestra el formulario para introducir un clásico nuevo o para editar uno existente. Para editar un clásico lo busca por su id, y si
	 * no lo encuentra o se introduce en la URL un id inexistente redirecciona a {@code classics} a través de {@code messageAndRedirect}
	 * y muestra un mensaje en la vista. Si se introduce en la URL un id no entero captura la excepción, redirecciona a {@code classics}
	 * a través de {@code exceptionAndRedirect} y muestra un mensaje en la vista. En brands guarda una lista de marcas para mostrarlas en
	 * un desplegable. Antes de editar comprueba el rol del usuario con {@code hasRole}:<br>
	 * 
	 * - si es administrador obtiene el clásico sin restricciones para mostrar sus datos en el formulario.<br>
	 * - si es usuario registrado comprueba que el clásico le pertenezca, en caso contrario redirecciona a {@code classics} y muestra un
	 * 	 mensaje en la vista.<br>
	 * 
	 * @param id Id del clásico.<br>
	 * 			 - Si es nulo se va a introducir un clásico nuevo y se le asigna el usuario autenticado.<br>
	 * 			 - Si no es nulo se muestran en el formulario los datos del clásico cuyo id coincide con el introducido en la URL.
	 * @param model Guarda el clásico, el título y el mensaje para mostrarlos en la vista. El título y el mensaje dependen de si se añade
	 * 			    un clásico nuevo o se edita uno existente.
	 * @param redAttrib Guarda un mensaje para mostrarlo en la vista si no encuentra un clásico o es nulo, y cuando salta una excepción
	 * 					al parsear el id.
	 * @param auth Se usa para obtener el nombre del usuario autenticado que se pasa como parámetro a {@code getUserByUsername}, el cual
	 * 			   obtiene el usuario autenticado que se emplea para invocar {@code getByIdByUser} y obtener el clásico perteneciente al
	 * 			   usuario.
	 * @return Vista del formulario o redirección a {@code classics} en caso de no haber un clásico para editar.
	 */
	@GetMapping({"/new_classic", "/edit_classic/{id}"})
	public String newEditClassic(@PathVariable(required = false) String id, Model model, RedirectAttributes redAttrib, Authentication auth) {
		
		String title = ClassicsTitles.NEW_CLASSIC_TITLE;
		String message = ClassicsMessages.NEW_CLASSIC_MESSAGE;
		Classic classic = new Classic();
		List<Brand> brands = new ArrayList<Brand>();
		
		try {
			
			UserR userR = userService.getUserByUsername(auth.getName());
			
			if (id == null) {
				classic.setUser(userR);
			
			} else {
				
				Long idClassic = Long.parseLong(id);
				
				if (!classicService.exists(idClassic))
					messageAndRedirect(redAttrib);
				
				title = ClassicsTitles.EDIT_CLASSIC_TITLE;
				message = ClassicsMessages.EDIT_CLASSIC_MESSAGE;
				
				
				if (roleChecker.hasRole(ADMIN)) {
					classic = classicService.getById(idClassic);
					classicsMessages.statusBadge(classic, model);
				}
				
				if (roleChecker.hasRole(USER)) {
					classic = classicService.getByIdByUser(userR.getId(), idClassic);
					
					// Es nulo si el clásico no pertenece al usuario que lo solicita.
					if (classic == null) {
						classicsMessages.noClassicMessage(redAttrib);
						return CLASSICS_METHOD;
					}
				}
				
				classic.setApproved(false);
			}
			
			brands = brandService.getAll();
			
		} catch (NumberFormatException e) {
			exceptionAndRedirect(e, redAttrib);
			
		} catch (Exception e) {
			commonMessages.errorMessage(e, model);
		}
		
		model.addAttribute("title", title);
		model.addAttribute("class", "warning").addAttribute("message", message);
		model.addAttribute("classic", classic);
		model.addAttribute("brands", brands);
		
		return FORM_CLASSIC;
	}


	/**
	 * Guarda un clásico en la base de datos. Si se intenta guardar uno con un nombre existente captura la excepción, dirige de
	 * nuevo al formulario y muestra un mensaje en la vista. En brands guarda una lista de marcas para mostrarlas en un desplegable.
	 * 
	 * @param photo Foto del clásico. Si no está vacía y el clásico tiene una asignada, borra la existente con {@code deletePhoto}
	 * 				y asigna una nueva copiándola en el directorio especificado en {@code copyPhoto}.
	 * @param classic Clásico a guardar.
	 * @param result Evalúa si los datos introducidos son correctos. Si son incorrectos dirige de nuevo al formulario de clásico.
	 * 				 para corregirlos.
	 * @param model Guarda el clásico, las marcas, el título y el mensaje para mostrarlos en la vista. El título y el mensaje
	 * 				dependen de si se guarda un clásico nuevo o se edita uno existente.
	 * @return Redirección al método {@code classics} o vista del formulario de clásico en caso de error o excepción.
	 */
	@PostMapping("/save_classic")
	public String saveClassic(@RequestParam(name = "file", required = false) MultipartFile photo, @Valid Classic classic, 
							  BindingResult result, Model model) {
		
		String title = (classic.getId() == 0) ? ClassicsTitles.NEW_CLASSIC_TITLE : ClassicsTitles.EDIT_CLASSIC_TITLE;
		String message = (classic.getId() == 0) ? ClassicsMessages.NEW_CLASSIC_MESSAGE : ClassicsMessages.EDIT_CLASSIC_MESSAGE;
	
		List<Brand> brands = new ArrayList<Brand>();
		
		try {
			brands = brandService.getAll();
			
			if (result.hasErrors()) {
				model.addAttribute("title", title);
				model.addAttribute("class", "info").addAttribute("message", message);
				model.addAttribute("brands", brands);
				
				return FORM_CLASSIC;
			}
			
			if (!photo.isEmpty()) {
				
				// Si existe el clásico, tiene foto y la longitud de esta es mayor a 0 la borra del directorio.
				if (classicService.exists(classic.getId()) && classic.getPhoto() != null && classic.getPhoto().length() > 0)
					photoMethods.deletePhoto("uploads/cars", classic.getPhoto());
				
				// Asigna un nombre de archivo único a la foto del clásico a la hora de copiarla en el directorio.
				String uniqueFilename = photoMethods.copyPhoto("/Users/rober/DESARROLLO/uploads/cars", photo, model);
				
				classic.setPhoto(uniqueFilename);
			}
			
			classicService.save(classic);
			
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			commonMessages.nameExistsMessage(model);
			model.addAttribute("title", title);
			model.addAttribute("brands", brands);
			
			return FORM_CLASSIC;
			
		} catch (Exception e) {
			commonMessages.errorMessage(e, model);
		}
		
		classicsMessages.savedClassicMessage(model);
		model.addAttribute("title", ClassicsTitles.SAVED_CLASSIC_TITLE);
		model.addAttribute("classic", classic);
		
		return CLASSICS_METHOD;
	}
	
	
	/**
	 * Borra un clásico buscándolo por su id. El borrado actúa de forma diferente dependiendo del rol del usuario:<br>
	 * - si es administrador permite borrar el clásico de la base de datos sin restricciones así como su foto del directorio
	 * 	 especificado en {@code deletePhoto}<br>
	 * - si es usuario registrado no borra físicamente el clásico, sino que cambia su visibilidad de tal forma que sólo puede
	 * 	 ser visto por el administrador quien será el que lo borre definitivamente.<br>
	 * 
	 * @param id Id del clásico. Si no lo encuentra o se introduce en la URL un id inexistente redirecciona a {@code classics}
	 * 			 a través de {@code messageAndRedirect</code> y muestra un mensaje en la vista.
	 * @param model Guarda la lista de clásicos, el título y el mensaje para mostrarlos en la vista.
	 * @param redAttrib Guarda un mensaje para mostrarlo en la vista si no se encuentra un clásico o cuando hay una excepción
	 * 					al parsear el id.
	 * @param auth Variable necesaria para obtener el usuario autenticado con el método {@code getUserByUsername} e invocar
	 * 			   el método {@code deleteByUser} para borrar el clásico que pertene a dicho usuario.
	 * @return Vista con la lista de clásicos.
	 */
	@RequestMapping(value = "/delete_classic/{id}", method = {RequestMethod.DELETE, RequestMethod.GET})
	public String deleteClassic(@PathVariable String id, Model model, RedirectAttributes redAttrib, Authentication auth) {
		
		List<Classic> classics = new ArrayList<Classic>();
		
		try {
			Long idClassic = Long.parseLong(id);
			
			if (!classicService.exists(idClassic))
				messageAndRedirect(redAttrib);
			
			if (roleChecker.hasRole(ADMIN)) {
				
				Classic classic = classicService.getById(idClassic);
				
				classicService.delete(idClassic);
				classics = classicService.getAll();
				
				photoMethods.deletePhoto("uploads/cars", classic.getPhoto());
			}
			
			if (roleChecker.hasRole(USER)) {
				UserR userR = userService.getUserByUsername(auth.getName());
				classicService.deleteByUser(userR.getId(), idClassic);
				classics = classicService.getByUser(userR.getId());
			}
			
		} catch (NumberFormatException e) {
			exceptionAndRedirect(e, redAttrib);
			
		} catch (Exception e) {
			commonMessages.errorMessage(e, model);
		}
		
		classicsMessages.deletedClassicMessage(model);
		model.addAttribute("title", ClassicsTitles.DELETED_CLASSIC_TITLE);
		model.addAttribute("classics", classics);
		
		return CLASSICS_LIST;
	}


	/**
	 * Redirecciona a {@code classics} y muestra un mensaje en la
	 * vista cuando no se encuentran resultados.
	 * 
	 * @param redAttrib Contiene el mensaje a mostrar.
	 * @return Redirección a {@code classics}.
	 */
	private String messageAndRedirect(RedirectAttributes redAttrib) {
		classicsMessages.noClassicMessage(redAttrib);
		return CLASSICS_METHOD;
	}
	
	
	/**
	 * Redirecciona a {@code classics} y muestra un mensaje en la vista cuando se
	 * produce una excepción.
	 * 
	 * @param e Excepción producida.
	 * @param redAttrib Contiene el mensaje a mostrar.
	 * @return Redirección a {@code classics}.
	 */
	private String exceptionAndRedirect(Exception e, RedirectAttributes redAttrib) {
		e.printStackTrace();
		commonMessages.numberFormatExceptionMessage(redAttrib);
		return CLASSICS_METHOD;
	}

} // ClassicsBackController
