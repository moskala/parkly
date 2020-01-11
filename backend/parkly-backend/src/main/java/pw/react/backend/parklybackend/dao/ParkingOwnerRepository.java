package pw.react.backend.parklybackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pw.react.backend.parklybackend.model.ParkingOwner;

import java.util.List;
import java.util.Optional;

public interface ParkingOwnerRepository extends JpaRepository<ParkingOwner, Long>{

    Optional<ParkingOwner> findByEmail(String email);
}
