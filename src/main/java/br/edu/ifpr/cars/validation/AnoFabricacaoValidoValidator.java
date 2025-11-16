package br.edu.ifpr.cars.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class AnoFabricacaoValidoValidator implements ConstraintValidator<AnoFabricacaoValido, Integer> {
    
    private static final int ANO_PRIMEIRO_CARRO = 1886;
    
    @Override
    public void initialize(AnoFabricacaoValido constraintAnnotation) {
        // Inicialização se necessário
    }
    
    @Override
    public boolean isValid(Integer ano, ConstraintValidatorContext context) {
        // Se o ano for nulo, considera válido (use @NotNull se quiser obrigar)
        if (ano == null) {
            return true;
        }
        
        // Obtém o ano atual
        int anoAtual = LocalDate.now().getYear();
        
        // Valida se o ano está entre 1886 e o ano atual
        return ano >= ANO_PRIMEIRO_CARRO && ano <= anoAtual;
    }
}