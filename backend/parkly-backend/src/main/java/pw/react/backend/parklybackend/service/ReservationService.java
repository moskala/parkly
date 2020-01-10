package pw.react.backend.parklybackend.service;

import org.springframework.http.ResponseEntity;
import pw.react.backend.parklybackend.model.Reservation;
import pw.react.backend.parklybackend.model.ReservationCreateRequest;

import java.util.Collection;

public interface ReservationService {
    Reservation createReservation(ReservationCreateRequest reservation);

    Collection<Reservation> getAllReservations();

    ResponseEntity<Collection<Reservation>> getUserReservationsByToken(String userToken);

    Reservation updateReservation(Long reservationId, Reservation updatedReservation);

    boolean deleteReservation(Long reservationId);
}
