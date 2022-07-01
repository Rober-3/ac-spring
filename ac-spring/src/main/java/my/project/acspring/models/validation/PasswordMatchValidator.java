package my.project.acspring.models.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import my.project.acspring.models.entity.UserDto;


public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Object> {

	@Override
	public void initialize(PasswordMatch constraintAnnotation) {
	}
	
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		
		final UserDto user = (UserDto) value;
		return user.getPassword().equals(user.getConfirmPassword());
	}

}
