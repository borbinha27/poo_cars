package br.edu.ifpr.cars.api;

import br.edu.ifpr.cars.api.dto.AcceptTravelRequestDTO;
import br.edu.ifpr.cars.api.dto.CreateTravelRequestDTO;
import br.edu.ifpr.cars.domain.TravelRequest;
import br.edu.ifpr.cars.domain.TravelRequestStatus;
import br.edu.ifpr.cars.service.TravelService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/travel-requests", produces = MediaType.APPLICATION_JSON_VALUE)
public class TravelRequestController {
    
    private final TravelService travelService;
    
    public TravelRequestController(TravelService travelService) {
        this.travelService = travelService;
    }
    
    @PostMapping
    public ResponseEntity<TravelRequest> createTravelRequest(
            @Valid @RequestBody CreateTravelRequestDTO dto) {
        TravelRequest travelRequest = travelService.createTravelRequest(
            dto.getPassengerId(), 
            dto.getOrigin(), 
            dto.getDestination()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(travelRequest);
    }
    
    @PatchMapping("/{id}/accept")
    public ResponseEntity<TravelRequest> acceptTravelRequest(
            @PathVariable Long id,
            @Valid @RequestBody AcceptTravelRequestDTO dto) {
        TravelRequest travelRequest = travelService.acceptTravelRequest(id, dto.getDriverId());
        return ResponseEntity.ok(travelRequest);
    }
    
    @PatchMapping("/{id}/refuse")
    public ResponseEntity<TravelRequest> refuseTravelRequest(
            @PathVariable Long id,
            @Valid @RequestBody AcceptTravelRequestDTO dto) {
        TravelRequest travelRequest = travelService.refuseTravelRequest(id, dto.getDriverId());
        return ResponseEntity.ok(travelRequest);
    }
    
    @GetMapping
    public ResponseEntity<List<TravelRequest>> listAllTravelRequests(
            @RequestParam(required = false) TravelRequestStatus status) {
        List<TravelRequest> travelRequests;
        
        if (status != null) {
            travelRequests = travelService.listTravelRequestsByStatus(status);
        } else {
            travelRequests = travelService.listAllTravelRequests();
        }
        
        return ResponseEntity.ok(travelRequests);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TravelRequest> findTravelRequest(@PathVariable Long id) {
        TravelRequest travelRequest = travelService.findTravelRequestById(id);
        return ResponseEntity.ok(travelRequest);
    }
    
    @GetMapping("/passenger/{passengerId}")
    public ResponseEntity<List<TravelRequest>> listTravelRequestsByPassenger(
            @PathVariable Long passengerId) {
        List<TravelRequest> travelRequests = travelService.listTravelRequestsByPassenger(passengerId);
        return ResponseEntity.ok(travelRequests);
    }
    
    @GetMapping("/driver/{driverId}")
    public ResponseEntity<List<TravelRequest>> listTravelRequestsByDriver(
            @PathVariable Long driverId) {
        List<TravelRequest> travelRequests = travelService.listTravelRequestsByDriver(driverId);
        return ResponseEntity.ok(travelRequests);
    }
}