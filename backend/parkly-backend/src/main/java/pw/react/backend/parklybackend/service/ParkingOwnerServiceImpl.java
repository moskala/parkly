package pw.react.backend.parklybackend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import pw.react.backend.parklybackend.dao.ParkingOwnerRepository;
import pw.react.backend.parklybackend.model.Parking;
import pw.react.backend.parklybackend.model.ParkingOwner;
import pw.react.backend.parklybackend.controller.ParkingOwnerController;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.validation.*;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.*;

@Service
class ParkingOwnerServiceImpl implements ParkingOwnerService {
    private final Logger logger = LoggerFactory.getLogger(ParkingServiceImpl.class);

    private ParkingOwnerRepository repository;

    ParkingOwnerServiceImpl() { /*Needed only for initializing spy in unit tests*/}




    @Autowired
    ParkingOwnerServiceImpl(ParkingOwnerRepository repository)
    {
        this.repository = repository;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @Override
    public ParkingOwner updateParkingOwner(Long id, ParkingOwner updatedParkingOwner) {
        ParkingOwner result = ParkingOwner.EMPTY;
        if (repository.existsById(id)) {
            updatedParkingOwner.setId(id);

            result = repository.save(updatedParkingOwner);
            //logger.info("Parking with id {} updated.", id);

        }
        return result;
    }

    private String Hash(String password)
    {
//        SecureRandom random = new SecureRandom();
//        byte[] salt = new byte[16];
//        random.nextBytes(salt);
//        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
//        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        return Integer.toString(password.hashCode());
    }

    @Override
    public ResponseEntity<String> addParkingOwner(ParkingOwner parkingOwner)
    {
        //sprawdzenie mejla
        if(repository.findByEmail(parkingOwner.getEmail()).isPresent())
        {
            parkingOwner.setHashPassword(Hash(parkingOwner.getHashPassword()));
            ParkingOwner result = repository.save(parkingOwner);
            return ResponseEntity.ok("Parking owner is valid");
        }
        else
        {
            return ResponseEntity.badRequest().body("User with this email already exists");
        }
    }
    @Override
    public Collection<ParkingOwner> getAllParkingOwners()
    {
        return repository.findAll();
    }
    @Override
    public ParkingOwner getParkingOwner(long parkingOwnerId)
    {
        return repository.findById(parkingOwnerId).orElseGet(() -> ParkingOwner.EMPTY);
    }

    @Override
    public boolean deleteParkingOwner(Long parkingOwnerId) {
        boolean result = false;
        if (repository.existsById(parkingOwnerId)) {
            repository.deleteById(parkingOwnerId);
            logger.info("Parking with id {} deleted.", parkingOwnerId);
            result = true;
        }
        return result;
    }
}
