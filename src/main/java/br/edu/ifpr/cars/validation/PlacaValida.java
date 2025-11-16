package br.edu.ifpr.cars.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PlacaValidaValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PlacaValida {
    String message() default "Placa inválida. Deve seguir o formato Mercosul (ex: ABC1D23)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}