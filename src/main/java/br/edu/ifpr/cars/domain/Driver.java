package br.edu.ifpr.cars.domain;

import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;

import br.edu.ifpr.cars.validation.AnoFabricacaoValido;
import br.edu.ifpr.cars.validation.CNHValida;
import br.edu.ifpr.cars.validation.PlacaValida;
import br.edu.ifpr.cars.validation.SemPalavrasOfensivas;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Data
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    
    @NotBlank
    @Size(min = 2, max = 50)
    String name;
    
    LocalDate birthDate;
    
    @NotBlank
    @CPF
    String cpf;
    
    @PlacaValida
    String placa;
    
    @CNHValida
    String cnh;
    
    @AnoFabricacaoValido
    Integer anoCarro;
    
    @SemPalavrasOfensivas
    String comentario;
}