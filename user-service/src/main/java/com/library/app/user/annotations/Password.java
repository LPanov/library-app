package com.library.app.user.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class) // Links to the logic class
@Target({ ElementType.FIELD, ElementType.PARAMETER }) // Can be used on fields and parameters
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {

    // The default error message if validation fails
    String message() default "Password must be 8-20 characters long and include at least one uppercase letter, one lowercase letter, one digit, and one special character.";

    // Required by the Spring Validation framework
    Class<?>[] groups() default {};

    // Required by the Spring Validation framework
    Class<? extends Payload>[] payload() default {};
}
