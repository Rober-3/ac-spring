package my.project.acspring.controllers.frontoffice;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import my.project.acspring.models.entity.UserDto;
import my.project.acspring.models.entity.UserR;
import my.project.acspring.models.interfaces.IUserService;
import my.project.acspring.texts.CommonMessages;
import my.project.acspring.texts.UsersMessages;
import my.project.acspring.texts.UsersTitles;


/**
 * Clase de acceso público que se encarga del registro de nuevos usuarios.
 */
@SessionAttributes("user")
@RequestMapping(value="/ac-spring")
@Controller
public class RegistryController {
	
	@Autowired
	private IUserService userService;
	@Autowired
	private UsersMessages usersMessages;
	@Autowired
	private CommonMessages commonMessages;
	
	private static final String FORM_REGISTRY = "views/frontoffice/form-registry";
	private static final String LOGIN = "views/frontoffice/login";
	
	
	@GetMapping("/registry")
	public String register(Model model) {
		
		usersMessages.newUserMessage(model);
		model.addAttribute("title", UsersTitles.NEW_USER_TITLE);
		// Crea una instancia de UserDto, la clase que sirve para recoger la información del registro.
		model.addAttribute("user", new UserDto());
		
		return FORM_REGISTRY;
	}

	
	@PostMapping("/new_user")
	public String registerUser(@ModelAttribute("user") @Valid UserDto userDto, BindingResult result, Model model) throws Exception {

		UserR registeredUser = new UserR();
		
		if (result.hasErrors()) {
			model.addAttribute("title", UsersTitles.NEW_USER_TITLE);
			usersMessages.newUserMessage(model);
			
			return FORM_REGISTRY;
		}

		try {
			registeredUser = userService.register(userDto);
			
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			commonMessages.nameExistsMessage(model);
			
			return FORM_REGISTRY;

		} catch (Exception e) {
			commonMessages.errorMessage(e, model);
		}
	
		
		usersMessages.savedUserMessage(model);
		model.addAttribute("title", UsersTitles.SAVED_USER_TITLE);
		model.addAttribute("user", registeredUser);
		
		return LOGIN;
	}

} // RegisterController
