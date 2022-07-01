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

import my.project.acspring.models.entity.Brand;
import my.project.acspring.models.entity.Country;
import my.project.acspring.models.interfaces.IBrandService;
import my.project.acspring.models.interfaces.ICountryService;
import my.project.acspring.texts.BrandsMessages;
import my.project.acspring.texts.BrandsTitles;
import my.project.acspring.texts.CommonMessages;


/**
 * Gestiona las marcas en el backoffice de acceso sólo para el administrador.
 */
@SessionAttributes("brand")
@RequestMapping("/ac-spring/backoffice")
@Secured("ROLE_ADMIN")
@Controller
public class BrandsBackController {

	@Autowired
	private IBrandService brandService;
	@Autowired
	private ICountryService countryService;
	@Autowired
	private BrandsMessages brandsMessages;
	@Autowired
	private BrandsTitles brandsTitles;
	@Autowired
	private CommonMessages commonMessages;

	private static final String BRANDS_LIST = "views/backoffice/list";
	private static final String BRAND_DETAILS = "views/backoffice/details";
	private static final String FORM_BRAND = "views/backoffice/form";
	private static final String BRANDS_METHOD = "redirect:/ac-spring/backoffice/brands";


	/**
	 * Muestra las marcas que tienen una lista de clásicos y las vacías. Si no existen marcas en la
	 * base de datos muestra un mensaje en la vista.
	 * 
	 * @param empty Si es verdadero muestra las marcas que tienen clásicos, si es falso las vacías
	 * 		  		y si es nulo todas.
	 * @param model Guarda la lista de marcas, el título y el mensaje para mostrarlos en la vista.
	 * 				El título depende de la lista de marcas (todas, con clásicos o vacías).
	 * @return Vista con la lista de marcas.
	 */
	@GetMapping({"/brands", "/empty_brands/{empty}"})
	public String brands(@PathVariable(required = false) Boolean empty, Model model) {
		
		String title = BrandsTitles.BRANDS_LIST_TITLE;
		List<Brand> brands = new ArrayList<Brand>();
		
		try {
			brands = brandService.getAll();
			
			if (empty != null) {
				
				if (empty) {
					title = BrandsTitles.EMPTY_BRANDS_TITLE;
					brands = brandService.getAllEmpty(true);
					
				} else {
					title = BrandsTitles.BRANDS_WITH_CLASSICS_TITLE;
					brands = brandService.getAllEmpty(false);
				}
			}
			
			if (brands.isEmpty())
				brandsMessages.noBrandsMessage(model);
			
		} catch (Exception e) {
			commonMessages.errorMessage(e, model);
		}
		
		model.addAttribute("title", title);
		model.addAttribute("brands", brands);
		
		return BRANDS_LIST;
	}


	/**
	 * Muestra una marca buscándola por su id. Si no la encuentra o se introduce en la URL un
	 * id inexistente redirecciona a {@code brands} a través de {@code messageAndRedirect} y
	 * muestra un mensaje en la vista. Si se introduce en la URL un id no entero captura la
	 * excepción, redirecciona a {@code brands} a través de {@code exceptionAndRedirect} y
	 * muestra un mensaje en la vista.
	 * 
	 * @param id Id de la marca. 
	 * @param model Guarda la marca, el título y el mensaje para mostrarlos en la vista.
	 * @param redAttrib Guarda un mensaje para mostrarlo en la vista si no encuentra una marca
	 * 					o es nula, y cuando se produce una excepción al parsear el id.
	 * @return Vista con la marca cuyo id coincide con el buscado.
	 */
	@GetMapping("/brand/{id}")
	public String brand(@PathVariable String id, Model model, RedirectAttributes redAttrib) {

		Brand brand = new Brand();
		
		try {
			Long idBrand = Long.parseLong(id);

			if (!brandService.exists(idBrand))
				messageAndRedirect(redAttrib);

			brand = brandService.getById(idBrand);
			
			if (brand == null)
				messageAndRedirect(redAttrib);
			
		} catch (NumberFormatException e) {
			exceptionAndRedirect(e, redAttrib);
			
		} catch (Exception e) {
			commonMessages.errorMessage(e, model);
		}

		brandsTitles.brandTitle(model, brand);
		model.addAttribute("brand", brand);

		return BRAND_DETAILS;
	}


