package bth.dss.group2.backend.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import bth.dss.group2.backend.model.dto.RegistrationForm;

/**
 * From https://github.com/Baeldung/spring-security-registration (MIT License), possibly with modifications
 */
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

	@Override
	public void initialize(final PasswordMatches constraintAnnotation) {
		//
	}

	@Override
	public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
		final RegistrationForm reg = (RegistrationForm) obj;
		return reg.getPassword().equals(reg.getPasswordRepeat());
	}
}