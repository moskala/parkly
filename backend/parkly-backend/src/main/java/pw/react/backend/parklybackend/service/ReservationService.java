package pw.react.backend.parklybackend.service;

import org.springframework.http.ResponseEntity;
import pw.react.backend.parklybackend.dto.ReservationDto;
import pw.react.backend.parklybackend.model.Reservation;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public interface ReservationService {

    ReservationDto getReservationById(Long reservationId);

    Optional<ReservationDto> createReservation(ReservationDto reservation);

    Collection<ReservationDto> getAllReservations();

    Collection<ReservationDto> getReservationsByParking(Long parkingId);

    boolean deleteReservation(Long reservationId);

    Collection<ReservationDto> findPossibleReservations(String city, LocalDateTime dateFrom, LocalDateTime dateTo);

    Collection<ReservationDto> filterReservations(Long ownerId, Optional<String> city, Optional<String> street,
                                                  Optional<Integer> incomeFrom, Optional<Integer> incomeTo);

}
