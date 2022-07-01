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

import my.project.acspring.models.entity.Country;
import my.project.acspring.models.entity.Origin;
import my.project.acspring.models.interfaces.ICountryService;
import my.project.acspring.models.interfaces.IOriginService;
import my.project.acspring.texts.CommonMessages;
import my.project.acspring.texts.CountriesMessages;
import my.project.acspring.texts.CountriesTitles;


/**
 * Gestiona los países en el backoffice de acceso sólo para el administrador.
 */
@SessionAttributes("country")
@RequestMapping("/ac-spring/backoffice")
@Secured("ROLE_ADMIN")
@Controller
public class CountriesController {

	@Autowired
	private ICountryService countryService;
	@Autowired
	private IOriginService originService;
	@Autowired
	private CountriesMessages countriesMessages;
	@Autowired
	private CountriesTitles countriesTitles;
	@Autowired
	private CommonMessages commonMessages;

	private static final String COUNTRIES_LIST = "views/backoffice/list";
	private static final String COUNTRY_DETAILS = "views/backoffice/details";
	private static final String FORM_COUNTRY = "views/backoffice/form";
	private static final String COUNTRIES_METHOD = "redirect:/ac-spring/backoffice/countries";


	/**
	 * Muestra los países que tienen una lista de marcas y los vacíos. Si no hay países en la base
	 * de datos muestra un mensaje en la vista.
	 * 
	 * @param empty Si es verdadero muestra los países que tienen marcas, si es falso los vacíos
	 * 		  		y si es nulo todos.
	 * @param model Guarda la lista de países, el título y el mensaje para mostrarlos en la vista.
	 *  			El título depende de la lista de países (todos, con marcas o vacíos).
	 * @return Vista con la lista de países.
	 */
	@GetMapping({"/countries", "/empty_countries/{empty}"})
	public String countries(@PathVariable(required = false) Boolean empty, Model model) {
		
		String title = CountriesTitles.COUNTRIES_LIST_TITLE;
		List<Country> countries = new ArrayList<Country>();
		
		try {
			countries = countryService.getAll();
			
			if (empty != null) {
				title = CountriesTitles.COUNTRIES_WITH_BRANDS_TITLE;
				countries = countryService.getAllEmpty(false);
				
				if (empty) {
					title = CountriesTitles.EMPTY_COUNTRIES_TITLE;
					countries = countryService.getAllEmpty(true);
				}
			}
			
			if (countries.isEmpty())
				countriesMessages.noCountriesMessage(model);
			
		} catch (Exception e) {
			commonMessages.errorMessage(e, model);
		}
		
		model.addAttribute("title", title);
		model.addAttribute("countries", countries);
		
		return COUNTRIES_LIST;
	}


	/**
	 * Muestra un país buscándolo por su id. Si no lo encuentra o se introduce en la URL un id que no
	 * no existe, redirecciona a {@code countries} a través de {@code messageAndRedirect} y muestra un
	 * mensaje en la vista. Si se introduce en la URL un id no entero captura la excepción, redirecciona
	 * a ccountries} a través de {@code exceptionAndRedirect} y muestra un mensaje en la vista.
	 * 
	 * @param id Id del país. 
	 * @param model Guarda el país, el título y el mensaje para mostrarlos en la vista.
	 * @param redAttrib Guarda un mensaje para mostrarlo en la vista cuando no encuentra un país o es
	 * 		  nulo, o cuando se produce una excepción al parsear el id.
	 * @return Vista con el país cuyo id coincide con el buscado.
	 */
	@GetMapping("/country/{id}")
	public String country(@PathVariable String id, Model model, RedirectAttributes redAttrib) {
		
		Country country = new Country();

		try {
			Long idCountry = Long.parseLong(id);

			if (!countryService.exists(idCountry))
				messageAndRedirect(redAttrib);

			country = countryService.getById(idCountry);
			
			if (country == null)
				messageAndRedirect(redAttrib);
			
		} catch (NumberFormatException e) {
			exceptionAndRedirect(e, redAttrib);
		
		} catch (Exception e) {
			commonMessages.errorMessage(e, model);
		}

		countriesTitles.countryTitle(model, country);
		model.addAttribute("country", country);

		return COUNTRY_DETAILS;
	}


