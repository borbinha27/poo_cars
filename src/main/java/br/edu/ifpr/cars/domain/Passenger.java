package br.edu.ifpr.cars.domain;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Data
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank
    @Size(min = 2, max = 50)
    String name;

    @Email
    @NotBlank
    String email;

    @CPF
    @NotBlank
    String cpf;
}
