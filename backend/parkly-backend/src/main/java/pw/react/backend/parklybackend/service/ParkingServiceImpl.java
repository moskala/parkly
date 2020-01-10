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
import pw.react.backend.parklybackend.dao.ParkingRepository;
import pw.react.backend.parklybackend.model.Parking;

import javax.validation.*;
import java.util.*;

@Service
class ParkingServiceImpl implements ParkingService {
    private final Logger logger = LoggerFactory.getLogger(ParkingServiceImpl.class);

    private ParkingRepository repository;

    ParkingServiceImpl() { /*Needed only for initializing spy in unit tests*/}




    @Autowired
    ParkingServiceImpl(ParkingRepository repository)
    {
        this.repository = repository;
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


    public List<Parking> filterByAll(String city, String street, int workingFrom, int workingTo) {
        return repository.findAllByCityAndStreetAndWorkingHoursFromIsLessThanEqualAndWorkingHoursToIsGreaterThanEqual(
                city, street, workingFrom, workingTo);
    }


    public List<Parking> filterByCityAndHours(String city, int workingFrom, int workingTo) {
        return repository.findAllByCityAndWorkingHoursFromIsLessThanEqualAndWorkingHoursToIsGreaterThanEqual(
                city, workingFrom, workingTo);
    }


    public List<Parking> filterByHoursFrom(String city, int workingFrom) {
        return repository.findAllByCityAndWorkingHoursFromIsLessThanEqual(city, workingFrom);
    }


    public List<Parking> filterByHoursTo(String city, int workingTo) {
        return repository.findAllByCityAndWorkingHoursToIsGreaterThanEqual(city, workingTo);
    }


    public List<Parking> filterByStreetAdnHoursFrom(String city, String street, int workingFrom) {
        return repository.findAllByCityAndStreetAndWorkingHoursFromIsLessThanEqual(city, street, workingFrom);
    }


    public List<Parking> filterByStreetAndHoursTo(String city, String street, int workingTo) {
        return repository.findAllByCityAndStreetAndWorkingHoursToIsGreaterThanEqual(city, street, workingTo);
    }


    @Override
    public List<Parking> filterParkings(String city, Optional<String> street, Optional<Integer> workingHoursFrom, Optional<Integer> workingHoursTo) {

        //wszystko
        if(street.isPresent() && workingHoursFrom.isPresent() && workingHoursTo.isPresent())
        {
            return filterByAll(city, street.get(), workingHoursFrom.get(), workingHoursTo.get());
        }
        //miasto i ulica
        else if(street.isPresent() && !workingHoursFrom.isPresent() && !workingHoursTo.isPresent())
        {
            return repository.findAllByCityAndStreet(city, street.get());
        }
        //tylko miasto
        else if(!street.isPresent() && !workingHoursFrom.isPresent() && !workingHoursTo.isPresent())
        {
            return repository.findAllByCity(city);
        }
        //tylko miasto i obie godziny
        else if(!street.isPresent() && workingHoursFrom.isPresent() && workingHoursTo.isPresent())
        {
           return filterByCityAndHours(city, workingHoursFrom.get(), workingHoursTo.get());
        }
        //tylko miasto i hoursFrom
        else if(!street.isPresent() && workingHoursFrom.isPresent() && !workingHoursTo.isPresent())
        {
            return  filterByHoursFrom(city, workingHoursFrom.get());
        }
        //tylko miasto i hoursTo
        else if(!street.isPresent() && !workingHoursFrom.isPresent() && workingHoursTo.isPresent())
        {
            return filterByHoursTo(city, workingHoursTo.get());
        }
        //miasto ulica hoursFrom
        else if(street.isPresent() && workingHoursFrom.isPresent() && !workingHoursTo.isPresent())
        {
            return filterByStreetAdnHoursFrom(city, street.get(), workingHoursFrom.get());
        }
        //miasto ulica hoursTo
        else if(street.isPresent() && !workingHoursFrom.isPresent() && workingHoursTo.isPresent())
        {
            return filterByStreetAndHoursTo(city, street.get(), workingHoursTo.get());
        }
        else return null;
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
}
