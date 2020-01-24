package pw.react.backend.parklybackend.service;

import org.springframework.http.ResponseEntity;
import pw.react.backend.parklybackend.dto.ParkingDto;
import pw.react.backend.parklybackend.model.Parking;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface ParkingService {
    ParkingDto updateParking(Long id, ParkingDto updatedParking);
    boolean deleteParking(Long parkingId);
    ParkingDto addParking(ParkingDto parking);
    ParkingDto getParking(long parkingId);
    Collection<ParkingDto> getAllParkings();
    Collection<ParkingDto> getAllParkingsByOnwerId(long parkingOwnerId);
    Collection<ParkingDto> filterParkings(String city, Optional<String> street, Optional<Integer> workingFrom, Optional<Integer> workingTo);
    boolean isParkingValid(Parking parking);
}
