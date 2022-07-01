package my.project.acspring.utils.frontoffice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import my.project.acspring.models.entity.Brand;
import my.project.acspring.models.interfaces.IBrandService;
import my.project.acspring.texts.BrandsMessages;
import my.project.acspring.texts.CommonMessages;


/**
 * Utilidad del frontoffice para mostrar las marcas.
 */
@Component
public class BrandsList {
	
	@Autowired
	private IBrandService brandService;
	@Autowired
	private BrandsMessages brandsMessages;
	@Autowired
	private CommonMessages commonMessages;

	
	/**
	 * Muestra las marcas que tienen una lista de clásicos aprobados 
	 * por el administrador. Si no existen marcas de ese tipo o se
	 * produce una excepción muestra un mensaje en la vista. 
	 * 
	 * @param model Guarda la lista de marcas 
	 */
	public void showBrands(Model model) {

		List<Brand> brands = new ArrayList<Brand>();

		try {
			brands = brandService.getAllWithClassicsApproved();
			
			if (brands.isEmpty())
				brandsMessages.noBrandsMessage(model);

		} catch (Exception e) {
			commonMessages.errorMessage(e, model);
		}

		model.addAttribute("brands", brands);
	}

}
