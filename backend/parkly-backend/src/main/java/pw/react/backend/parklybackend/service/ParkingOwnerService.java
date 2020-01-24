package pw.react.backend.parklybackend.service;

import pw.react.backend.parklybackend.model.ParkingOwner;

public interface ParkingOwnerService {

    ParkingOwner addParkingOwner(ParkingOwner parkingOwner);
    ParkingOwner getParkingOwner(long parkingOwnerId);
    boolean checkIfEmailExists(String email);
    boolean validateParkingOwner(ParkingOwner parkingOwner);
}