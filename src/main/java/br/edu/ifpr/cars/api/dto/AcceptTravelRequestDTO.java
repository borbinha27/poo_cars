package br.edu.ifpr.cars.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AcceptTravelRequestDTO {
    
    @NotNull(message = "ID do motorista é obrigatório")
    private Long driverId;
}