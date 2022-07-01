package my.project.acspring.auth.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;


/**
 * Clase que muestra un mensaje cuando un usuario ha iniciado sesión con éxito.
 */
@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
										Authentication authentication) throws IOException, ServletException {
		
		SessionFlashMapManager flashMapManager = new SessionFlashMapManager();

		FlashMap flashMap = new FlashMap();

		flashMap.put("class", "success");
		flashMap.put("message", "Has iniciado sesión correctamente.");

		flashMapManager.saveOutputFlashMap(flashMap, request, response);

		if (authentication != null)
			logger.info("El usuario " + authentication.getName().concat(" ha iniciado sesión con éxito."));

		super.onAuthenticationSuccess(request, response, authentication);
	}
	
}
