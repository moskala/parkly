package pw.react.backend.parklybackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pw.react.backend.parklybackend.model.Parking;
import pw.react.backend.parklybackend.model.ParkingSpot;

import java.util.List;
import java.util.Optional;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long>  {
    void deleteAllByParking(Parking parking);
    int countAllByParkingId(Long parkingId);
    List<ParkingSpot> findAllByParkingId(Long parkingId);
}

