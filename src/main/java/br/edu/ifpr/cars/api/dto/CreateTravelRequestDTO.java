package br.edu.ifpr.cars.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateTravelRequestDTO {
    
    @NotNull(message = "ID do passageiro é obrigatório")
    private Long passengerId;
    
    @NotBlank(message = "Origem é obrigatória")
    private String origin;
    
    @NotBlank(message = "Destino é obrigatório")
    private String destination;
}