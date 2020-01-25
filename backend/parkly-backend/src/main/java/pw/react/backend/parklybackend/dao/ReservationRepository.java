package pw.react.backend.parklybackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pw.react.backend.parklybackend.model.Reservation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByParkingIdAndDateToGreaterThanEqual(Long parkingId, LocalDateTime date);

    void deleteById(Long reservationId);

    @Override
    boolean existsById(Long aLong);

    List<Reservation> findAllByParkingId(Long parkingId);

    @Query("SELECT r FROM Reservation r WHERE ( r.parking.owner.id = :ownerId) and (:city is null or r.parking.city = :city) and (:street is null or r.parking.street = :street)" +
            "and (:totalCostFrom is null or r.totalCost >= :totalCostFrom) and (:totalCostTo is null or r.totalCost <= :totalCostTo)")
    List<Reservation> findReservationsWithParams(@Param("ownerId") Long ownerId, @Param("city") String city,
                                                 @Param("street") String street, @Param("totalCostFrom") Integer totalCostFrom,
                                                 @Param("totalCostTo") Integer totalCostTo);

}