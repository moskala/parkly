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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pw.react.backend.parklybackend.dao.ParkingRepository;
import pw.react.backend.parklybackend.model.Parking;
import pw.react.backend.parklybackend.service.ParkingService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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

    @PostMapping(path = "")
    public ResponseEntity<String> createParking(@RequestHeader HttpHeaders headers, @Valid @RequestBody List<Parking> parkings) {


            List<Parking> result = repository.saveAll(parkings);
            return ResponseEntity.ok(result.stream().map(c -> String.valueOf(c.getId())).collect(joining(",")));


    }


    @GetMapping(path = "/{parkingId}")
    public ResponseEntity<Parking> getParking(@RequestHeader HttpHeaders headers,
                                              @PathVariable Long parkingId) {

//        if (securityService.isAuthorized(headers)) {
//            return ResponseEntity.ok(repository.findById(parkingId).orElseGet(() -> Parking.EMPTY));
//        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Parking.EMPTY);
        return ResponseEntity.ok(repository.findById(parkingId).orElseGet(() -> Parking.EMPTY));
    }

    @GetMapping(path = "")
    public ResponseEntity<Collection<Parking>> getAllParkings(@RequestHeader HttpHeaders headers) {
//
//        if (securityService.isAuthorized(headers)) {
//            return ResponseEntity.ok(repository.findAll());
//        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.emptyList());
        return ResponseEntity.ok(repository.findAll());
    }

    @PutMapping(path = "/{parkingId}")
    public ResponseEntity<Parking> updateParking(@RequestHeader HttpHeaders headers,
                                                 @PathVariable Long parkingId,
                                                 @RequestBody Parking updatedParking) {

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
