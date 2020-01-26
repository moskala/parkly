package pw.react.backend.parklybackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pw.react.backend.parklybackend.dto.ParkingDto;
import pw.react.backend.parklybackend.model.Parking;
import pw.react.backend.parklybackend.service.ParkingService;
import pw.react.backend.parklybackend.service.SecurityService;

import javax.validation.Valid;
import java.util.*;

import static java.util.stream.Collectors.joining;

@CrossOrigin
@RestController
@RequestMapping(path = "/parkings")
public class ParkingController {

    private ParkingService parkingService;
    private SecurityService securityService;

    @Autowired
    public ParkingController(ParkingService parkingService, SecurityService securityService) {
        this.parkingService = parkingService;
        this.securityService = securityService;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @GetMapping(path = "")
    public ResponseEntity<Collection<ParkingDto>> getAllParkings(@RequestHeader HttpHeaders headers) {

        if (securityService.isAuthorized(headers)) {
            return ResponseEntity.ok(parkingService.getAllParkings());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.emptyList());
    }

    @GetMapping(path = "/my-parkings/{ownerId}")
    public ResponseEntity<Collection<ParkingDto>> getAllParkingsByOwner(@RequestHeader HttpHeaders headers, @PathVariable Long ownerId) {

        if (securityService.isAuthorized(headers)) {
            return ResponseEntity.ok(parkingService.getAllParkingsByOnwerId(ownerId));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.emptyList());
    }

    @PostMapping(path = "")
    public ResponseEntity<?> createParking(@RequestHeader HttpHeaders headers, @Valid @RequestBody ParkingDto parkingRequest)
    {
        if (securityService.isAuthorized(headers)) {
            ParkingDto parking = parkingService.addParking(parkingRequest);
            return ResponseEntity.ok(parking);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized service request.");
    }

    @GetMapping(path = "/{parkingId}")
    public ResponseEntity<ParkingDto> getParking(@RequestHeader HttpHeaders headers, @PathVariable Long parkingId) {

        if (securityService.isAuthorized(headers)) {
            return ResponseEntity.ok(parkingService.getParking(parkingId));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ParkingDto.EMPTY);
    }

    @PutMapping(path = "/{parkingId}")
    public ResponseEntity<ParkingDto> updateParking(@RequestHeader HttpHeaders headers,
                                                 @PathVariable Long parkingId,
                                                 @RequestBody @Valid ParkingDto updatedParking) {
        ParkingDto result;
        if (securityService.isAuthorized(headers)) {
            result = parkingService.updateParking(parkingId, updatedParking);
            if (Parking.EMPTY.equals(result)) {
                return ResponseEntity.badRequest().body(updatedParking);
            }
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ParkingDto.EMPTY);

    }

    @DeleteMapping(path = "/{parkingId}")
    public ResponseEntity<String> deleteParking(@RequestHeader HttpHeaders headers, @PathVariable Long parkingId) {

        if (securityService.isAuthorized(headers)) {
           if(parkingService.deleteParking(parkingId)){
               return ResponseEntity.ok(String.format("Parking with id %s deleted.", parkingId));
           }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access to resources.");
    }

    @GetMapping(path = "/filter")
    public ResponseEntity<Collection<ParkingDto>> filterForParkingOwner(@RequestHeader HttpHeaders headers, @RequestParam Long ownerId,
                                    @RequestParam Optional<String> city, @RequestParam Optional<String> street,
                                    @RequestParam Optional<Integer> workingHoursFrom, @RequestParam Optional<Integer> workingHoursTo) {

        if (securityService.isAuthorized(headers)) {
            Collection<ParkingDto> parkings = parkingService.filterParkingsForOwnerId(ownerId, city, street, workingHoursFrom, workingHoursTo);
            return ResponseEntity.ok(parkings);
        }
        else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.EMPTY_LIST);
    }


}