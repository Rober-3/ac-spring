package my.project.acspring.controllers.frontoffice;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@SessionAttributes("user")
@RequestMapping(value="/ac-spring")
@Controller
public class LoginController {
	
	@GetMapping("/login")
	public String login(@RequestParam(value = "error", required = false) String error,
						@RequestParam(value = "logout", required = false) String logout,
						Model model, Principal principal, RedirectAttributes redAttrib) {
		
		if (principal != null) {
			redAttrib.addFlashAttribute("class", "info").addFlashAttribute("message", "Ya has iniciado sesión.");
			return "redirect:/ac-spring/index";
		}
		
		if (error != null)
			model.addAttribute("class", "danger").addAttribute("message", "Usuario y/o contraseña incorrecto(s), inténtalo de nuevo.");
		
		if (logout != null)
			model.addAttribute("class", "success").addAttribute("message", "Sesión cerrada correctamente.");
		
		model.addAttribute("title", "Inicio de sesión");
		
		return "views/frontoffice/login";
	}
	
}
