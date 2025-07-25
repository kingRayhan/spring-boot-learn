package dev.rayhan.spring_store.common.validator_annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.hibernate.validator.internal.constraintvalidators.hv.UUIDValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UUIDValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUUID {
  String message() default "Invalid UUID format";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
