package my.project.acspring.controllers.frontoffice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import my.project.acspring.models.entity.Classic;
import my.project.acspring.models.entity.Origin;
import my.project.acspring.models.interfaces.IBrandService;
import my.project.acspring.models.interfaces.IClassicService;
import my.project.acspring.models.interfaces.IOriginService;
import my.project.acspring.texts.BrandsMessages;
import my.project.acspring.texts.ClassicsMessages;
import my.project.acspring.texts.ClassicsTitles;
import my.project.acspring.texts.CommonMessages;
import my.project.acspring.texts.OriginsMessages;
import my.project.acspring.utils.frontoffice.BrandsList;


/**
 * Clase de acceso público que gestiona los clásicos en el frontoffice.
 */
@RequestMapping("/ac-spring")
@Controller
public class ClassicsController {

	@Autowired
	private IClassicService classicService;
	@Autowired
	private IBrandService brandService;
	@Autowired
	private IOriginService originService;
	@Autowired
	private ClassicsMessages classicsMessages;
	@Autowired
	private BrandsMessages brandsMessages;
	@Autowired
	private OriginsMessages originsMessages;
	@Autowired
	private ClassicsTitles classicsTitles;
	@Autowired
	private BrandsList brandsList;
	@Autowired
	private CommonMessages commonMessages;
	
	private static final String CLASSICS_LIST = "views/frontoffice/classics";
	private static final String CLASSIC_VIEW = "views/frontoffice/classic";
	private static final String CLASSICS_METHOD = "redirect:/ac-spring/classics";
	
	
	/**
	 * Muestra una lista de clásicos en función del parámetro introducido en la URL
	 * <ul>
	 * <li> con idBrand muestra los clásicos de la marca cuyo id coincide con este parámetro
	 * <li> con originName muestra los clásicos del origen cuyo nombre coincide con este parámetro
	 * <li> sin parámetros muestra los clásicos aprobados por el administrador
	 * </ul>
	 * <p>Si no encuentra ni la marca ni el origen, o si se introduce en la URL un id o un nombre de origen inexistente
	 * 	  redirecciona a {@code classics} para mostrar los clásicos aprobados por el administrador y muestra un mensaje
	 * 	  en la vista. Si se introduce en la URL un id no entero captura la excepción, redirecciona a {@code classics}
	 *    para mostrar los clásicos aprobados por el administrador y muestra un mensaje en la vista.
	 * 
	 * <p>Asimismo obtiene las marcas para mostrar su nombre en un desplegable de la barra de navegación del frontoffice.
	 * 
	 * @param idBrand Id de la marca.
	 * @param originName Nombre del origen.
	 * @param model Guarda la lista de clásicos, el título y el mensaje para mostrarlo en la vista.
	 * @param redAttrib Guarda un mensaje para mostrarlo en la vista cuando salta una excepción al parsear el id.
	 * @return Vista con la lista de clásicos.
	 */
	@GetMapping({"/classics", "/classics_brand/{idBrand}", "/classics_origin/{originName}"})
	public String classics(@PathVariable(required = false) String idBrand, @PathVariable(required = false) String originName,
						   Model model, RedirectAttributes redAttrib) {
		
		List<Classic> classics = new ArrayList<Classic>();
		
		try {
			
			if (idBrand != null) {
				
				Long id = Long.parseLong(idBrand);
				
				if (!brandService.exists(id)) {
					brandsMessages.noBrandMessage(redAttrib);
					return CLASSICS_METHOD;
				}
				
				classics = classicService.getByBrand(id);
				classicsTitles.classicsBrandTitle(model, brandService.getById(id).getName());
				
			} else if (originName != null) {
				
				Origin origin = originService.getByName(originName);
				
				if (!originService.exists(origin.getId())) {
					originsMessages.noOriginMessage(redAttrib);
					return CLASSICS_METHOD;
				}
				
				classics = classicService.getByOrigin(originName);
				classicsTitles.classicsOriginTitle(model, originName);

			} else {
				classics = classicService.getByStatus(true);
				model.addAttribute("title", ClassicsTitles.CLASSICS_LIST_TITLE);
			}
				
			if (classics.isEmpty())
				classicsMessages.noClassicsMessage(model);
			
		} catch (NumberFormatException e) {
			exceptionAndRedirect(e, redAttrib);
			
		} catch (Exception e) {
			commonMessages.errorMessage(e, model);
		}
		
		brandsList.showBrands(model);
		model.addAttribute("classics", classics);
			
		return CLASSICS_LIST;
	}


	/**
	 * Muestra un clásico buscándolo por su id. Si no lo encuentra o se introduce en la URL un
	 * id inexistente redirecciona a {@code classics} a través de {@code messageAndRedirect} y
	 * muestra un mensaje en la vista. Si se introduce en la URL un id no entero captura la
	 * excepción, redirecciona a {@code classics} a través de {@code exceptionAndRedirect} y 
	 * muestra un mensaje en la vista.
	 * 
	 * <p>Asimismo obtiene las marcas para mostrar su nombre en un desplegable de la barra de
	 *	  navegación del frontoffice.
	 * 
	 * @param id Id del clásico.
	 * @param model Guarda el clásico, el título y el mensaje para mostrarlos en la vista.
	 * @param redAttrib Guarda un mensaje para mostrarlo en la vista si no encuentra un clásico
	 *					o es nulo, y cuando salta una excepción al parsear el id.
	 * @return Vista con el clásico cuyo id coincide con el buscado o en caso de no haber uno
	 * 		   para mostrar redirección a {@code classics}.
	 */
	@GetMapping("/classic/{id}")
	public String classic(@PathVariable String id, Model model, RedirectAttributes redAttrib) {

		Classic classic = new Classic();
		
		try {
			Long idClassic = Long.parseLong(id);
				
			if (!classicService.exists(idClassic))
				messageAndRedirect(redAttrib);
			
			classic = classicService.getByIdApproved(idClassic);
			
			if (classic == null)
				messageAndRedirect(redAttrib);
			
		} catch (NumberFormatException e) {
			exceptionAndRedirect(e, redAttrib);
			
		} catch (Exception e) {
			commonMessages.errorMessage(e, model);
		}
		
		brandsList.showBrands(model);
		model.addAttribute("title", ClassicsTitles.CLASSIC_DETAILS_TITLE);
		model.addAttribute("classic", classic);
				
		return CLASSIC_VIEW;
	}


	/**
	 * Redirecciona a {@code classics} y muestra un mensaje en la
	 * vista cuando no se encuentran resultados.
	 * 
	 * @param redAttrib Contiene el mensaje a mostrar.
	 * @return Redirección a {@code classics}.
	 */
	private String messageAndRedirect(RedirectAttributes redAttrib) {
		classicsMessages.noClassicMessage(redAttrib);
		return CLASSICS_METHOD;
	}
	
	
	/**
	 * Redirecciona a {@code classics} y muestra un mensaje en la vista cuando se
	 * produce una excepción.
	 * 
	 * @param e Excepción producida.
	 * @param redAttrib Contiene el mensaje a mostrar.
	 * @return Redirección a {@code classics}.
	 */
	private String exceptionAndRedirect(Exception e, RedirectAttributes redAttrib) {
		e.printStackTrace();
		commonMessages.numberFormatExceptionMessage(redAttrib);
		return CLASSICS_METHOD;
	}

} // ClassicsController
