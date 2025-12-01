package br.edu.ifpr.cars.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateTravelRequestDTO {
    
    @NotNull()
    private Long passengerId;
    
    @NotBlank()
    private String origin;
    
    @NotBlank()
    private String destination;
}