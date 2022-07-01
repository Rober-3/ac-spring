package my.project.acspring.controllers.frontoffice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import my.project.acspring.models.entity.Brand;
import my.project.acspring.models.entity.Classic;
import my.project.acspring.models.interfaces.IBrandService;
import my.project.acspring.models.interfaces.IClassicService;
import my.project.acspring.texts.CommonMessages;


/**
 * Muestra la página de inicio pública.
 */
@RequestMapping("/ac-spring")
@Controller
public class IndexController {

	@Autowired
	private IClassicService classicService;
	@Autowired
	private IBrandService brandService;
	@Autowired
	private CommonMessages commonMessages;


	/**
	 * Muestra en un carrusel de la página principal del frontoffice
	 * tres clásicos obtenidos aleatoriamente. Asimismo obtiene las
	 * marcas para mostrar su nombre en un desplegable de la barra
	 * de navegación de la misma página.
	 * 
	 * @param model Guarda los clásicos, las marcas y el título para
	 * 				mostrarlos en la vista.
	 * @return Vista de la página principal del frontoffice.
	 */
	@GetMapping({ "/index", "/home", "/", "" })
	public String index(Model model) {

		List<Classic> classics = new ArrayList<Classic>();
		List<Brand> brands = new ArrayList<Brand>();
		
		try {
			classics = classicService.getThree();
			brands = brandService.getAllWithClassicsApproved();
			
		} catch (Exception e) {
			commonMessages.errorMessage(e, model);
		}
		
		model.addAttribute("title", "AC Spring - Página principal");
		model.addAttribute("classics", classics);
		model.addAttribute("brands", brands);

		return "index";
	}
	
}
