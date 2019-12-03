package pw.react.backend.parklybackend.service;

import pw.react.backend.parklybackend.model.Parking;


public interface ParkingService {
    Parking updateParking(Long id, Parking updatedParking);
    boolean deleteParking(Long parkingId);
}
