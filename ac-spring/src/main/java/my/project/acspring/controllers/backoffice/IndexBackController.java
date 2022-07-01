package my.project.acspring.controllers.backoffice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import my.project.acspring.models.interfaces.IBrandService;
import my.project.acspring.models.interfaces.IClassicService;
import my.project.acspring.models.interfaces.ICountryService;
import my.project.acspring.models.interfaces.IOriginService;
import my.project.acspring.models.interfaces.IUserService;
import my.project.acspring.texts.CommonMessages;
import my.project.acspring.utils.backoffice.UserIdChecker;

/**
 * Muestra la página de inicio del backoffice de acceso sólo para el administrador y los usuarios registrados.
 */
@Secured({"ROLE_ADMIN", "ROLE_USER"})
@RequestMapping("/ac-spring/backoffice")
@Controller
public class IndexBackController {
	
	@Autowired
	private IClassicService classicService;
	@Autowired
	private IBrandService brandService;
	@Autowired
	private ICountryService countryService;
	@Autowired
	private IOriginService originService;
	@Autowired
	private IUserService userService;
	@Autowired
	private UserIdChecker idChecker;
	@Autowired
	private CommonMessages commonMessages;
	
	
	/**
	 * Muestra el total de clásicos en función del rol del usuario:<br><br>
	 * 
	 * - si es administrador muestra todos, los aprobados, los pendientes de
	 * 	 pendientes de aprobación y los borrados por los usuarios<br>
	 * - si es usuario registrado a través de su id muestra todos los que le
	 *   pertenecen, sus aprobados y sus pendientes.<br><br>
	 * 
	 * Los roles se obtienen invocando {@code getAuthorities} en el objeto {@code
	 * Authentication} y se guardan en {@code roles} para posteriormente invocar
	 * {@code contains} y obtener el rol del usuario.
	 * 
	 * @param model Guarda la lista de clásicos para mostrarlos en la vista.
	 * @param auth Contiene atributos del usuario autenticado.
	 * @return Página de inicio con los clásicos.
	 */
	@GetMapping({ "/index", "/home", "/", "" })
	public String index(Model model, Authentication auth) {
		
		Collection<? extends GrantedAuthority> roles = auth.getAuthorities();
		
		if (roles.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
			
			List<Long> total1 = new ArrayList<Long>();
			List<Long> total2 = new ArrayList<Long>();
			List<Long> total3 = new ArrayList<Long>();
			List<Long> total4 = new ArrayList<Long>();
			Long total5 = (long) 0;

			try {
				total1.add(classicService.count());
				total1.add(classicService.countApproved(true));
				total1.add(classicService.countApproved(false));
				total1.add(classicService.countDeleted());

				total2.add(brandService.count());
				total2.add(brandService.countEmpty(false));
				total2.add(brandService.countEmpty(true));

				total3.add(countryService.countCountries());
				total3.add(countryService.countEmpty(false));
				total3.add(countryService.countEmpty(true));

				total4.add(originService.count());
				total4.add(originService.countEmpty(false));
				total4.add(originService.countEmpty(true));
				
				total5 = userService.count();
				
			} catch (Exception e) {
				commonMessages.errorMessage(e, model);
			}

			model.addAttribute("total1", total1)
				 .addAttribute("total2", total2)
				 .addAttribute("total3", total3)
				 .addAttribute("total4", total4)
				 .addAttribute("total5", total5);
		}
		
		if (roles.contains(new SimpleGrantedAuthority("ROLE_USER"))) {
			
			List<Long> total = new ArrayList<Long>();
			
			try {
				Long idUser = idChecker.idCheck(auth);

				total.add(classicService.countUserClassics(idUser));
				total.add(classicService.countUserClassicsApproved(idUser, true));
				total.add(classicService.countUserClassicsApproved(idUser, false));
				
			} catch (Exception e) {
				commonMessages.errorMessage(e, model);
			}

			model.addAttribute("total", total);
		}
		
		return "views/backoffice/index";
		
	}

} // IndexBackController