	/**
	 * Muestra el formulario para introducir un país nuevo o para editar uno existente. Para editar un país lo busca por
	 * su id, y si no lo encuentra o se introduce en la URL un id inexistente redirecciona a {@code countries} a través
	 * de {@code messageAndRedirect} y muestra un mensaje en la vista. Si se introduce en la URL un id no entero captura
	 * la excpeción, redirecciona a {@code countries} através de {@code exceptionAndRedirect} y muestra un mensaje en la
	 * a vista. En origins se guarda una lista de orígenes para mostrarlos en un desplegable.
	 * 
	 * @param id Id del país. Si no es nulo se muestran los datos del país en el formulario. 
	 * @param model Guarda el país, el título y el mensaje para mostrarlos en la vista. El título y el mensaje dependen
	 * 				de si se añade un país nuevo o se edita uno existente.
	 * @param redAttrib Guarda un mensaje para mostrarlo en la vista si no encuentra un país o cuando salta una excepción
	 * 					al parsear el id.
	 * @return Vista del formulario, vacío si se introduce un país nuevo, con datos si se edita uno existente.
	 */
	@GetMapping({"new_country", "/edit_country/{id}"})
	public String newEditCountry(@PathVariable(required = false) String id, Model model, RedirectAttributes redAttrib) {
		
		String title = CountriesTitles.NEW_COUNTRY_TITLE;
		String message = CountriesMessages.NEW_COUNTRY_MESSAGE;
		Country country = new Country();
		List<Origin> origins = new ArrayList<Origin>();
		
		try {
			if (id != null) {
				Long idCountry = Long.parseLong(id);
				
				if (!countryService.exists(idCountry))
					messageAndRedirect(redAttrib);
				
				title = CountriesTitles.EDIT_COUNTRY_TITLE;
				message = CountriesMessages.EDIT_COUNTRY_MESSAGE;
				country = countryService.getById(idCountry);
			}
			
			origins = originService.getAll();
			
		} catch (NumberFormatException e) {
			exceptionAndRedirect(e, redAttrib);
			
		} catch (Exception e) {
			commonMessages.errorMessage(e, model);
		}
		
		model.addAttribute("title", title);
		model.addAttribute("class", "warning").addAttribute("message", message);
		model.addAttribute("country", country);
		model.addAttribute("origins", origins);
		
		return FORM_COUNTRY;
	}


	/**
	 * Guarda un país en la base de datos. Si se intenta guardar uno con un nombre existente
	 * captura la excepción, dirige de nuevo al formulario de país y muestra un mensaje en la
	 * vista. En origins guarda una lista de orígenes para mostrarlos en un desplegable.
	 * 
	 * @param country País a guardar.
	 * @param result Evalúa si los datos introducidos son correctos. Si son incorrectos dirige
	 * 				 de nuevo al formulario de país para corregirlos.
	 * @param model Guarda el país, los orígenes, el título y el mensaje para mostrarlos en la
	 * 				vista. El título y el mensaje dependen de si se guarda un país nuevo o uno
	 * 				existente.
	 * @return Vista con el país guardado o vista del formulario de marca en caso de error o
	 * 		   excepción.
	 */
	@PostMapping("/save_country")
	public String saveCountry(@Valid Country country, BindingResult result, Model model) {

		List<Origin> origins = new ArrayList<Origin>();
		
		try {
			origins = originService.getAll();
			
			if (result.hasErrors()) {

				String title = (country.getId() == 0) ? CountriesTitles.NEW_COUNTRY_TITLE 
													  : CountriesTitles.EDIT_COUNTRY_TITLE;
				String message = (country.getId() == 0) ? CountriesMessages.NEW_COUNTRY_MESSAGE
														: CountriesMessages.EDIT_COUNTRY_MESSAGE;

				model.addAttribute("title", title);
				model.addAttribute("class", "info").addAttribute("message", message);
				model.addAttribute("origins", origins);

				return FORM_COUNTRY;

			}
			
			country = countryService.save(country);

		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			commonMessages.nameExistsMessage(model);
			model.addAttribute("title", CountriesTitles.NEW_COUNTRY_TITLE);
			model.addAttribute("origins", origins);

			return FORM_COUNTRY;

		} catch (Exception e) {
			commonMessages.errorMessage(e, model);
		}

		countriesMessages.savedCountryMessage(model);
		model.addAttribute("title", CountriesTitles.SAVED_COUNTRY_TITLE);
		model.addAttribute("country", country);

		return COUNTRY_DETAILS;
	}


