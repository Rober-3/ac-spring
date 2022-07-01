package my.project.acspring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class MvcConfig implements WebMvcConfigurer {

	/**
	 * Configura un controlador para que muestre la página de error 403.
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/error_403").setViewName("error/403");
	}
	
	
	/**
	 * Implementación de passwordEncoder empleando BCrypt. Registra 
	 * passwordEncoder en la configuración de Spring Security para
	 * que lo use como método de encriptación por defecto.
	 * 
	 * @return Instancia de BCryptPasswordEncoder.
	 * 
	 * @see {@link BCryptPasswordEncoder}
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	
	/**
	 * Añade recursos estáticos al proyecto configurando un directorio externo como directorio de recursos para subir
	 * imágenes al servidor desde los formularios.<br><br>
	 * 
	 * uploads/cars y uploads/users son, respectivamente, la carpeta para subir las fotos de los clásicos y las de los
	 * usuarios.<br><br>
	 * 
	 * Para especificar unas rutas físicas es necesario modificar los strings de {@code addResourceLocations}.<br><br>
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		WebMvcConfigurer.super.addResourceHandlers(registry);

		registry.addResourceHandler("/uploads/cars/**").addResourceLocations("file:/Users/rober/DESARROLLO/uploads/cars/");
		registry.addResourceHandler("/uploads/users/**").addResourceLocations("file:/Users/rober/DESARROLLO/uploads/users/");
	}

} // MvcConfig
