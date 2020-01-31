package pw.react.backend.parklybackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pw.react.backend.parklybackend.dto.ReservationDto;
import pw.react.backend.parklybackend.model.ParkingSpot;
import pw.react.backend.parklybackend.model.Reservation;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByParkingSpotParkingIdAndDateToGreaterThanEqual(Long parkingId, LocalDateTime date);

    void deleteById(Long reservationId);

    @Override
    boolean existsById(Long aLong);

    @Query("SELECT r FROM Reservation r WHERE ( r.parkingSpot.parking.id = :parkingId)")
    List<Reservation> findAllReservationsBySpotByParkingId(@Param("parkingId") Long parkingId);

    List<Reservation> findAllByParkingSpotParkingId(Long parkingId);

    @Query("SELECT r FROM Reservation r WHERE ( r.parkingSpot.parking.owner.id = :ownerId) and (:city is null or r.parkingSpot.parking.city = :city)"
            +"and (:street is null or r.parkingSpot.parking.street = :street) and (:totalCostFrom is null or r.totalCost >= :totalCostFrom) and (:totalCostTo is null or r.totalCost <= :totalCostTo)")
    List<Reservation> findReservationsWithParams(@Param("ownerId") Long ownerId, @Param("city") String city,
                                                 @Param("street") String street, @Param("totalCostFrom") Integer totalCostFrom,
                                                 @Param("totalCostTo") Integer totalCostTo);

    @Query("SELECT r.parkingSpot FROM Reservation r WHERE ( r.parkingSpot.parking.id = :parkingId)" +
            "and ((:dateFrom > r.dateFrom) and (:dateFrom < r.dateTo)) or ((:dateTo > r.dateFrom) and (:dateTo < r.dateTo))")
    List<ParkingSpot> findCrossedReservationsParkingSpots(@Param("parkingId") Long parkingId, @Param("dateFrom") LocalDateTime dateFrom,
                                              @Param("dateTo") LocalDateTime dateTo);

    @Query("SELECT r.parkingSpot FROM Reservation r WHERE ( r.parkingSpot.parking.id = :parkingId)" +
            "and (:dateFrom <= r.dateFrom) and (:dateTo >= r.dateTo)")
    List<ParkingSpot> findInternalReservationsParkingSpots(@Param("parkingId") Long parkingId, @Param("dateFrom") LocalDateTime dateFrom,
                                                                    @Param("dateTo") LocalDateTime dateTo);


}