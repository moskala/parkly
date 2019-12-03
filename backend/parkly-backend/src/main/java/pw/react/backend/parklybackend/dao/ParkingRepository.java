package pw.react.backend.parklybackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pw.react.backend.parklybackend.model.Parking;

public interface ParkingRepository extends JpaRepository<Parking, Long> {
}