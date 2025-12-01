package br.edu.ifpr.cars.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class TravelRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull()
    @ManyToOne
    @JoinColumn(name = "passenger_id", nullable = false)
    private Passenger passenger;
    
    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;
    
    @NotBlank()
    @Column(nullable = false)
    private String origin;
    
    @NotBlank()
    @Column(nullable = false)
    private String destination;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TravelRequestStatus status;
    
    @Column(nullable = false)
    private LocalDateTime creationDate;
    
    private LocalDateTime acceptanceDate;
    
    @PrePersist
    protected void onCreate() {
        this.creationDate = LocalDateTime.now();
        if (this.status == null) {
            this.status = TravelRequestStatus.CREATED;
        }
    }
}