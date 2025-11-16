package br.edu.ifpr.cars.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CNHValidaValidator implements ConstraintValidator<CNHValida, String> {
    
    @Override
    public void initialize(CNHValida constraintAnnotation) {
        // Inicialização se necessário
    }
    
    @Override
    public boolean isValid(String cnh, ConstraintValidatorContext context) {
        // Se a CNH for nula ou vazia, considera válido (use @NotBlank se quiser obrigar)
        if (cnh == null || cnh.isEmpty()) {
            return true;
        }
        
        // Valida se a CNH possui exatamente 11 dígitos numéricos
        return cnh.matches("\\d{11}");
    }
}