package pw.react.backend.parklybackend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pw.react.backend.parklybackend.dao.ParkingOwnerRepository;
import pw.react.backend.parklybackend.model.ParkingOwner;
import pw.react.backend.parklybackend.service.ParkingOwnerService;

import javax.validation.Valid;
import java.util.*;

import static java.util.stream.Collectors.joining;

@RestController
@RequestMapping(path = "/parkingOwner") //czy parkings??
//dodac security
public class ParkingOwnerController {


    private ParkingOwnerRepository repository;
    private ParkingOwnerService parkingOwnerService;

    @Autowired
    public ParkingOwnerController(ParkingOwnerRepository repository, ParkingOwnerService parkingOwnerService) {
        this.repository = repository;
        this.parkingOwnerService = parkingOwnerService;
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

    @PostMapping(path = "")
    public ResponseEntity<String> createParking(@RequestHeader HttpHeaders headers, @Valid @RequestBody ParkingOwner parkingOwner)
    {
        return parkingOwnerService.addParkingOwner(parkingOwner);
    }


    @GetMapping(path = "/{parkingOwnerId}")
    public ResponseEntity<ParkingOwner> getParkingOwner(@RequestHeader HttpHeaders headers,
                                              @PathVariable Long parkingOwnerId) {

//        if (securityService.isAuthorized(headers)) {
//            return ResponseEntity.ok(repository.findById(parkingId).orElseGet(() -> Parking.EMPTY));
//        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Parking.EMPTY);
        return ResponseEntity.ok(parkingOwnerService.getParkingOwner(parkingOwnerId));
    }

    @GetMapping(path = "")
    public ResponseEntity<Collection<ParkingOwner>> getAllParkingOwners(@RequestHeader HttpHeaders headers) {
//
//        if (securityService.isAuthorized(headers)) {
//            return ResponseEntity.ok(repository.findAll());
//        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.emptyList());
        return ResponseEntity.ok(parkingOwnerService.getAllParkingOwners());
    }

    @PutMapping(path = "/{parkingOwnerId}")
    public ResponseEntity<ParkingOwner> updateParkingOwner(@RequestHeader HttpHeaders headers,
                                                 @PathVariable Long parkingId,
                                                 @RequestBody @Valid ParkingOwner updatedParkingOwner) {

        ParkingOwner result;
//        if (securityService.isAuthorized(headers)) {
//            result = companyService.updateCompany(companyId, updatedCompany);
//            if (Company.EMPTY.equals(result)) {
//                return ResponseEntity.badRequest().body(updatedCompany);
//            }
//            return ResponseEntity.ok(result);
//        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Company.EMPTY);
        result = parkingOwnerService.updateParkingOwner(parkingId, updatedParkingOwner);
        if (ParkingOwner.EMPTY.equals(result)) {
            return ResponseEntity.badRequest().body(updatedParkingOwner);
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(path = "/{parkingOwnerId}")
    public ResponseEntity<String> deleteParking(@RequestHeader HttpHeaders headers, @PathVariable Long parkingId) {

//        if (securityService.isAuthorized(headers)) {
//            boolean deleted = companyService.deleteCompany(companyId);
//            if (!deleted) {
//                return ResponseEntity.badRequest().body(String.format("Company with id %s does not exists.", companyId));
//            }
//            return ResponseEntity.ok(String.format("Company with id %s deleted.", companyId));
//        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access to resources.");
        boolean deleted = parkingOwnerService.deleteParkingOwner(parkingId);
        if (!deleted) {
            return ResponseEntity.badRequest().body(String.format("Parking with id %s does not exists.", parkingId));
        }
        return ResponseEntity.ok(String.format("Parking with id %s deleted.", parkingId));
    }




}
