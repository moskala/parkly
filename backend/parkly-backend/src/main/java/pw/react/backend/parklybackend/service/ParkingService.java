package pw.react.backend.parklybackend.service;

import org.springframework.http.ResponseEntity;
import pw.react.backend.parklybackend.model.Parking;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Map;


public interface ParkingService {
    Parking updateParking(Long id, Parking updatedParking);
    boolean deleteParking(Long parkingId);
    boolean validateParking(Parking parking);
    ResponseEntity<String> addParking(Parking parking);
    Parking getParking(long parkingId);
    Collection<Parking> getAllParkings();
}