	/**
	 * Muestra el formulario para introducir una marca nueva o para editar una existente. Para editar una marca la busca
	 * por su id, y si no la encuentra o se introduce en la URL un id inexistente redirecciona a {@code brands} a través
	 * de {@code messageAndRedirect} y muestra un mensaje en la vista. Si se introduce en la URL un id no entero captura
	 * la excepción, redirecciona a {@code brands} a través de {@code exceptionAndRedirect} y muestra un mensaje en la
	 * vista. En countries guarda una lista de países para mostrarlos en un desplegable.
	 * 
	 * @param id Id de la marca. Si no es nulo se muestran los datos de la marca en el formulario.
	 * @param model Guarda la marca, el título y el mensaje para mostrarlos en la vista. El título y el mensaje dependen
	 * 				de si se añade una marca nueva o se edita una existente.
	 * @param redAttrib Guarda un mensaje para mostrarlo en la vista si no encuentra una marca o cuando se produce una
	 * 					excepción al parsear el id.
	 * @return Vista del formulario, vacío si se introduce una marca nueva, con datos si se edita una existente.
	 */
	@GetMapping({"/new_brand", "/edit_brand/{id}"})
	public String newEditBrand(@PathVariable(required = false) String id, Model model, RedirectAttributes redAttrib) {
		
		String title = BrandsTitles.NEW_BRAND_TITLE;
		String message = BrandsMessages.NEW_BRAND_MESSAGE;
		Brand brand = new Brand();
		List<Country> countries = new ArrayList<Country>();
		
		try {
			
			if (id != null) {
				Long idBrand = Long.parseLong(id);
				
				if (!brandService.exists(idBrand))
					messageAndRedirect(redAttrib);
				
				title = BrandsTitles.EDIT_BRAND_TITLE;
				message = BrandsMessages.EDIT_BRAND_MESSAGE;
				brand = brandService.getById(idBrand);
			}
			
			countries = countryService.getAll();
			
		} catch (NumberFormatException e) {
			exceptionAndRedirect(e, redAttrib);
			
		} catch (Exception e) {
			commonMessages.errorMessage(e, model);
		}
		
		model.addAttribute("title", title);
		model.addAttribute("class", "warning").addAttribute("message", message);
		model.addAttribute("brand", brand);
		model.addAttribute("countries", countries);
		
		return FORM_BRAND;
	}


