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
import pw.react.backend.parklybackend.appException.InvalidArgumentException;
import pw.react.backend.parklybackend.appException.ResourceNotFoundException;
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
public class ParkingOwnerServiceImpl implements ParkingOwnerService {

    private ParkingOwnerRepository repository;

    ParkingOwnerServiceImpl() { }

    @Autowired
    ParkingOwnerServiceImpl(ParkingOwnerRepository repository)
    {
        this.repository = repository;
    }

    @Override
    public ParkingOwner addParkingOwner(ParkingOwner parkingOwner)
    {
        if(checkIfEmailExists(parkingOwner.getEmail())){
            throw new InvalidArgumentException("Given email already exists");
        }
        if(validateParkingOwner(parkingOwner)){
            return repository.save(parkingOwner);
        }
        else return ParkingOwner.EMPTY;
    }

    @Override
    public ParkingOwner getParkingOwner(long parkingOwnerId)
    {
        Optional<ParkingOwner> owner = repository.findById(parkingOwnerId);
        if(owner.isPresent()){
            return owner.get();
        }
        else throw new ResourceNotFoundException(String.format("Parking owner with id %s does not exists.", parkingOwnerId));
    }


    @Override
    public boolean checkIfEmailExists(String email) {
        Optional<ParkingOwner> alreadyExists = repository.findByEmail(email);
        if(alreadyExists.isPresent()) return true;
        return false;
    }

    @Override
    public boolean validateParkingOwner(ParkingOwner parkingOwner) {
        String name = parkingOwner.getName();
        String surname = parkingOwner.getSurname();
        String email = parkingOwner.getEmail();
        String phone = parkingOwner.getPhoneNumber();
        String password = parkingOwner.getPassword();
        if(name == null || name.isEmpty() || name.trim().isEmpty()) throw new InvalidArgumentException("Name cannot be null");
        if(surname == null || surname.isEmpty() || surname.trim().isEmpty()) throw new InvalidArgumentException("Surname cannot be null");
        if(email == null || email.isEmpty() || email.trim().isEmpty()) throw new InvalidArgumentException("Email cannot be null");
        if(password == null || password.isEmpty() || password.trim().isEmpty()) throw new InvalidArgumentException("Password cannot be null");
        if(phone == null || phone.isEmpty() || phone.trim().isEmpty()) throw new InvalidArgumentException("Phone cannot be null");

        return true;
    }
}
