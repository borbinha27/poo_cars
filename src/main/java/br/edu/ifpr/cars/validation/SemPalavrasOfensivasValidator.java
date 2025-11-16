package br.edu.ifpr.cars.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class SemPalavrasOfensivasValidator implements ConstraintValidator<SemPalavrasOfensivas, String> {
    
    // Lista de palavras proibidas
    private static final List<String> PALAVRAS_OFENSIVAS = Arrays.asList(
        "burro", "idiota", "lixo", "estúpido", "imbecil", 
        "tolo", "bobo", "otário", "cretino", "inútil"
    );
    
    @Override
    public void initialize(SemPalavrasOfensivas constraintAnnotation) {
        // Inicialização se necessário
    }
    
    @Override
    public boolean isValid(String comentario, ConstraintValidatorContext context) {
        // Se o comentário for nulo ou vazio, considera válido
        if (comentario == null || comentario.isEmpty()) {
            return true;
        }
        
        // Converte o comentário para minúsculas
        String comentarioLower = comentario.toLowerCase();
        
        // Verifica se contém alguma palavra ofensiva
        for (String palavraOfensiva : PALAVRAS_OFENSIVAS) {
            if (comentarioLower.contains(palavraOfensiva)) {
                return false;
            }
        }
        
        return true;
    }
}