package my.project.acspring.controllers.frontoffice;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionsHandlerController {
	
	@ExceptionHandler(Exception.class)
	public String genericError(Exception e, Model model) {
		
		e.printStackTrace();
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		model.addAttribute("message", "Ha habido un error.");
		model.addAttribute("cause", "Causa: " + e.toString());
		
		return "error/500";
	}
}
