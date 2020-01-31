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
import pw.react.backend.parklybackend.dto.LoggedUserDto;
import pw.react.backend.parklybackend.model.Parking;
import pw.react.backend.parklybackend.model.ParkingOwner;
import pw.react.backend.parklybackend.service.ParkingOwnerService;
import pw.react.backend.parklybackend.service.SecurityService;

import javax.validation.Valid;
import java.util.*;

import static java.util.stream.Collectors.joining;

@CrossOrigin
@RestController
@RequestMapping(path = "/parking-owner")
public class ParkingOwnerController {

    private ParkingOwnerService parkingOwnerService;
    private SecurityService securityService;

    @Autowired
    public ParkingOwnerController(ParkingOwnerService parkingOwnerService, SecurityService securityService) {
        this.parkingOwnerService = parkingOwnerService;
        this.securityService = securityService;
    }

    @PostMapping(path = "")
    public ResponseEntity<ParkingOwner> createParkingOwner(@RequestHeader HttpHeaders headers, @Valid @RequestBody ParkingOwner parkingOwner)
    {
        return ResponseEntity.ok(parkingOwnerService.addParkingOwner(parkingOwner));
    }

    @GetMapping(path = "/{parkingOwnerId}")
    public ResponseEntity<ParkingOwner> getParkingOwner(@RequestHeader HttpHeaders headers,
                                              @PathVariable Long parkingOwnerId) {

        if (securityService.isAuthorized(headers)) {
            return ResponseEntity.ok(parkingOwnerService.getParkingOwner(parkingOwnerId));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ParkingOwner.EMPTY);
    }


    @GetMapping(path = "/login")
    public ResponseEntity<LoggedUserDto> logInUser(@RequestHeader HttpHeaders headers) {

        Optional<LoggedUserDto> dto = securityService.authenticateParkingOwner(headers);
        if(dto.isPresent()){
            return ResponseEntity.ok(dto.get());
        }
        else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(LoggedUserDto.EMPTY);
    }

    @GetMapping(path="/email")
    public ResponseEntity<String> checkIsEmailUnique(@RequestHeader HttpHeaders headers, @RequestParam String emailAddress){

        if(parkingOwnerService.checkIfEmailExists(emailAddress)){
            return ResponseEntity.status(HttpStatus.SEE_OTHER).body("Email already exists in service");
        }
        else return ResponseEntity.ok("Email is unique");
    }

}
