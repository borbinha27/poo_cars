package br.edu.ifpr.cars.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AnoFabricacaoValidoValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface AnoFabricacaoValido {
    String message() default "Ano de fabricação inválido. Deve estar entre 1886 e o ano atual";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}