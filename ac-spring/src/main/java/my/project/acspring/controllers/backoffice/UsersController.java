package my.project.acspring.controllers.backoffice;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import my.project.acspring.models.entity.UserR;
import my.project.acspring.models.interfaces.IUserService;
import my.project.acspring.texts.CommonMessages;
import my.project.acspring.texts.UsersMessages;
import my.project.acspring.texts.UsersTitles;
import my.project.acspring.utils.backoffice.LoggedInUserDetails;
import my.project.acspring.utils.backoffice.RoleChecker;
import my.project.acspring.utils.frontoffice.PhotoMethods;


/**
 * Clase que gestiona los usuarios en el backoffice a la que tienen acceso el administrador y los usuarios registrados.
 */
@SessionAttributes("user")
@RequestMapping("/ac-spring/backoffice")
@Controller
public class UsersController {
	
	@Autowired
	private IUserService userService;
	@Autowired
	private RoleChecker roleChecker;
	@Autowired
	private PhotoMethods photoMethods;
	@Autowired
	private UsersMessages usersMessages;
	@Autowired
	private CommonMessages commonMessages;	
	
	private static final String USERS_LIST = "views/backoffice/list";
	private static final String USER_DETAILS = "views/backoffice/details";
	private static final String FORM_USER = "views/backoffice/form";
	private static final String USERS_METHOD = "redirect:/ac-spring/backoffice/users";
	private static final String INDEX = "redirect:/ac-spring/backoffice/index";


	/**
	 * Muestra al administrador todos los usuarios registrados.
	 * 
	 * @param model Guarda la lista de usuarios y el título para mostrarlos en la vista.
	 * @return Vista con la lista de usuarios.
	 */
	@Secured("ROLE_ADMIN")
	@GetMapping("/users")
	public String users(Model model) {
		
		List<UserR> userRs = new ArrayList<UserR>();
		
		try {
			userRs = userService.getAll();
			
			if (userRs.isEmpty())
				usersMessages.noUsersMessage(model);
			
		} catch (Exception e) {
			commonMessages.errorMessage(e, model);
		}
		
		model.addAttribute("title", UsersTitles.USERS_LIST_TITLE);
		model.addAttribute("users", userRs);
		
		return USERS_LIST;
	}


