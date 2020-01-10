package pw.react.backend.parklybackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pw.react.backend.parklybackend.model.ParkingOwner;

public interface ParkingOwnerRepository extends JpaRepository<ParkingOwner, Long>{
}
