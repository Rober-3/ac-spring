package my.project.acspring.models.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


@Constraint(validatedBy = PasswordMatchValidator.class)
@Documented
@Retention(RUNTIME)
@Target({ TYPE, ANNOTATION_TYPE })
public @interface PasswordMatch {
	
	String message() default "La contraseña y su confirmación deben coincidir.";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

}
