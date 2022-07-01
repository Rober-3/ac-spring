package my.project.acspring.models.validation;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class MaxAnnotationValidator implements ConstraintValidator<MaxAnnotation, String> {

	static Date currentDate = new Date();
	static SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
	static String year = yearFormat.format(currentDate);
	
	private static final int MAX = Integer.parseInt(year);
	
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		try {
			
			int year = Integer.parseInt(value);

			if (year < MAX)
				return true;

		} catch (Exception e) {
			return false;
		}
		
		return false;
	}
	
}
