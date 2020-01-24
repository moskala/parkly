package pw.react.backend.parklybackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pw.react.backend.parklybackend.model.Parking;
import pw.react.backend.parklybackend.model.Reservation;

import java.util.List;

import java.util.Optional;

public interface ParkingRepository extends JpaRepository<Parking, Long> {

    List<Parking> findAllByOwner(long ownerId);
    List<Parking> findAllByCity(String city);
    List<Parking> findAllByCityAndStreet(String city, String street);

    List<Parking> findAllByCityAndStreetAndWorkingHoursFromIsLessThanEqualAndWorkingHoursToIsGreaterThanEqual(
            String city,
            String street,
            int workingHoursFrom,
            int workingHoursTo
    );

    List<Parking> findAllByCityAndWorkingHoursFromIsLessThanEqualAndWorkingHoursToIsGreaterThanEqual(
            String city,
            int workingHoursFrom,
            int workingHoursTo
    );

    List<Parking> findAllByCityAndWorkingHoursFromIsLessThanEqual(String city, int workingHoursFrom);

    List<Parking> findAllByCityAndWorkingHoursToIsGreaterThanEqual(String city, int workingHoursTo);

    List<Parking> findAllByCityAndStreetAndWorkingHoursFromIsLessThanEqual(String city, String street, int workingHoursFrom);

    List<Parking> findAllByCityAndStreetAndWorkingHoursToIsGreaterThanEqual(String city, String street, int workingHoursTo);

    Optional<Parking> findById(Long aLong);
}