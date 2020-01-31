package pw.react.backend.parklybackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pw.react.backend.parklybackend.dto.AvailableParkingDto;
import pw.react.backend.parklybackend.dto.ReservationDto;
import pw.react.backend.parklybackend.service.ReservationService;
import pw.react.backend.parklybackend.service.SecurityService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(path = "/reservations")
public class ReservationController {

    private ReservationService reservationService;
    private SecurityService securityService;

    @Autowired
    public ReservationController(ReservationService reservationService, SecurityService securityService) {
        this.reservationService = reservationService;
        this.securityService = securityService;
    }

    @PostMapping(path = "")
    public ResponseEntity<?> createReservation(@RequestHeader HttpHeaders headers, @Valid @RequestBody ReservationDto reservation) {

        if (securityService.isAuthorized(headers)) {
            Optional<ReservationDto> reservationDto = reservationService.createReservation(reservation);
            if(reservationDto.isPresent()){
                return ResponseEntity.ok(reservationDto.get());
            }
            else return ResponseEntity.status(HttpStatus.CONFLICT).body("This parking has no longer available parking spots in chosen dates");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ReservationDto.EMPTY);
    }

    @GetMapping(path = "/find-parkings")
    public ResponseEntity<Collection<AvailableParkingDto>> findAvailableParkings(@RequestHeader HttpHeaders headers, @RequestParam String city,
                           @RequestParam("dateFrom") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
                           @RequestParam("dateTo") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo) {

        if (securityService.isAuthorized(headers)) {

            Collection<AvailableParkingDto> parkings = reservationService.findAvailableParkings(city, dateFrom, dateTo);
            if(!parkings.isEmpty()){
                return ResponseEntity.ok(parkings);
            }
            else return ResponseEntity.status(HttpStatus.NO_CONTENT).body(parkings);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Collections.EMPTY_LIST);
    }

    @GetMapping(path = "")
    public ResponseEntity<Collection<ReservationDto>> getAllReservations(@RequestHeader HttpHeaders headers) {
        if (securityService.isAuthorized(headers)) {
            return ResponseEntity.ok(reservationService.getAllReservations());
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Collections.emptyList());
    }

    @GetMapping(path="/{reservationId}")
    public ResponseEntity<ReservationDto> getReservation(@RequestHeader HttpHeaders headers,  @PathVariable Long reservationId)
    {
        if (securityService.isAuthorized(headers)) {
            return ResponseEntity.ok(reservationService.getReservationById(reservationId));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ReservationDto.EMPTY);
    }


    @DeleteMapping(path = "/{reservationId}")
    public ResponseEntity<?> deleteReservation(@RequestHeader HttpHeaders headers, @PathVariable Long reservationId) {

        if (securityService.isAuthorized(headers)) {
            boolean deleted = reservationService.deleteReservation(reservationId);
            if(deleted){
                return ResponseEntity.ok(String.format("Reservation with id %s deleted.", reservationId));
            }
            else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format("Reservation with id %s cannot be deleted.", reservationId));

        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unautorized user");
    }

    @GetMapping(path = "/filter/{ownerId}")
    public ResponseEntity<Collection<ReservationDto>> filterReservations(@RequestHeader HttpHeaders headers, @PathVariable Long ownerId,
                                                                         @RequestParam Optional<String> city, @RequestParam Optional<String> street,
                                                                         @RequestParam Optional<Integer> totalCostFrom,  @RequestParam Optional<Integer> totalCostTo) {
        if (securityService.isAuthorized(headers)) {
            Collection<ReservationDto> res = reservationService.filterReservations(ownerId, city, street, totalCostFrom, totalCostTo);
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Collections.emptyList());
    }

    @GetMapping(path = "/parking/{parkingId}")
    public ResponseEntity<Collection<ReservationDto>> getAllReservationsByParking(@RequestHeader HttpHeaders headers, @PathVariable Long parkingId) {
        if (securityService.isAuthorized(headers)) {
            return ResponseEntity.ok(reservationService.getAllReservationsByParking(parkingId));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Collections.emptyList());
    }

}
