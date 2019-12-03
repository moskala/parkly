package pw.react.backend.parklybackend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pw.react.backend.parklybackend.dao.ParkingRepository;
import pw.react.backend.parklybackend.model.Parking;

@Service
class ParkingServiceImpl implements ParkingService {
    private final Logger logger = LoggerFactory.getLogger(ParkingServiceImpl.class);

    private ParkingRepository repository;

    ParkingServiceImpl() { /*Needed only for initializing spy in unit tests*/}

    @Autowired
    ParkingServiceImpl(ParkingRepository repository) {
        this.repository = repository;
    }

    @Override
    public Parking updateParking(Long id, Parking updatedParking) {
        Parking result = Parking.EMPTY;
        if (repository.existsById(id)) {
            updatedParking.setId(id);
            result = repository.save(updatedParking);
            logger.info("Parking with id {} updated.", id);
        }
        return result;
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
