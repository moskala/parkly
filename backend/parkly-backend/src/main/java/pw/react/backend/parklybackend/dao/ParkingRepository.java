package pw.react.backend.parklybackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pw.react.backend.parklybackend.model.Parking;
import pw.react.backend.parklybackend.model.Reservation;

import java.util.List;

public interface ParkingRepository extends JpaRepository<Parking, Long> {
    List<Parking> findAllByOwnerID(long ownerId);
    List<Parking> findAllByCity(String city);
    List<Parking> findAllByCityAndStreet(String city, String street);
    List<Parking> findAllByWorkingHoursFromIsGreaterThanEqualAndWorkingHoursToLessThanEqualAndCityAndStreet(
            String city,
            String street,
            int workingHoursFrom,
            int workingHoursTo);
    List<Parking> findAllByWorkingHoursFromIsGreaterThanEqualAndWorkingHoursToLessThanEqualAndCity(
            String city,
            int workingHoursFrom,
            int workingHoursTo);
}