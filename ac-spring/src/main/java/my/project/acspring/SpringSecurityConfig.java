package my.project.acspring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import my.project.acspring.auth.handler.LoginSuccessHandler;
import my.project.acspring.utils.backoffice.LoggedInUserDetailsService;


/**
 * Clase que configura la seguridad de la aplicación.
 */
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private LoginSuccessHandler successHandler;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private LoggedInUserDetailsService loggedInUserDetailsService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
		builder.userDetailsService(loggedInUserDetailsService).passwordEncoder(passwordEncoder);
	}
	
	
	/**
	 * Configura las autorizaciones en las rutas para evitar accesos no autorizados a los controladores a través de la URL.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests().antMatchers("/bootstrap/**", "/css/**", "/datatables/**", "/fontawesome-free/**", "/images/**", "/js/**", "/uploads/**",
				 							 "/ac-spring", "/ac-spring/", "/ac-spring/index", "/ac-spring/home", 
				 							 "/ac-spring/classics", "/ac-spring/classic/{id}", 
				 							 "/ac-spring/classics_brand/{id}", "/ac-spring/classics_origin/{origin}",
				 							 "/ac-spring/brands", "/ac-spring/brand/{id}",
				 							 "/ac-spring/registry", "/ac-spring/new_user").permitAll()
								.antMatchers("/ac-spring/backoffice/users").hasAnyRole("ADMIN")
								.antMatchers("/ac-spring/backoffice/user/**").hasAnyRole("ADMIN", "USER")
								.antMatchers("/ac-spring/backoffice/edit_user/**").hasAnyRole("ADMIN", "USER")
								.antMatchers("/ac-spring/backoffice/save_user").hasAnyRole("ADMIN", "USER")
								.antMatchers("/ac-spring/backoffice/**").hasAnyRole("ADMIN", "USER")
								.anyRequest().authenticated()
								.and().formLogin().successHandler(successHandler).loginPage("/ac-spring/login").permitAll()
								.and().logout().permitAll()
								.and().exceptionHandling().accessDeniedPage("/error_403");
	}
	
} // SpringSecurityConfig
