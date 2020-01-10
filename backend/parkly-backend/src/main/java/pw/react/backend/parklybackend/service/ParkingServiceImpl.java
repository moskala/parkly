package pw.react.backend.parklybackend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import pw.react.backend.parklybackend.dao.AvailableParkingSpotRepository;
import pw.react.backend.parklybackend.dao.ParkingRepository;
import pw.react.backend.parklybackend.model.AvailableParkingSpot;
import pw.react.backend.parklybackend.model.Parking;

import javax.validation.*;
import java.time.LocalDateTime;
import java.util.*;

@Service
class ParkingServiceImpl implements ParkingService {
    private final Logger logger = LoggerFactory.getLogger(ParkingServiceImpl.class);

    private ParkingRepository repository;
    private AvailableParkingSpotRepository repositorySpots;


    ParkingServiceImpl() { /*Needed only for initializing spy in unit tests*/}




    @Autowired
    ParkingServiceImpl(ParkingRepository repository, AvailableParkingSpotRepository repositorySpot)
    {
        this.repository = repository;
        this.repositorySpots = repositorySpot;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @Override
    public Parking updateParking(Long id, Parking updatedParking) {
        Parking result = Parking.EMPTY;
        if (repository.existsById(id)) {
            updatedParking.setId(id);

                result = repository.save(updatedParking);
                //logger.info("Parking with id {} updated.", id);

        }
        return result;
    }
    @Override
    public ResponseEntity<String> addParking(Parking parking)
    {
            Parking result = repository.save(parking);
            return ResponseEntity.ok("Parking is valid");
    }
    @Override
    public Collection<Parking> getAllParkings()
    {
        return repository.findAll();
    }

    @Override
    public boolean addNewDates(Collection<LocalDateTime> datesToAdd, Long parkingId) {
        Optional<Parking> parkingOpt = repository.findById(parkingId);
        if(!parkingOpt.isPresent()) return false;
        Parking parking = parkingOpt.get();
        for(LocalDateTime date:datesToAdd){
            List<AvailableParkingSpot> spotsToSave = addNewDateToParking(parking, date);
            repositorySpots.saveAll(spotsToSave);
        }
        return true;
    }

    @Override
    public Parking getParking(long parkingId)
    {
        return repository.findById(parkingId).orElseGet(() -> Parking.EMPTY);
    }

    @Override
    public boolean deleteParking(Long parkingId) {
        boolean result = false;
        if (repository.existsById(parkingId)) {
            repository.deleteById(parkingId);
            logger.info("Parking with id {} deleted.", parkingId);
            result = true;
        }
        return result;
    }

    public List<AvailableParkingSpot> addNewDateToParking(Parking parking, LocalDateTime date) {
        List<AvailableParkingSpot> spotsDates = new ArrayList<>();
        int workingFrom = parking.getWorkingHoursFrom();
        int workingTo = parking.getWorkingHoursTo();
        for(int spot = 1; spot <= parking.getNumberOfSpots(); ++spot) {
            for(int i = workingFrom; i < workingTo; ++i) {
                LocalDateTime dateFrom = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), i, 0);
                LocalDateTime dateTo = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), i+1, 0);
                AvailableParkingSpot avSpot = new AvailableParkingSpot(dateFrom, dateTo, spot, parking);
                spotsDates.add(avSpot);
            }
        }
        return spotsDates;
    }
}
