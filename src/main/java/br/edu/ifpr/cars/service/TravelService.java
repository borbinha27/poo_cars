package br.edu.ifpr.cars.service;

import br.edu.ifpr.cars.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TravelService {
    
    private final TravelRequestRepository travelRequestRepository;
    private final PassengerRepository passengerRepository;
    private final DriverRepository driverRepository;
    
    public TravelService(TravelRequestRepository travelRequestRepository,
                        PassengerRepository passengerRepository,
                        DriverRepository driverRepository) {
        this.travelRequestRepository = travelRequestRepository;
        this.passengerRepository = passengerRepository;
        this.driverRepository = driverRepository;
    }
    
    @Transactional
    public TravelRequest createTravelRequest(Long passengerId, String origin, String destination) {
        Passenger passenger = passengerRepository.findById(passengerId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Passageiro não encontrado"));
        
        TravelRequest travelRequest = new TravelRequest();
        travelRequest.setPassenger(passenger);
        travelRequest.setOrigin(origin);
        travelRequest.setDestination(destination);
        travelRequest.setStatus(TravelRequestStatus.CREATED);
        
        return travelRequestRepository.save(travelRequest);
    }
    
    @Transactional
    public TravelRequest acceptTravelRequest(Long travelRequestId, Long driverId) {
        Driver driver = driverRepository.findById(driverId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Motorista não encontrado"));
        
        TravelRequest travelRequest = travelRequestRepository.findById(travelRequestId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Viagem não encontrada"));

        if (travelRequest.getStatus() != TravelRequestStatus.CREATED) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, 
                "Não é possível aceitar uma viagem com status " + travelRequest.getStatus());
        }
        
        travelRequest.setDriver(driver);
        travelRequest.setStatus(TravelRequestStatus.ACCEPTED);
        travelRequest.setAcceptanceDate(LocalDateTime.now());
        
        return travelRequestRepository.save(travelRequest);
    }
    
    @Transactional
    public TravelRequest refuseTravelRequest(Long travelRequestId, Long driverId) {
        driverRepository.findById(driverId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Motorista não encontrado"));
        
        TravelRequest travelRequest = travelRequestRepository.findById(travelRequestId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Viagem não encontrada"));
        
        if (travelRequest.getStatus() != TravelRequestStatus.CREATED) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, 
                "Não é possível recusar uma viagem com status " + travelRequest.getStatus());
        }
        
        travelRequest.setStatus(TravelRequestStatus.REFUSED);
        
        return travelRequestRepository.save(travelRequest);
    }
    
    public List<TravelRequest> listAllTravelRequests() {
        return travelRequestRepository.findAll();
    }
 
    public TravelRequest findTravelRequestById(Long id) {
        return travelRequestRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Viagem não encontrada"));
    }
    
    public List<TravelRequest> listTravelRequestsByPassenger(Long passengerId) {
        passengerRepository.findById(passengerId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Passageiro não encontrado"));
        
        return travelRequestRepository.findByPassengerId(passengerId);
    }
    
    public List<TravelRequest> listTravelRequestsByDriver(Long driverId) {
        driverRepository.findById(driverId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Motorista não encontrado"));
        
        return travelRequestRepository.findByDriverId(driverId);
    }
    
    public List<TravelRequest> listTravelRequestsByStatus(TravelRequestStatus status) {
        return travelRequestRepository.findByStatus(status);
    }
}