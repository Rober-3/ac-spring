package my.project.acspring.models.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


/**
 * El año introducido en un formulario debe ser menor al año actual.
 */
@Documented
@Constraint(validatedBy = MaxAnnotationValidator.class)
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface MaxAnnotation {

	String message() default "El año debe ser menor al año actual.";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
	
}