	/**
	 * Borra un país de la base de datos buscándolo por su id. Si no lo encuentra o se introduce en la URL
	 * un id inexistente redirecciona a {@code countries} a través de {@code messageAndRedirect} y muestra
	 * un mensaje en la vista. Si se introduce en la URL un id no entero captura la excepción, redirecciona
	 * a {@code countries</code> a través de {@code exceptionAndRedirect} y muestra un mensaje en la vista.
	 * Si el país tiene marcas no permite el borrado, redirecciona a {@code countries} y muestra un mensaje
	 * en la vista.
	 * 
	 * @param id Id del país.
	 * @param model Guarda la lista de países, el título y el mensaje para mostrarlos en la vista.
	 * @param redAttrib Guarda un mensaje para mostrarlo en la vista si no se encuentra un país o cuando se
	 * 					produce una excepción al parsear el id.
	 * @return Lista de países o redirección a {@code countries} en caso de no borrar una marca.
	 */
	@RequestMapping(value = "/delete_country/{id}", method = {RequestMethod.DELETE, RequestMethod.GET})
	public String deleteCountry(@PathVariable String id, Model model, RedirectAttributes redAttrib) {

		List<Country> countries = new ArrayList<Country>();
		
		try {
			Long idCountry = Long.parseLong(id);

			if (!countryService.exists(idCountry))
				messageAndRedirect(redAttrib);

			Country country = countryService.getById(idCountry);

			if (!country.getBrands().isEmpty()) {
				countriesMessages.notDeletedCountryMessage(redAttrib);
				return COUNTRIES_METHOD;
			}

			countryService.delete(idCountry);
			countries = countryService.getAll();
			
		} catch (NumberFormatException e) {
			exceptionAndRedirect(e, redAttrib);
			
		} catch (Exception e) {
			commonMessages.errorMessage(e, model);
		}

		countriesMessages.deletedCountryMessage(model);
		model.addAttribute("title", CountriesTitles.DELETED_COUNTRY_TITLE);
		model.addAttribute("countries", countries);

		return COUNTRIES_LIST;
	}


	/**
	 * Redirecciona a {@code countries} y muestra un mensaje en la vista
	 * uando no se encuentran resultados.
	 * 
	 * @param redAttrib Contiene el mensaje a mostrar.
	 * @return Redirección al método {@code countries}.
	 */
	private String messageAndRedirect(RedirectAttributes redAttrib) {
		countriesMessages.noCountryMessage(redAttrib);
		return COUNTRIES_METHOD;
	}
	
	/**
	 * Redirecciona a {@code countries} y muestra un mensaje en la vista cuando se
	 * produce una excepción.
	 * 
	 * @param e Excepción producida.
	 * @param redAttrib Contiene el mensaje a mostrar.
	 * @return Redirección al método {@code countries}.
	 */
	private String exceptionAndRedirect(Exception e, RedirectAttributes redAttrib) {
		e.printStackTrace();
		commonMessages.numberFormatExceptionMessage(redAttrib);
		return COUNTRIES_METHOD;
	}

} // CountriesController
