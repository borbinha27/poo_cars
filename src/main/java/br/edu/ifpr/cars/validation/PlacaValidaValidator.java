package br.edu.ifpr.cars.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PlacaValidaValidator implements ConstraintValidator<PlacaValida, String> {
    
    private static final String PLACA_MERCOSUL_PATTERN = "^[A-Z]{3}[0-9][A-Z][0-9]{2}$";
    
    @Override
    public void initialize(PlacaValida constraintAnnotation) {
        // Inicialização se necessário
    }
    
    @Override
    public boolean isValid(String placa, ConstraintValidatorContext context) {
        // Se a placa for nula ou vazia, considera válido (use @NotBlank se quiser obrigar)
        if (placa == null || placa.isEmpty()) {
            return true;
        }
        
        // Valida se a placa segue o formato Mercosul
        return placa.matches(PLACA_MERCOSUL_PATTERN);
    }
}