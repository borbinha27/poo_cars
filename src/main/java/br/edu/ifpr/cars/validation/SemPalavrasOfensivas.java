package br.edu.ifpr.cars.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SemPalavrasOfensivasValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface SemPalavrasOfensivas {
    String message() default "O comentário contém palavras ofensivas";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}