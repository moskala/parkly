package pw.react.backend.parklybackend.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pw.react.backend.parklybackend.dao.ParkingRepository;
import pw.react.backend.parklybackend.model.Parking;
import pw.react.backend.parklybackend.model.Filter;
import pw.react.backend.parklybackend.service.ParkingService;

import javax.validation.Valid;
import java.awt.image.ReplicateScaleFilter;
import java.util.*;

import static java.util.stream.Collectors.joining;

@RestController
@RequestMapping(path = "/parking") //czy parkings??
//dodac security
public class ParkingController {


    private ParkingRepository repository;
    private ParkingService parkingService;

    @Autowired
    public ParkingController(ParkingRepository repository, ParkingService parkingService) {
        this.repository = repository;
        this.parkingService = parkingService;
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

    @JsonProperty("parking")
    @PostMapping(path = "")
    public ResponseEntity<String> createParking(@RequestHeader HttpHeaders headers, @Valid @RequestBody Parking parking)
    {
        return parkingService.addParking(parking);
    }


    //filtrowanie
    @GetMapping(path = "/filter")
    public ResponseEntity<Object> filter(@RequestHeader HttpHeaders headers,
                                         @RequestParam String city, @RequestParam Optional<String> street,
                                         @RequestParam Optional<Integer> workingHoursFrom, @RequestParam Optional<Integer> workingHoursTo)
    {

        if(city == null || city.isEmpty()) return ResponseEntity.badRequest().body(null);
        //to do : zwalidować ulice, dodac inne filtry
        List<Parking> parkings = parkingService.filterParkings(city, street, workingHoursFrom, workingHoursTo);
        if(parkings != null) return ResponseEntity.ok(parkings);
        else return ResponseEntity.badRequest().body(null);
    }

    @GetMapping(path = "/{parkingId}")
    public ResponseEntity<Parking> getParking(@RequestHeader HttpHeaders headers,
                                              @PathVariable Long parkingId) {

//        if (securityService.isAuthorized(headers)) {
//            return ResponseEntity.ok(repository.findById(parkingId).orElseGet(() -> Parking.EMPTY));
//        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Parking.EMPTY);
        return ResponseEntity.ok(parkingService.getParking(parkingId));
    }

    @GetMapping(path = "")
    public ResponseEntity<Collection<Parking>> getAllParkings(@RequestHeader HttpHeaders headers) {
//
//        if (securityService.isAuthorized(headers)) {
//            return ResponseEntity.ok(repository.findAll());
//        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.emptyList());
        return ResponseEntity.ok(parkingService.getAllParkings());
    }

    @GetMapping(path = "/my-parkings/{ownerId}")
    public ResponseEntity<Collection<Parking>> getAllParkingsByOwner(@RequestHeader HttpHeaders headers,
                                                              @PathVariable Long ownerId
                                                              ) {
//
//        if (securityService.isAuthorized(headers)) {
//            return ResponseEntity.ok(repository.findAll());
//        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.emptyList());
        return ResponseEntity.ok(repository.findAllByOwnerID(ownerId));
    }

    @PutMapping(path = "/{parkingId}")
    public ResponseEntity<Parking> updateParking(@RequestHeader HttpHeaders headers,
                                                 @PathVariable Long parkingId,
                                                 @RequestBody @Valid Parking updatedParking) {

        Parking result;
//        if (securityService.isAuthorized(headers)) {
//            result = companyService.updateCompany(companyId, updatedCompany);
//            if (Company.EMPTY.equals(result)) {
//                return ResponseEntity.badRequest().body(updatedCompany);
//            }
//            return ResponseEntity.ok(result);
//        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Company.EMPTY);
        result = parkingService.updateParking(parkingId, updatedParking);
        if (Parking.EMPTY.equals(result)) {
            return ResponseEntity.badRequest().body(updatedParking);
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(path = "/{parkingId}")
    public ResponseEntity<String> deleteParking(@RequestHeader HttpHeaders headers, @PathVariable Long parkingId) {

//        if (securityService.isAuthorized(headers)) {
//            boolean deleted = companyService.deleteCompany(companyId);
//            if (!deleted) {
//                return ResponseEntity.badRequest().body(String.format("Company with id %s does not exists.", companyId));
//            }
//            return ResponseEntity.ok(String.format("Company with id %s deleted.", companyId));
//        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access to resources.");
        boolean deleted = parkingService.deleteParking(parkingId);
        if (!deleted) {
            return ResponseEntity.badRequest().body(String.format("Parking with id %s does not exists.", parkingId));
        }
        return ResponseEntity.ok(String.format("Parking with id %s deleted.", parkingId));
    }




}
