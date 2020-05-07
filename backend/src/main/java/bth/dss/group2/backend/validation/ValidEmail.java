package bth.dss.group2.backend.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * From https://github.com/Baeldung/spring-security-registration (MIT License), possibly with modifications
 */
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface ValidEmail {

	String message() default "Invalid Email";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}