	/**
	 * Muestra la página de perfil del usuario que ha iniciado sesión identificándolo por su id. Si el id pasado como
	 * parámetro coincide con el id del usuario que ha iniciado sesión este último puede acceder a su perfil, en caso
	 * contrario redirecciona a {@code index} y muestra un mensaje en la vista.
	 *
	 * @param id Id del usuario.
	 * @param userdetails Obtiene el id del usuario que ha iniciado sesión.
	 * @param model Guarda el usuario y el título para mostrarlos en la vista.
	 * @param redAttrib Guarda un mensaje para mostrarlo en la vista cuando se produce una excepción al parsear el id
	 * 					o cuando un usuario intenta acceder al perfil de otro.
	 * @return Vista con el perfil del usuario o redirección a {@code index} en caso de acceso no autorizado a otros
	 * 		   perfiles.
	 */
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/user/{id}")
	public String user(@PathVariable String id, @AuthenticationPrincipal LoggedInUserDetails userdetails, Model model,
					   RedirectAttributes redAttrib ) {
		
		UserR userR = new UserR();
		
		try {
			Long idUser = Long.parseLong(id);
			
			if (idUser == userdetails.getId() || roleChecker.hasRole("ROLE_ADMIN")) {
				userR = userService.getById(idUser);
				model.addAttribute("title", UsersTitles.MY_PROFILE_TITLE);
				model.addAttribute("user", userR);
				return USER_DETAILS;
			}
		
		} catch (NumberFormatException e) {
			exceptionAndRedirect(e, redAttrib);
			
		} catch (Exception e) {
			commonMessages.errorMessage(e, model);
		}
		
		usersMessages.forbiddenAccessMessage(redAttrib);
		return INDEX;
	}
	
	
	/**
	 * Edita el perfil del usuario que ha iniciado sesión identificándolo por su id. Si el id pasado como parámetro coincide
	 * con el id del usuario que ha iniciado sesión éste puede acceder a su perfil para modificarlo y muestra un mensaje en
	 * la vista, en caso contrario redirecciona a {@code index} y muestra un mensaje en la vista.
	 * 
	 * @param id Id del usuario.
	 * @param userDetails Obtiene el id del usuario que ha iniciado sesión
	 * @param model Guarda el usuario, el título y el mensaje para mostrarlos en la vista.
	 * @param redAttrib Guarda un mensaje paa mostrarlo en la vista cuando se produce una excepción al parsear el id o cuando
	 * 					un usuario intenta acceder al perfil de otro.
	 * @return Vista del formulario con los datos del usuario o redirección a {@code index} en caso de acceso no autorizado a
	 * 		   otros perfiles.
	 */
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/edit_user/{id}")
	public String editUser(@PathVariable String id,  @AuthenticationPrincipal LoggedInUserDetails userDetails, Model model, 
							RedirectAttributes redAttrib) {
		
		UserR userR = new UserR();
		
		try {
			Long idUser = Long.parseLong(id);
			
			if (idUser == userDetails.getId()) {
				userR = userService.getById(idUser);
				usersMessages.editProfileMessage(model);
				model.addAttribute("title", UsersTitles.EDIT_PROFILE_TITLE);
				model.addAttribute("user", userR);
				return FORM_USER;
			}
			
		} catch (NumberFormatException e) {
			exceptionAndRedirect(e, redAttrib);
			
		} catch (Exception e) {
			commonMessages.errorMessage(e, model);
		}
		
		usersMessages.forbiddenAccessMessage(redAttrib);
		return INDEX;
	}

	
	/**
	 * Actualiza en la base de datos los datos del usuario que ha iniciado sesión.
	 * 
	 * @param photo Foto del usuario. Si no está vacía y el usuario tiene una asignada borra la existente con {@code 
	 * 				deletePhoto} y asigna una nueva copiándola en el directorio especificado en {@code copyPhoto}.
	 * @param userR
	 * @param result Evalúa si los datos introducidos son correctos. Si son incorrectos dirige de nuevo al formulario
	 * 				 de usuario para corregirlos.
	 * @param model Guarda el usuario, el título y el mensaje para mostrarlos en la vista. El título y el mensaje
	 * 				dependen de si ha guardado un usuario nuevo o edita uno existente.
	 * @return Vista con el perfil del usuario o vista del formulario de usuario en caso de error o excepción.
	 */
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@PostMapping("/save_user")
	public String updateUser(@RequestParam(name = "file", required = false) MultipartFile photo,
								@Valid @ModelAttribute("user") UserR userR, BindingResult result , Model model) {
		
		try {

			if (result.hasErrors()) {
				model.addAttribute("title", UsersTitles.EDIT_PROFILE_TITLE);
				usersMessages.editProfileMessage(model);
				return FORM_USER;
			}
			
			if (!photo.isEmpty()) {
				
				if (userService.exists(userR.getId()) && userR.getPhoto() != null && userR.getPhoto().length() > 0)
					photoMethods.deletePhoto("Users/rober/DESARROLLO/uploads/users", userR.getPhoto());
				
				// Asigna un nombre de archivo único a la foto del usuario a la hora de copiarla en el directorio.
				String uniqueFilename = photoMethods.copyPhoto("/Users/rober/DESARROLLO/uploads/users", photo, model);
				
				userR.setPhoto(uniqueFilename);
			}

			userR = userService.save(userR);

		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			commonMessages.nameExistsMessage(model);
			model.addAttribute("title", UsersTitles.EDIT_PROFILE_TITLE);
			return FORM_USER;
			
		} catch (Exception e) {
			commonMessages.errorMessage(e, model);
		}

		usersMessages.savedProfileMessage(model);
		model.addAttribute("title", UsersTitles.SAVED_USER_TITLE);
		model.addAttribute("user", userR);

		return USER_DETAILS;
	}


	/**
	 * Redirecciona a {@code users} y muestra un mensaje en la vista cuando se produce
	 * una excepción.
	 * 
	 * @param e Excepción producida.
	 * @param redAttrib Contiene el mensaje a mostrar.
	 * @return Redirección a {@code users}.
	 */
	private String exceptionAndRedirect(Exception e, RedirectAttributes redAttrib) {
		e.printStackTrace();
		commonMessages.numberFormatExceptionMessage(redAttrib);
		return USERS_METHOD;
	}
	
} // UsersController