	/**
	 * Guarda una marca en la base de datos. Si se intenta guardar una con un nombre existente
	 * captura la excepción, dirige de nuevo al formulario de marca y muestra un mensaje en la
	 * vista. En countries guarda una lista de países para mostrarlos en un desplegable.
	 * 
	 * @param brand Marca a guardar.
	 * @param result Evalúa si los datos introducidos son correctos. Si son incorrectos dirige
	 * 				 de nuevo al formulario de marca para corregirlos.
	 * @param model Guarda la marca, los países, el título y el mensaje para mostrarlos en la
	 * 				vista. El título y el mensaje dependen de si se guarda una marca nueva o
	 * 				una existente.
	 * @return Vista con la marca guardada o vista del formulario de marca en caso de error o
	 * 	       excepción.
	 */
	@PostMapping("/save_brand")
	public String saveBrand(@Valid Brand brand, BindingResult result, Model model) {

		List<Country> countries = new ArrayList<Country>();
		
		try {
			countries = countryService.getAll();

			if (result.hasErrors()) {

				String title = (brand.getId() == 0) ? BrandsTitles.NEW_BRAND_TITLE : BrandsTitles.EDIT_BRAND_TITLE;
				String message = (brand.getId() == 0) ? BrandsMessages.NEW_BRAND_MESSAGE : BrandsMessages.EDIT_BRAND_MESSAGE;

				model.addAttribute("title", title);
				model.addAttribute("class", "info").addAttribute("message", message);
				model.addAttribute("countries", countries);

				return FORM_BRAND;
			}

			brand = brandService.save(brand);

		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			commonMessages.nameExistsMessage(model);
			model.addAttribute("title", BrandsTitles.NEW_BRAND_TITLE);
			model.addAttribute("countries", countries);
			
			return FORM_BRAND;

		} catch (Exception e) {
			commonMessages.errorMessage(e, model);
		}

		brandsMessages.savedBrandMessage(model);
		model.addAttribute("title", BrandsTitles.SAVED_BRAND_TITLE);
		model.addAttribute("brand", brand);

		return BRAND_DETAILS;
	}

	
	/**
	 * Borra una marca de la base de datos buscándola por su id. Si no la encuentra o se introduce en
	 * la URL un id inexistente redirecciona a {@code brands} a través de {@code messageAndRedirect} y
	 * muestra un mensaje en la vista. Si se introduce en la URL un id no entero captura la excepción,
	 * redirecciona a {@code brands} a través de {@code exceptionAndRedirect} y muestra un mensaje en
	 * la vista. Si la marca tiene clásicos no permite el borrado, redirecciona a {@code brands} y
	 * muestra un mensaje en la vista.	 
	 * 
	 * @param id Id de la marca. 
	 * @param model Guarda la lista de marcas, el título y el mensaje para mostrarlos en la vista.
	 * @param redAttrib Guarda un mensaje para mostrarlo en la vista si no se encuentra una marca o
	 * 					cuando se produce una excepción al parsear el id.
	 * @return Vista con la lista de marcas o redirección a {@code brands} en caso de no borrar una marca.
	 */
	@RequestMapping(value = "/delete_brand/{id}", method = {RequestMethod.DELETE, RequestMethod.GET})
	public String deleteBrand(@PathVariable String id, Model model, RedirectAttributes redAttrib) {

		List<Brand> brands = new ArrayList<Brand>();
		
		try {
			Long idBrand = Long.parseLong(id);

			if (!brandService.exists(idBrand))
				messageAndRedirect(redAttrib);

			Brand brand = brandService.getById(idBrand);

			if (!brand.getClassics().isEmpty()) {
				brandsMessages.notDeletedBrandMessage(redAttrib);
				return BRANDS_METHOD;
			}

			brandService.delete(idBrand);
			brands = brandService.getAll();
			
		} catch (NumberFormatException e) {
			exceptionAndRedirect(e, redAttrib);
			
		} catch (Exception e) {
			commonMessages.errorMessage(e, model);
		}

		brandsMessages.deletedBrandMessage(model);
		model.addAttribute("title", BrandsTitles.BRANDS_LIST_TITLE);
		model.addAttribute("brands", brands);

		return BRANDS_LIST;
	}


	/**
	 * Redirecciona a {@code brands} y muestra un mensaje en la vista
	 * cuando no se encuentran resultados.
	 * 
	 * @param redAttrib Contiene el mensaje a mostrar.
	 * @return Redirección a {@code brands}.
	 */
	private String messageAndRedirect(RedirectAttributes redAttrib) {
		brandsMessages.noBrandMessage(redAttrib);
		return BRANDS_METHOD;
	}
	
	
	/**
	 * Redirecciona a {@code brands} y muestra un mensaje en la vista cuando hay una
	 * excepción.
	 * 
	 * @param e Excepción producida.
	 * @param redAttrib Contiene el mensaje a mostrar.
	 * @return Redirección a {@code brands}.
	 */
	private String exceptionAndRedirect(Exception e, RedirectAttributes redAttrib) {
		e.printStackTrace();
		commonMessages.numberFormatExceptionMessage(redAttrib);
		return BRANDS_METHOD;
	}

} // BrandsBackController
