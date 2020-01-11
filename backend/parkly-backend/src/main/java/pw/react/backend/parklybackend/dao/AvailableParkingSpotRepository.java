package pw.react.backend.parklybackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pw.react.backend.parklybackend.model.AvailableParkingSpot;

public interface AvailableParkingSpotRepository extends JpaRepository<AvailableParkingSpot, Long> {
}
