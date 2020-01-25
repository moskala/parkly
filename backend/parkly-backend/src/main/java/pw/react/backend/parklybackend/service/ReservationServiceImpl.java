package pw.react.backend.parklybackend.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import pw.react.backend.parklybackend.appException.ForbiddenActionException;
import pw.react.backend.parklybackend.appException.ResourceNotFoundException;
import pw.react.backend.parklybackend.dao.ParkingOwnerRepository;
import pw.react.backend.parklybackend.dao.ParkingRepository;
import pw.react.backend.parklybackend.dao.ParkingSpotRepository;
import pw.react.backend.parklybackend.dao.ReservationRepository;
import pw.react.backend.parklybackend.dto.ParkingDto;
import pw.react.backend.parklybackend.dto.ReservationDto;
import pw.react.backend.parklybackend.model.Parking;
import pw.react.backend.parklybackend.model.ParkingSpot;
import pw.react.backend.parklybackend.model.Reservation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    private ParkingRepository parkingRepository;
    private ReservationRepository reservationRepository;
    private ParkingOwnerRepository ownerRepository;
    private ParkingSpotRepository spotRepository;

    ReservationServiceImpl() { }

    @Autowired
    ReservationServiceImpl(ParkingRepository repository, ReservationRepository reservationRepository,
                       ParkingOwnerRepository ownerRepository, ParkingSpotRepository spotRepository) {
        this.parkingRepository = repository;
        this.reservationRepository = reservationRepository;
        this.ownerRepository = ownerRepository;
        this.spotRepository = spotRepository;
    }


    @Override
    public ReservationDto getReservationById(Long reservationId) {
        return null;
    }

    @Override
    public Optional<ReservationDto> createReservation(ReservationDto reservation) {
        return Optional.empty();
    }

    @Override
    public Collection<ReservationDto> getAllReservations() {
        return getReservationDtoCollection(reservationRepository.findAll());
    }

    @Override
    public Collection<ReservationDto> getReservationsByParking(Long parkingId) {

        if(parkingRepository.existsById(parkingId)){
            return getReservationDtoCollection(reservationRepository.findAllByParkingId(parkingId));
        }
        else throw new ResourceNotFoundException(String.format("Parking with id %s does not exists.", parkingId));
    }

    @Override
    public boolean deleteReservation(Long reservationId) {

        if(reservationRepository.existsById(reservationId)){
            reservationRepository.deleteById(reservationId);
            return true;
        }
        else throw new ResourceNotFoundException(String.format("Reservation with id %s does not exists.", reservationId));
    }

    @Override
    public Collection<ReservationDto> findPossibleReservations(String city, LocalDateTime dateFrom, LocalDateTime dateTo) {
        return null;
    }

    @Override
    public Collection<ReservationDto> filterReservations(Long ownerId, Optional<String> city, Optional<String> street, Optional<Integer> incomeFrom, Optional<Integer> incomeTo) {
        if(ownerRepository.existsById(ownerId)){
            String cityParam = null;
            String streetParam = null;
            Integer costFrom = null;
            Integer costTo = null;
            if(city.isPresent()) cityParam = city.get();
            if(street.isPresent()) streetParam = street.get();
            if(incomeFrom.isPresent()) costFrom = incomeFrom.get();
            if(incomeTo.isPresent()) costTo = incomeTo.get();
            return getReservationDtoCollection(reservationRepository.findReservationsWithParams(ownerId, cityParam, streetParam, costFrom, costTo));
        }
        else throw new ResourceNotFoundException(String.format("Parking owner with id %s does not exists.", ownerId));
    }

    private ReservationDto createReservationDtoFromValue(Reservation reservation){
        ReservationDto dto = ReservationDto.EMPTY;

        dto.setId(reservation.getId());
        dto.setSpotId(reservation.getParkingSpot().getSpotId());
        dto.setDateFrom(reservation.getDateFrom());
        dto.setDateTo(reservation.getDateTo());
        dto.setTotalCost(reservation.getTotalCost());
        dto.setUserFirstName(reservation.getUserFirstName());
        dto.setUserLastName(reservation.getUserLastName());
        dto.setUserEmail(reservation.getUserEmail());
        dto.setCreatedAt(reservation.getCreatedAt());
        dto.setSpotId(reservation.getParkingSpot().getSpotId());

        Parking parking = reservation.getParkingSpot().getParking();
        dto.setCity(parking.getCity());
        dto.setStreet(parking.getStreet());
        dto.setStreetNumber(parking.getStreetNumber());
        dto.setParkingId(parking.getId());

        return dto;
    }

    private Reservation createReservationFromDto(ReservationDto dto){
        Reservation res = Reservation.EMPTY;

        res.setDateFrom(dto.getDateFrom());
        res.setDateTo(dto.getDateTo());
        res.setUserFirstName(dto.getUserFirstName());
        res.setUserLastName(dto.getUserLastName());
        res.setUserEmail(dto.getUserEmail());
        // parkingSpot, parking, createdAt

        return res;
    }

    private Collection<ReservationDto> getReservationDtoCollection(Collection<Reservation> reservations){

        List<ReservationDto> res = new ArrayList<ReservationDto>(reservations.size());
        for (Reservation reservation : reservations) {
            ReservationDto dto = createReservationDtoFromValue(reservation);
            res.add(dto);
        }
        return res;
    }



}
