package my.project.acspring.controllers.frontoffice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import my.project.acspring.models.entity.Brand;
import my.project.acspring.models.interfaces.IBrandService;
import my.project.acspring.models.interfaces.IClassicService;
import my.project.acspring.texts.BrandsMessages;
import my.project.acspring.texts.BrandsTitles;
import my.project.acspring.texts.CommonMessages;
import my.project.acspring.utils.frontoffice.BrandsList;


/**
 * Clase de acceso público que gestiona las marcas en el frontoffice.
 */
@RequestMapping("/ac-spring")
@Controller
public class BrandsController {

	@Autowired
	private IBrandService brandService;
	@Autowired
	private IClassicService classicService;
	@Autowired
	private BrandsTitles brandsTitles;
	@Autowired
	private BrandsMessages brandsMessages;
	@Autowired
	private CommonMessages commonMessages;
	@Autowired
	private BrandsList brandsList;

	private static final String BRANDS_LIST = "views/frontoffice/brands";
	private static final String BRAND_VIEW = "views/frontoffice/brand";
	private static final String BRANDS_METHOD = "redirect:/ac-spring/brands";


	/**
	 * Muestra las marcas en el backoffice a través de {@code showBrands}.
	 * 
	 * @param model Guarda la lista de marcas y el título para mostrarlos
	 * 				en la vista.
	 * @return Vista con la lista de marcas.
	 * @see showBrands
	 */
	@GetMapping("/brands")
	public String brands(Model model) {

		model.addAttribute("title", BrandsTitles.BRANDS_LIST_TITLE);
		brandsList.showBrands(model);
		
		return BRANDS_LIST;
	}


	/**
	 * Muestra una marca buscándola por su id. Si no la encuentra o se introduce en la URL un
	 * id inexistente redirecciona a {@code brands} y muestra un mensaje en la vista. Si se
	 * introduce en la URL un id no entero captura la excepción, redirecciona a {@code brands}
	 * y muestra un mensaje en la vista. En caso de introducir en la URL el id de una marca
	 * existente que no tenga clásicos muestra un mensaje en la vista.
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
		
			if (!brandService.exists(idBrand)) {
				brandsMessages.noBrandMessage(redAttrib);
				return BRANDS_METHOD;
			}

			brand = brandService.getByIdWithClassicsApproved(idBrand);
			
			// Cuenta el número de clásicos de la marca, y si es 0 muestra un mensaje.
			Long numClassics = classicService.countApprovedByBrand(idBrand);
			if (numClassics == 0)
				brandsMessages.noClassicBrandMessage(model);
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			commonMessages.numberFormatExceptionMessage(redAttrib);
			return BRANDS_METHOD;
			
		} catch (Exception e) {
			commonMessages.errorMessage(e, model);
		}

		brandsTitles.brandTitle(model, brand);
		brandsList.showBrands(model);
		model.addAttribute("brand", brand);

		return BRAND_VIEW;
	}
	
} // BrandsController

