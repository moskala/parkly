package pw.react.backend.parklybackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pw.react.backend.parklybackend.model.Parking;
import pw.react.backend.parklybackend.model.Reservation;

import java.util.List;

import java.util.Optional;

public interface ParkingRepository extends JpaRepository<Parking, Long> {

    List<Parking> findAllByOwnerId(long ownerId);

    List<Parking> findAllByCityAndWorkingHoursFromAndWorkingHoursTo(String city, int workingHoursFrom, int workingHoursTo);

    List<Parking> findAllByCityAndWorkingHoursFromIsLessThanEqualAndWorkingHoursToIsGreaterThanEqual(
            String city,
            int workingHoursFrom,
            int workingHoursTo
    );

    Optional<Parking> findById(Long aLong);

    @Query("SELECT p FROM Parking p WHERE ( p.owner.id = :ownerId) and (:city is null or p.city = :city) and (:street is null or p.street = :street) and (:workingHoursFrom is null or p.workingHoursFrom <= :workingHoursFrom) and (:workingHoursTo is null or p.workingHoursTo >= :workingHoursTo)")
    List<Parking> findByOwnerIdAndParams(@Param("ownerId") Long ownerId, @Param("city") String city,
                                                 @Param("street") String street, @Param("workingHoursFrom") Integer workingHoursFrom,
                                                 @Param("workingHoursTo") Integer workingHoursTo);

    @Query("SELECT p FROM Parking p WHERE p.owner.id= :ownerId")
    List<Parking> findByParam(@Param("ownerId") Long ownerId);

    @Query("SELECT p FROM Parking p WHERE (p.owner.id= :ownerId) and (:city is null or p.city = :city) ")
    List<Parking> findByParamCity(@Param("ownerId") Long ownerId, @Param("city") String city);

}