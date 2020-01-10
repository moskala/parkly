package pw.react.backend.parklybackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pw.react.backend.parklybackend.model.Parking;
import pw.react.backend.parklybackend.model.Reservation;
import pw.react.backend.parklybackend.model.ReservationCreateRequest;
import pw.react.backend.parklybackend.service.ReservationService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping(path = "/reservation")
public class ReservationController {

    private ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;

    }

    @PostMapping(path = "")
    public ResponseEntity<Reservation> createReservation(@RequestHeader HttpHeaders headers, @Valid @RequestBody ReservationCreateRequest reservation) {

        Reservation res = reservationService.createReservation(reservation);
        return ResponseEntity.ok(res);
    }

    @GetMapping(path = "")
    public ResponseEntity<Collection<Reservation>> getAllReservations(@RequestHeader HttpHeaders headers) {

        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @GetMapping(path = "/{userToken}")
    public ResponseEntity<Collection<Reservation>> getUserReservations(@RequestHeader HttpHeaders headers, @PathVariable String userToken) {

        return reservationService.getUserReservationsByToken(userToken);
    }

    @PutMapping(path = "/{reservationId}")
    public ResponseEntity<Reservation> updateParking(@RequestHeader HttpHeaders headers,
                                                 @PathVariable Long reservationId,
                                                 @RequestBody Reservation updatedReservation) {
        Reservation result;
        result = reservationService.updateReservation(reservationId, updatedReservation);
        if (Reservation.EMPTY.equals(result)) {
            return ResponseEntity.badRequest().body(updatedReservation);
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(path = "/{reservationId}")
    public ResponseEntity<String> deleteReservation(@RequestHeader HttpHeaders headers, @PathVariable Long reservationId) {

        boolean deleted = reservationService.deleteReservation(reservationId);
        if (!deleted) {
            return ResponseEntity.badRequest().body(String.format("Reservation with id %s does not exists.", reservationId));
        }
        return ResponseEntity.ok(String.format("Reservation with id %s deleted.", reservationId));
    }


}
