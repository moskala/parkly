package pw.react.backend.parklybackend.service;

import org.springframework.http.ResponseEntity;
import pw.react.backend.parklybackend.model.ParkingOwner;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Map;


public interface ParkingOwnerService {
    ParkingOwner updateParkingOwner(Long id, ParkingOwner updatedParkingOwner);
    boolean deleteParkingOwner(Long parkingOwnerId);
    ResponseEntity<String> addParkingOwner(ParkingOwner parkingOwner);
    ParkingOwner getParkingOwner(long parkingOwnerId);
    Collection<ParkingOwner> getAllParkingOwners();
    boolean authenticateUser(String email, String password);
    boolean checkEmailExists(String email);
}