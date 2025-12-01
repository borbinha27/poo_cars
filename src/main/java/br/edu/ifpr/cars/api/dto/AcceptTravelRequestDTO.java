package br.edu.ifpr.cars.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AcceptTravelRequestDTO {
    
    @NotNull()
    private Long driverId;
}