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
    
    /**
     * Cria uma nova solicitação de viagem
     * Passageiro cria viagem → status CREATED
     */
    @Transactional
    public TravelRequest createTravelRequest(Long passengerId, String origin, String destination) {
        // Valida se o passageiro existe
        Passenger passenger = passengerRepository.findById(passengerId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Passageiro não encontrado"));
        
        // Cria a solicitação de viagem
        TravelRequest travelRequest = new TravelRequest();
        travelRequest.setPassenger(passenger);
        travelRequest.setOrigin(origin);
        travelRequest.setDestination(destination);
        travelRequest.setStatus(TravelRequestStatus.CREATED);
        
        return travelRequestRepository.save(travelRequest);
    }
    
    /**
     * Motorista aceita uma viagem
     * Regras:
     * - Não pode aceitar viagem em status diferente de CREATED
     * - Se já estiver ACCEPTED ou REFUSED → retornar 400
     * - Caso id não exista → retornar 404
     */
    @Transactional
    public TravelRequest acceptTravelRequest(Long travelRequestId, Long driverId) {
        // Valida se o motorista existe
        Driver driver = driverRepository.findById(driverId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Motorista não encontrado"));
        
        // Valida se a viagem existe
        TravelRequest travelRequest = travelRequestRepository.findById(travelRequestId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Viagem não encontrada"));
        
        // Valida o status da viagem
        if (travelRequest.getStatus() != TravelRequestStatus.CREATED) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, 
                "Não é possível aceitar uma viagem com status " + travelRequest.getStatus());
        }
        
        // Aceita a viagem
        travelRequest.setDriver(driver);
        travelRequest.setStatus(TravelRequestStatus.ACCEPTED);
        travelRequest.setAcceptanceDate(LocalDateTime.now());
        
        return travelRequestRepository.save(travelRequest);
    }
    
    /**
     * Motorista recusa uma viagem
     */
    @Transactional
    public TravelRequest refuseTravelRequest(Long travelRequestId, Long driverId) {
        // Valida se o motorista existe
        driverRepository.findById(driverId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Motorista não encontrado"));
        
        // Valida se a viagem existe
        TravelRequest travelRequest = travelRequestRepository.findById(travelRequestId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Viagem não encontrada"));
        
        // Valida o status da viagem
        if (travelRequest.getStatus() != TravelRequestStatus.CREATED) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, 
                "Não é possível recusar uma viagem com status " + travelRequest.getStatus());
        }
        
        // Recusa a viagem
        travelRequest.setStatus(TravelRequestStatus.REFUSED);
        
        return travelRequestRepository.save(travelRequest);
    }
    
    /**
     * Lista todas as viagens
     */
    public List<TravelRequest> listAllTravelRequests() {
        return travelRequestRepository.findAll();
    }
    
    /**
     * Busca uma viagem por ID
     */
    public TravelRequest findTravelRequestById(Long id) {
        return travelRequestRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Viagem não encontrada"));
    }
    
    /**
     * Lista viagens de um passageiro
     */
    public List<TravelRequest> listTravelRequestsByPassenger(Long passengerId) {
        // Valida se o passageiro existe
        passengerRepository.findById(passengerId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Passageiro não encontrado"));
        
        return travelRequestRepository.findByPassengerId(passengerId);
    }
    
    /**
     * Lista viagens de um motorista
     */
    public List<TravelRequest> listTravelRequestsByDriver(Long driverId) {
        // Valida se o motorista existe
        driverRepository.findById(driverId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Motorista não encontrado"));
        
        return travelRequestRepository.findByDriverId(driverId);
    }
    
    /**
     * Lista viagens por status
     */
    public List<TravelRequest> listTravelRequestsByStatus(TravelRequestStatus status) {
        return travelRequestRepository.findByStatus(status);
    }
}