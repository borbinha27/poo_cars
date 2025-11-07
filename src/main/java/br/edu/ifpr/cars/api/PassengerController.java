package br.edu.ifpr.cars.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.edu.ifpr.cars.domain.Passenger;
import br.edu.ifpr.cars.domain.PassengerRepository;

@Service
@RestController
@RequestMapping(value = "/passengers", produces = MediaType.APPLICATION_JSON_VALUE)
public class PassengerController {

    private final PassengerRepository passengerRepository;

    public PassengerController(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @GetMapping
    public List<Passenger> listPassengers() {
        return passengerRepository.findAll();
    }

    @GetMapping("/{id}")
    public Passenger findPassenger(@PathVariable("id") Long id) {
        return passengerRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Passenger not found"));
    }

    @PostMapping
    public Passenger createPassenger(@RequestBody Passenger passenger) {
        return passengerRepository.save(passenger);
    }

    // update
    @PutMapping("/{id}")
    public Passenger fullUpdatePassenger(@PathVariable("id") Long id,
            @RequestBody Passenger passenger) {
        Passenger foundPassenger = findPassenger(id);
        foundPassenger.setName(passenger.getName());
        foundPassenger.setEmail(passenger.getEmail());
        foundPassenger.setCpf(passenger.getCpf());
        return passengerRepository.save(foundPassenger);
    }

    @PatchMapping("/{id}")
    public Passenger incrementalUpdatePassenger(@PathVariable("id") Long id,
            @RequestBody Passenger passenger){
            Passenger foundPassenger = findPassenger(id);

            foundPassenger.setName(Optional.ofNullable(passenger.getName())
                .orElse(foundPassenger.getName()));

            foundPassenger.setEmail(Optional.ofNullable(passenger.getEmail())
                .orElse(foundPassenger.getEmail()));

            foundPassenger.setCpf(Optional.ofNullable(passenger.getCpf())
                .orElse(foundPassenger.getCpf()));

            return passengerRepository.save(foundPassenger);
    }

    @DeleteMapping("/{id}")
    public void deletePassenger(@PathVariable("id") Long id){
        passengerRepository.deleteById(id);
    }

        @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> handleResponseStatus(ResponseStatusException ex) {
        Map<String, String> body = new HashMap<>();
        body.put("error", ex.getReason());
        return ResponseEntity.status(ex.getStatusCode()).body(body);
    }
}
