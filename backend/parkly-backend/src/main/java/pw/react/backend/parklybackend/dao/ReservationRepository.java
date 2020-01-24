package pw.react.backend.parklybackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pw.react.backend.parklybackend.model.Reservation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByUserToken(String userToken);
    List<Reservation> findAllByParkingIdAndDateToGreaterThanEqual(Long parkingId, LocalDateTime date);
}