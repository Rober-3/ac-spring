package my.project.acspring.utils.frontoffice;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import my.project.acspring.texts.CommonMessages;


/**
 * Clase con métodos para operar con las fotos de los clásicos y los usuarios,.
 */
@Component
public class PhotoMethods {
	
	@Autowired
	private CommonMessages commonMessages;

	
	/**
	 * Copia la imagen de un clásico o de un usuario en un directorio externo. Asigna un UUID al
	 * nombre la foto para evitar que dos imágenes con el mismo nombre se sobrescriban, y con el
	 * UUIO y la ruta relativa del directorio obtiene la ruta absoluta.
	 * 
	 * @param folderPath Ruta del directorio.
	 * @param photo Foto a copiar.
	 * @param model Guarda un mensaje de error para mostrarlo en la vista en caso de excepción.
	 * @return
	 */
	public String copyPhoto(String folderPath, MultipartFile photo, Model model) {
		
		// Ruta relativa. El directorio y su ruta se encuentran registrados en MvcConfig.
		String directoryPath = folderPath;
		
		// Asignación de un UUID al nombre de la foto.
		String uniqueFilename = UUID.randomUUID().toString() + "_" + photo.getOriginalFilename();
		
		// Ruta absoluta.
		Path absolutePath = Paths.get(directoryPath + "//" + uniqueFilename);
			
		try {
			byte[] bytes = photo.getBytes();
			Files.write(absolutePath, bytes);
			
		} catch (IOException e) {
			e.printStackTrace();
			commonMessages.copyPhotoExceptionMessage(model);
		}
		
		return uniqueFilename;
	}
	
	
	/**
	 * Borra la imagen de un clásico o de un usuario del directorio de fotos.
	 * Obtiene la ruta absoluta de la foto a borrar y la propia foto a partir
	 * de dicha ruta, y si existe y se puede leer la borra.
	 * 
	 * @param folderPath Ruta del directorio.
	 * @param photo Foto a borrar.
	 */
	public void deletePhoto(String folderPath, String photo) {
		
		Path path = Paths.get(folderPath).resolve(photo).toAbsolutePath();
		
		File deletedPhoto = path.toFile();
		
		if (deletedPhoto.exists() && deletedPhoto.canRead())
			deletedPhoto.delete();
	}
	
} // PhotoMethods
