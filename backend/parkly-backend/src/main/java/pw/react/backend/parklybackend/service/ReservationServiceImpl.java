package pw.react.backend.parklybackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pw.react.backend.parklybackend.dao.ReservationRepository;
import pw.react.backend.parklybackend.model.Reservation;
import pw.react.backend.parklybackend.model.ReservationCreateRequest;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {
    private ReservationRepository repository;

    ReservationServiceImpl() { /*Needed only for initializing spy in unit tests*/}

    @Autowired
    ReservationServiceImpl(ReservationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Reservation createReservation(ReservationCreateRequest reservationCreateRequest) {
        Reservation reservation = new Reservation(reservationCreateRequest);
        reservation.setCreatedAt();
        return repository.save(reservation);

    }

    @Override
    public List<Reservation> getAllReservations() {
        return repository.findAll();
    }

    @Override
    public ResponseEntity<Collection<Reservation>> getUserReservationsByToken(String userToken) {
        List<Reservation> result = repository.findAllByUserToken(userToken);

        return ResponseEntity.ok(result);

        //else return ResponseEntity.badRequest().body(null);
    }

    @Override
    public Reservation updateReservation(Long reservationId, Reservation updatedReservation) {
        Reservation result = Reservation.EMPTY;
        if (repository.existsById(reservationId)) {
            updatedReservation.setId(reservationId);
            updatedReservation.setCreatedAt();
            result = repository.save(updatedReservation);
        }
        return result;
    }

    @Override
    public boolean deleteReservation(Long reservationId) {
        boolean result = false;
        if (repository.existsById(reservationId)) {
            repository.deleteById(reservationId);
            result = true;
        }
        return result;
    }
}
