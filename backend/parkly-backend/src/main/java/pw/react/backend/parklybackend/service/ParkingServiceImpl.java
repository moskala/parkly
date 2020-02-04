package pw.react.backend.parklybackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import pw.react.backend.parklybackend.appException.ForbiddenActionException;
import pw.react.backend.parklybackend.appException.InvalidArgumentException;
import pw.react.backend.parklybackend.appException.ResourceNotFoundException;
import pw.react.backend.parklybackend.dao.ParkingOwnerRepository;
import pw.react.backend.parklybackend.dao.ParkingRepository;
import pw.react.backend.parklybackend.dao.ParkingSpotRepository;
import pw.react.backend.parklybackend.dao.ReservationRepository;
import pw.react.backend.parklybackend.dto.ParkingDto;
import pw.react.backend.parklybackend.model.Parking;
import pw.react.backend.parklybackend.model.ParkingOwner;
import pw.react.backend.parklybackend.model.ParkingSpot;
import pw.react.backend.parklybackend.model.Reservation;

import javax.validation.*;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ParkingServiceImpl implements ParkingService {

    private ParkingRepository parkingRepository;
    private ReservationRepository reservationRepository;
    private ParkingOwnerRepository ownerRepository;
    private ParkingSpotRepository spotRepository;

    ParkingServiceImpl() { }

    @Autowired
    public ParkingServiceImpl(ParkingRepository repository, ReservationRepository reservationRepository,
                       ParkingOwnerRepository ownerRepository, ParkingSpotRepository spotRepository) {
        this.parkingRepository = repository;
        this.reservationRepository = reservationRepository;
        this.ownerRepository = ownerRepository;
        this.spotRepository = spotRepository;
    }

    @Override
    @Transactional
    public ParkingDto updateParking(Long id, ParkingDto parkingRequest) {

        if(existReservationsForParking(id)){
            throw new ForbiddenActionException(String.format(" There are existing future reservations for parking with id %s .", id));
        }
        if (parkingRepository.existsById(id)) {

            isNumberOfSpotsValid(parkingRequest);

            Parking parking = Parking.valueFrom(parkingRequest);

            if(isParkingValid(parking)){
                parking.setId(id);
                parking.setOwner(getParkingOwner(parkingRequest.getOwnerId()));
                Parking parkingUpdated =  parkingRepository.save(parking);
                addParkingSpots(parkingRequest.getSpotsNumber(), parkingUpdated);

                return getParkingDto(parkingUpdated);
            }
            else return ParkingDto.EMPTY;
        }
        else throw new ResourceNotFoundException(String.format("Parking with id %s does not exists.", id));
    }

    @Override
    public ParkingDto getParking(long parkingId) {

        Optional<Parking> parking = parkingRepository.findById(parkingId);

        if(parking.isPresent()){

            return getParkingDto(parking.get());
        }
        else throw new ResourceNotFoundException(String.format("Parking with id %s does not exists.", parkingId));
    }

    @Override
    @Transactional
    public boolean deleteParking(Long parkingId) {

        if(existReservationsForParking(parkingId)){

            throw new ForbiddenActionException(String.format("Parking with id %s has future reservations.", parkingId));
        }

        Optional<Parking> parking = parkingRepository.findById(parkingId);
        if (parking.isPresent()) {
            spotRepository.deleteAllByParking(parking.get());
            parkingRepository.delete(parking.get());
            return true;
        }
        else throw new ResourceNotFoundException(String.format("Parking with id %s does not exists.", parkingId));
    }

    @Override
    @Transactional
    public ParkingDto addParking(ParkingDto parkingRequest) {

        isNumberOfSpotsValid(parkingRequest);

        Parking parking = Parking.valueFrom(parkingRequest);

        if(isParkingValid(parking)){

            parking.setOwner(getParkingOwner(parkingRequest.getOwnerId()));
            Parking parkingSaved = parkingRepository.save(parking);
            addParkingSpots(parkingRequest.getSpotsNumber(), parkingSaved);

            return getParkingDto(parkingSaved);
        }
        return ParkingDto.EMPTY;
    }

    @Override
    public Collection<ParkingDto> getAllParkings() {

        return  getParkingDtoCollection(parkingRepository.findAll());
    }

    @Override
    public Collection<ParkingDto> getAllParkingsByOnwerId(Long ownerId) {
        if(ownerRepository.existsById(ownerId)){

            return getParkingDtoCollection(parkingRepository.findAllByOwnerId(ownerId));
        }
        else throw new ResourceNotFoundException(String.format("Parking owner with id %s does not exists.", ownerId));
    }

    @Override
    public Collection<ParkingDto> filterParkingsForOwnerIdWithParams(Long ownerId, Optional<String> city, Optional<String> street, Optional<Integer> workingFrom, Optional<Integer> workingTo) {

            String cityVal = null;
            String streetVal = null;
            Integer workingToVal = null;
            Integer workingFromVal = null;

            if(city.isPresent()) cityVal = city.get();
            if(street.isPresent()) streetVal = street.get();
            if(workingFrom.isPresent()) workingFromVal = workingFrom.get();
            if(workingTo.isPresent()) workingToVal = workingTo.get();

            List<Parking> filterResults = parkingRepository.findByOwnerIdAndParams(ownerId, cityVal, streetVal, workingFromVal, workingToVal);

            return getParkingDtoCollection(filterResults);
    }

    @Override
    public boolean isParkingValid(Parking parking) {
        if (!Parking.isAddressPartValid(parking.getCity())) throw new InvalidArgumentException("City name cannot be null and has to contain only letters");
        if (!Parking.isAddressPartValid(parking.getStreet())) throw new InvalidArgumentException("Street name cannot be null and has to contain only letters");
        if (!Parking.isNumberValid(parking.getStreetNumber())) throw new InvalidArgumentException("Street number has to be greater than 0");
        if (!Parking.areWorkingHoursValid(parking.getWorkingHoursFrom())) throw new InvalidArgumentException("Working hours from cannot be negative or greater than 24");
        if (!Parking.areWorkingHoursValid(parking.getWorkingHoursTo())) throw new InvalidArgumentException("Working hours to cannot be negative or greater than 24");
        if (!Parking.isNumberValid(parking.getCostPerHour())) throw new InvalidArgumentException("Cost per hour has to be greater than 0");

        return true;
    }

    private void isNumberOfSpotsValid(ParkingDto parking){
        if (!Parking.isNumberValid(parking.getSpotsNumber())) throw new InvalidArgumentException("Number of spots has to be greater than 0");
    }

    private boolean existReservationsForParking(Long parkingId) {
        LocalDateTime today = LocalDateTime.now();
        List<Reservation> reservations = reservationRepository.findAllByParkingSpotParkingIdAndDateToGreaterThanEqual(parkingId, today);
        if(reservations.isEmpty()){
            return false;
        }
        return true;
    }


    @Override
    @Transactional
    public void addParkingSpots(int numberOfSpots, Parking parking){

        int count = spotRepository.countAllByParkingId(parking.getId());
        if(count == numberOfSpots) return;

        spotRepository.deleteAllByParking(parking);
        Collection<ParkingSpot> spots = new ArrayList<ParkingSpot>();
        for(int i = 1; i <= numberOfSpots; ++i){
            ParkingSpot spot = new ParkingSpot();
            spot.setSpotNumber(i);
            spot.setParking(parking);
            spots.add(spot);
        }
        spotRepository.saveAll(spots);
    }

    private ParkingOwner getParkingOwner(Long ownerId){
        Optional<ParkingOwner> owner = ownerRepository.findById(ownerId);
        if(!owner.isPresent()) throw new ResourceNotFoundException(String.format("Parking owner with id %s does not exists.", ownerId));
        return owner.get();
    }

    private ParkingDto getParkingDto(Parking parking){
        ParkingDto dto = ParkingDto.valueFrom(parking);
        setNumberOfSpots(dto);
        return dto;
    }

    private void setNumberOfSpots(ParkingDto parkingDto){
        int numberOfSpots = spotRepository.countAllByParkingId(parkingDto.getId());
        parkingDto.setSpotsNumber(numberOfSpots);
    }

    private Collection<ParkingDto> getParkingDtoCollection(Collection<Parking> parkings){
        List<ParkingDto> parkingDtos = new ArrayList<>(parkings.size());
        for (Parking parking : parkings) {
            parkingDtos.add(getParkingDto(parking));
        }
        return parkingDtos;
    }

}
